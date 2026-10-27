#!/usr/bin/env python3
"""
AI Video Mix (ai.fusion.character.merge v1.2.2 b22) → OpenAI 兼容 2API demo server
上游: https://aifusion.aritek.app/  (aritek 系, 明文 JSON, 无加密)
鉴权: Sign = 原版 APK 证书 SHA1 (3325ecccfae4a0c4ee7eed0a4043daac58c51bec)
      Device-Id = 任意 hex (每设备免费 10 次/天 t2v)
      token = GET /api/v1/user/info 签发的短期 JWT (~1h), 自动换号
端口: 8913
"""
import json
import os
import ssl
import time
import uuid
import threading
import urllib.request
import urllib.error
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer

UPSTREAM = "https://aifusion.aritek.app"
SIGN = os.environ.get("AIVM_SIGN", "3325ecccfae4a0c4ee7eed0a4043daac58c51bec")
PORT = int(os.environ.get("AIVM_PORT", "8913"))
CT = ssl.create_default_context()
CT.check_hostname = False
CT.verify_mode = ssl.CERT_NONE

_lock = threading.Lock()
_dev_pool = []  # [(device_id, token, exp_ts, remaining)]


def _req(path, headers=None, data=None, method=None, timeout=60):
    req = urllib.request.Request(UPSTREAM + path, data=data, method=method,
                                 headers=headers or {})
    return urllib.request.urlopen(req, context=CT, timeout=timeout)


def _new_device():
    dev = uuid.uuid4().hex
    h = {"Sign": SIGN, "Device-Id": dev, "Ctry-Target": "others",
         "Authorization": "Bearer x", "User-Agent": "okhttp/4.12.0"}
    tok = json.loads(_req("/api/v1/user/info", h).read())["data"]["token"]
    return {"device_id": dev, "token": tok, "exp": time.time() + 3000}


def get_credential():
    with _lock:
        global _dev_pool
        now = time.time()
        _dev_pool = [d for d in _dev_pool if d["exp"] > now]
        for d in _dev_pool:
            try:
                r = json.loads(_req("/api/v1/user/check-limit?type=t2v", {
                    "Sign": SIGN, "Device-Id": d["device_id"],
                    "Ctry-Target": "others",
                    "Authorization": "Bearer " + d["token"],
                    "User-Agent": "okhttp/4.12.0"}).read())
                if r["data"].get("allowed"):
                    return d
            except Exception:
                continue
        d = _new_device()
        _dev_pool.append(d)
        return d


def poll_video(url, max_wait=300, interval=10):
    """上游返回 URL 即 task id；视频就绪以 CDN 200+video 为准"""
    t0 = time.time()
    while time.time() - t0 < max_wait:
        time.sleep(interval)
        try:
            r = urllib.request.urlopen(urllib.request.Request(
                url, method="HEAD", headers={"User-Agent": "okhttp/4.12.0"}),
                context=CT, timeout=15)
            ct = r.headers.get("Content-Type", "")
            if r.status == 200 and (ct.startswith("video") or ct.startswith("application/octet-stream")):
                return url
        except urllib.error.HTTPError as e:
            if e.code not in (403, 404):
                pass
        except Exception:
            pass
    return None


class Handler(BaseHTTPRequestHandler):
    def log_message(self, fmt, *a):
        print("[aivm]", self.address_string(), fmt % a)

    def _json(self, code, obj):
        b = json.dumps(obj, ensure_ascii=False).encode()
        self.send_response(code)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(b)))
        self.end_headers()
        self.wfile.write(b)

    def _upstream_headers(self, cred, extra=None):
        h = {"Sign": SIGN, "Device-Id": cred["device_id"],
             "Ctry-Target": "others", "User-Agent": "okhttp/4.12.0",
             "Authorization": "Bearer " + cred["token"]}
        h.update(extra or {})
        return h

    # ---------- OpenAI 兼容 ----------
    def do_POST(self):
        if self.path.rstrip("/") not in ("/v1/chat/completions", "/v1/videos", "/v1/video/generations"):
            return self._json(404, {"error": {"message": "not found"}})
        try:
            n = int(self.headers.get("Content-Length", 0))
            body = json.loads(self.rfile.read(n) or b"{}")
        except Exception:
            return self._json(400, {"error": {"message": "bad json"}})

        prompt = body.get("prompt") or " ".join(
            m.get("content", "") for m in body.get("messages", []) if m.get("role") == "user") or "a cat"
        ratio = body.get("ratio") or body.get("aspect_ratio") or "9:16"
        resolution = body.get("resolution") or "480p"
        ai_sound = 1 if body.get("sound") else 0
        wait = body.get("wait", True)

        try:
            cred = get_credential()
        except Exception as e:
            return self._json(502, {"error": {"message": f"credential: {e}"}})

        payload = json.dumps({"prompt": prompt[:1500], "used": [], "ratio": ratio,
                              "resolution": resolution, "ai_sound": ai_sound}).encode()
        try:
            # 上游同步生成: 阻塞 ~60-120s 直接返回成品 URL (sora.aritek.app)
            r = _req("/api/v1/video/t2v", self._upstream_headers(cred, {"Content-Type": "application/json"}),
                     data=payload, timeout=300)
            gen = json.loads(r.read())
        except urllib.error.HTTPError as e:
            return self._json(e.code, {"error": {"message": e.read().decode()[:300]}})
        except Exception as e:
            return self._json(502, {"error": {"message": f"upstream: {e}"}})

        if gen.get("code") != 0:
            return self._json(502, {"error": {"message": gen.get("message", "upstream reject")}})

        url = gen["data"]["url"]
        return self._json(200, {"id": url, "status": "succeeded", "video_url": url})

    def do_GET(self):
        if self.path.startswith("/demo"):
            return self._demo()
        if self.path.startswith("/v1/models"):
            return self._json(200, {"object": "list", "data": [
                {"id": "aivm-t2v", "object": "model"},
                {"id": "aivm-t2v-hd", "object": "model"}]})
        if self.path.startswith("/health"):
            try:
                cred = get_credential()
                return self._json(200, {"ok": True, "pool": len(_dev_pool),
                                        "device": cred["device_id"][:8]})
            except Exception as e:
                return self._json(200, {"ok": False, "err": str(e)[:200]})
        return self._json(404, {"error": "not found"})

    def _demo(self):
        html = open(os.path.join(os.path.dirname(__file__), "demo.html"), "rb").read()
        self.send_response(200)
        self.send_header("Content-Type", "text/html; charset=utf-8")
        self.send_header("Content-Length", str(len(html)))
        self.end_headers()
        self.wfile.write(html)


if __name__ == "__main__":
    print(f"AI Video Mix 2API demo on :{PORT}, upstream={UPSTREAM}")
    ThreadingHTTPServer(("0.0.0.0", PORT), Handler).serve_forever()
