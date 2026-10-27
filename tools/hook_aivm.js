// Frida 17 — AI Video Mix (ai.fusion.character.merge) 协议验证 hook
// 用法: frida -U -f ai.fusion.character.merge -l hook_aivm.js   (或 attach 按 PID)
// 验证点: Sign 头值 / 五头注入 / SHA1 计算
Java.perform(function () {
    // OkHttp 拦截器 — 打印每个上游请求的全部头
    var Interceptor = Java.use("p041e.h");
    Interceptor.intercept.implementation = function (chain) {
        var req = chain.f33956e.a();
        console.log("=== REQUEST " + req.f31165f + " ===");
        var names = req.f31164e();
        for (var i = 0; i < names.size(); i++) {
            var n = names.get(i);
            console.log("  " + n + ": " + req.f31162c(n));
        }
        return this.intercept(chain);
    };
    // SHA1(签名证书) — 确认 Sign 值来源
    var Util = Java.use("p062f9.l");
    Util.K.overload("android.content.Context").implementation = function (ctx) {
        var r = this.K(ctx);
        console.log("[Sign-K] " + r);
        return r;
    };
});
