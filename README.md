# AI Video Mix 2API — `ai.fusion.character.merge` 逆向交付

> 目标 APK：`AI Video Mix v1.2.2 b22 (Premium by @ElaMods)`（79.5MB）
> 上游：`https://aifusion.aritek.app/`（aritek 系，明文 JSON 无加密）
> 状态：**✅ 全链路打通** — 端到端真出片已验证（单发 103s、3 并发压测 3/3）

## 状态总览

| 能力 | 状态 | 说明 |
|---|---|---|
| t2v 文生视频 | ✅ 可用 | 同步阻塞 5~110s 直接返回成品 mp4 URL |
| t2i 文生图 | ✅ 额度 3/天 | 端点同族未深度接（demo 未含） |
| i2v 图生视频 | ⚠️ 新设备 0 额度 | multipart 协议已还原，需付费设备或有存量额度 |
| 换号机制 | ✅ 自动 | 任意 hex device_id = 新设备，`user/info` 免费签 JWT |
| enhance/faceswap | ❌ 硬付费墙 | HTTP 402，服务端校验，不可绕 |

## 逆向要点（TL;DR）

1. **Sign 头 = 原版 APK 证书 SHA1**：`3325ecccfae4a0c4ee7eed0a4043daac58c51bec`。
   本 mod 用 [ApkSignatureKillerEx](https://github.com/L-JINBIN/ApkSignatureKillerEx) 伪装，
   原版证书 base64 内嵌在 `com/elamods.java` 的 `killPM(pkg, base64)` 调用里 ——
   从 jadx 源码直接取 base64 → 解码 → SHA1，**零动态抓包**即可还原鉴权。
2. **鉴权四头**：`Sign`（证书 SHA1）+ `Device-Id`（任意 hex）+ `Ctry-Target`（`others`）
   + `Authorization: Bearer <JWT>`（`GET /api/v1/user/info` 空 Bearer 免费签发，~1h）。
3. **check-limit 的 `type` 是 query 参数**（jadx `@t` 注解混淆误导为 Header，发 Header 一律 400）。
4. **CDN UA 墙**：`sora.aritek.app` / `cdnaif.aritek.app` 对非 `okhttp` UA 一律 403，
   下载必须带 `User-Agent: okhttp/4.12.0`。
5. **免费额度**：t2v 10 次/天、t2i 3 次/天 per device_id；IP 维度全局限流
   429 `code:-22`（retry_after ~19min）。

## 快速开始

```bash
cd demo
python demo_server.py          # :8913, OpenAI 兼容 + 网页 demo
# 浏览器打开 http://127.0.0.1:8913/demo
# API:
curl http://127.0.0.1:8913/health
curl -X POST http://127.0.0.1:8913/v1/chat/completions \
  -H "Content-Type: application/json" \
  -d '{"prompt":"a cat surfing a wave","ratio":"9:16","resolution":"480p"}'
```

```bash
# 压测（直连，勿走本地代理）
NO_PROXY="*" python tools/stress_test.py 3
```

## 目录结构

```
demo/demo_server.py      OpenAI 兼容 2API（device 池自动换号 + 429 处理）
demo/demo.html           网页 demo（提示词 → 视频播放）
tools/stress_test.py     并发压测（成功率/耗时统计，HEAD 轮询排队 URL）
tools/hook_aivm.js       Frida 17 验证脚本（五头注入 + SHA1 计算点）
sources-key-files/       jadx 关键还原源文件
  ├── elamods_killpm.java      原版证书 base64 所在（Sign 值来源）
  ├── auth_interceptor_h.java  OkHttp 五头注入拦截器
  ├── retrofit_builder_e.java  baseUrl 与 Retrofit 构建
  ├── api_interface_f.java     全部 7 个端点声明
  └── original_cert.der        原版签名证书（SHA1 已验算）
```

## API 端点清单（全部实测）

| 端点 | 方法 | 说明 |
|---|---|---|
| `/api/v1/user/info` | GET | 空Bearer → 免费 guest JWT（换号核心） |
| `/api/v1/user/check-limit?type=t2v` | GET | 额度查询（**query 参数**） |
| `/api/v1/video/t2v` | POST | JSON `{prompt,used[],ratio,resolution,ai_sound}` → 同步回 mp4 URL |
| `/api/v1/video/i2v` | POST | multipart `image+prompt+aspect_ratio+resolution+ai_sound` |
| `/api/v1/generate/check-nsfw` | POST | multipart 预检 |
| `/api/v1/generate/status?id=` | GET | 遗留端点，恒返回 Completed |
| `/api/v1/home?v=22` | GET | 模板/trending（低鉴权） |

详细字段、响应样例与完整鉴权链分析见 [docs/REVERSE_REPORT.md](docs/REVERSE_REPORT.md)。

## new-api 渠道接入建议

- task 型渠道（同步上游适配异步任务语义），模型 `aivm-t2v`
- device_id 池 + check-limit 预检；429 时退避 ~20min 或切出口 IP（可复用共享代理池）

## 免责声明

仅供安全研究与协议学习。请勿用于商业用途或对上游服务造成滥用；
所有免费额度来自上游对 guest 设备的公开策略，使用请自担风险。

---
🤖 Generated with [Claude Code](https://claude.com/claude-code)
