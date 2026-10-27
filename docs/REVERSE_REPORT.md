# AI Video Mix v1.2.2 (b22) 逆向报告

> 目标: `AI Video Mix v1.2.2 b22 (Premium by @ElaMods).apk` (79.5MB)
> 包名: `ai.fusion.character.merge` · versionCode 22 · ElaMods 修改版（内嵌 ApkSignatureKillerEx）
> 工作目录: `D:/apk-analysis/ai-video-mix/` · 分析日期: 2026-10-28

## 一、结论速览

| 项 | 结论 |
|---|---|
| 上游 | `https://aifusion.aritek.app/`（aritek 系，与 AIVG 1.8.8 同家族） |
| 协议 | 明文 JSON over HTTPS，无加密无签名算法 |
| 鉴权 | 4 头：`Sign` + `Device-Id` + `Ctry-Target` + `Authorization: Bearer <JWT>` |
| Sign | **原版 APK 证书 SHA1** = `3325ecccfae4a0c4ee7eed0a4043daac58c51bec`（mod 用 killPM 伪造，值藏在 `com/elamods.java` killPM 调用的 base64 证书里） |
| 免费额度 | 每随机 device_id：t2v 10 次/天、t2i 3 次/天；i2v 新设备 0（IP 维度 429 全局限流） |
| 生成模式 | 同步阻塞 ~5-110s，响应直接给成品 mp4 URL（`sora.aritek.app` 或 `cdnaif.aritek.app`）；高并发时上游排队，客户端需按 URL HEAD 轮询就绪 |
| CDN UA 墙 | sora/cdnaif CDN 对非 `okhttp` UA 一律 403（python 默认 UA / curl 部分场景被拒），下载必须带 `User-Agent: okhttp/4.12.0` |
| 本地代理坑 | v2ray (127.0.0.1:10808) 高并发下 TLS UNEXPECTED_EOF，非上游风控；测试/部署需 NO_PROXY 直连 |
| 状态 | ✅ 全链路打通，demo + 压测已交付 |

## 二、API 端点清单（全部已实测）

### 1. GET /api/v1/user/info — 换号（免费 token 签发机）
```
GET /api/v1/user/info
Sign: 3325ecccfae4a0c4ee7eed0a4043daac58c51bec
Device-Id: <任意32位hex>
Ctry-Target: others
Authorization: Bearer x        ← 值随意，头必须存在
```
响应：`{"code":0,"data":{"token":"<JWT ~1h>","ttl":...}}`
JWT payload 自带 device_id/sign/ctry_target，`user_type":"guest"`。

### 2. GET /api/v1/user/check-limit?type=t2v — 查额度
注意：`type` 是 **query 参数**（jadx 中 `@t("type")` 注解误导为 Header；发 Header 一律 400）。
响应：`{"allowed":true,"remaining":10,"total_limit":10}`
类型：`t2v`(10/天) `i2v`(新设备0) `t2i`(3/天) `i2i`(新设备0)；`enhance`/`faceswap` → HTTP 402（付费专属）。

### 3. POST /api/v1/video/t2v — 文生视频（核心）
```json
{"prompt":"...", "used":[], "ratio":"9:16|16:9|1:1", "resolution":"480p|720p", "ai_sound":0}
```
同步阻塞 60-120s，返回：
`{"code":0,"data":{"url":"https://sora.aritek.app/generated/t2p_…_<uuid>.mp4"}}`
mp4 直接 GET 可下载（实测 3.2MB，Content-Type: video/mp4）。

### 4. POST /api/v1/video/i2v — 图生视频（multipart）
字段：`image`(file) + `prompt` + `aspect_ratio` + `resolution` + `ai_sound`。
新设备免费额度 0；同 IP 高频触发 429 `code:-22 retry_after~1159s`（IP 维度限流）。

### 5. GET /api/v1/generate/status?id=xxx — 状态查询（遗留端点，实测恒返回 "Completed"）
### 6. GET /api/v1/home?v=<versionCode> — 首页模板/ trending（无需鉴权即可访问）
### 7. POST /api/v1/generate/check-nsfw — NSFW 预检（multipart: image + prompt）

## 三、鉴权链还原（jadx）

- `p041e/e.java` — Retrofit 构建，baseUrl 可被 SP `api_url` 覆盖，默认 aifusion
- `p041e/h.java` — OkHttp 拦截器：注入 Sign/PT/Ctry-Target/Authorization/Device-Id 五头
  - `PT` = Google Play purchaseToken（付费用户才有，guest 空）
- `p062f9/l.java K(Context)` — Sign 值 = `SHA1(packageInfo.signatures[0].toBytes())` hex 小写
- `com/elamods.java` — ElaMods 壳：`killPM("ai.fusion.character.merge", "<base64 原版证书>")`
  通过 hook PackageManager.CREATOR 返回伪造 Signature → app 读到的 SHA1 即原版值
- `p211u/C.java` — 生成前流程：查 DB 最近任务 → user/info 换 token → check-limit → 放行
- `p211u/m.java` — t2v/i2v 请求构造（GenT2VRequest Gson 序列化）
- `ai/fusion/character/merge/service/CheckVideoWorker.java` — 轮询 worker：本版实际用 HEAD CDN URL 判完成
- `android_id` → `Device-Id`（`p020c/b.java`，Settings.Secure.ANDROID_ID）

## 四、限流与风控

| 维度 | 表现 |
|---|---|
| device_id | t2v 10/天、t2i 3/天；随机 uuid.hex 即新设备，**可无限换号** |
| IP | 全局计数，同 IP 大量新设备 + 生成请求 → 429 code:-22 (retry_after ~19min) |
| JWT | ~1h 过期，换号即重签，无绑定校验 |
| enhance/faceswap | 服务端硬付费墙 (HTTP 402)，免费路线不可绕 |

## 五、交付物（本仓库）

| 文件 | 说明 | 验证 |
|---|---|---|
|  `demo/demo_server.py` | OpenAI 兼容 2API（:8913），自动 device 池换号 | /health /v1/models ✅ |
|  `demo/demo.html` | 网页 demo（提示词→视频播放） | HTTP 200 ✅ |
|  `tools/stress_test.py` | 并发压测脚本 | 见压测记录 |
| `cert.der` / `cert.b64` | 原版证书（Sign 来源） | SHA1 已验算 ✅ |
| `jadx/` | 全量反编译源码 | — |

## 六、new-api 渠道接入建议

- 渠道形态：task 平台（异步适配同步上游：提交即阻塞拉结果）
- 模型：`aivm-t2v`（480p/720p × 9:16/16:9/1:1）
- 换号策略：device_id 池 + check-limit 预检，429 时退避 ~20min 或切出口 IP（共享代理池可直接复用）
- 计费：按次；成本=0（guest 额度），风险=IP 限流
