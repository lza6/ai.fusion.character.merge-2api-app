#!/usr/bin/env python3
"""AI Video Mix 上游直连压测 — 并发生成统计成功率/耗时
用法: python stress_test.py [并发数]
"""
import urllib.request, ssl, json, uuid, time, threading, queue, sys

ctx = ssl.create_default_context(); ctx.check_hostname=False; ctx.verify_mode=ssl.CERT_NONE
SIGN = "3325ecccfae4a0c4ee7eed0a4043daac58c51bec"
PROMPTS = ["a robot dancing in the rain","a dragon flying over mountains",
           "an astronaut riding a horse","ocean waves at sunset",
           "a puppy playing with a ball","city lights time lapse"]

def call(path, h, data=None, method=None, t=60):
    r = urllib.request.urlopen(urllib.request.Request(
        "https://aifusion.aritek.app"+path, data=data, method=method, headers=h),
        context=ctx, timeout=t)
    return json.loads(r.read())

def mkdev():
    dev = uuid.uuid4().hex
    h = {"Sign": SIGN, "Device-Id": dev, "Ctry-Target": "others",
         "Authorization": "Bearer x", "User-Agent": "okhttp/4.12.0"}
    tok = call("/api/v1/user/info", h)["data"]["token"]
    h["Authorization"] = "Bearer "+tok
    return dev, h

res = queue.Queue()
def worker(i):
    t0 = time.time()
    try:
        dev, h = mkdev()
        body = json.dumps({"prompt": PROMPTS[i%len(PROMPTS)], "used": [],
                           "ratio": "9:16", "resolution": "480p", "ai_sound": 0}).encode()
        g = call("/api/v1/video/t2v", {**h, "Content-Type": "application/json"}, body, t=300)
        if g.get("code") != 0:
            res.put(("fail", i, g.get("message",""), time.time()-t0)); return
        url = g["data"]["url"]
        # CDN 需 okhttp UA；高并发时 cdnaif URL 是排队路径，要轮询等就绪
        for _ in range(30):
            time.sleep(10)
            try:
                r = urllib.request.urlopen(urllib.request.Request(
                    url, method="HEAD", headers={"User-Agent": "okhttp/4.12.0"}),
                    context=ctx, timeout=15)
                if r.status == 200 and r.headers.get("Content-Type", "").startswith("video"):
                    res.put(("ok", i, f"{url} [{r.headers.get('Content-Length')}B]", time.time()-t0))
                    break
            except urllib.error.HTTPError:
                continue
            except Exception:
                continue
        else:
            res.put(("timeout", i, url, time.time()-t0))
    except Exception as e:
        res.put(("err", i, str(e)[:120], time.time()-t0))

N = int(sys.argv[1]) if len(sys.argv) > 1 else 5
t0 = time.time()
ths = [threading.Thread(target=worker, args=(i,)) for i in range(N)]
for t in ths: t.start()
for t in ths: t.join()
ok = fail = 0; durs = []
while not res.empty():
    s, i, d, dt = res.get(); durs.append(dt)
    print(f"{s:8} #{i} {dt:5.0f}s {d[:90]}")
    ok += s == "ok"; fail += s != "ok"
print(f"\n=== 压测: {ok}成功/{fail}失败/{N}总, 平均 {sum(durs)/len(durs):.0f}s, 总 {time.time()-t0:.0f}s")
