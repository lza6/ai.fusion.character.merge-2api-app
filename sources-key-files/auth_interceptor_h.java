package p041e;

import android.content.SharedPreferences;
import com.bumptech.glide.c;
import kotlin.jvm.internal.l;
import p063fb.D;
import p063fb.E;
import p063fb.InterfaceC1956v;
import p063fb.N;
import p063fb.w;
import p121kb.f;

/* JADX INFO: loaded from: classes.dex */
public final class h implements w {
    @Override // p063fb.w
    public final N intercept(InterfaceC1956v interfaceC1956v) {
        f fVar = (f) interfaceC1956v;
        D dA = fVar.f33956e.a();
        dA.a("Sign", c.f18870a);
        SharedPreferences sharedPreferences = g.f30380a;
        if (sharedPreferences == null) {
            l.m("preferences");
            throw null;
        }
        String string = sharedPreferences.getString("pt_", "");
        if (string == null) {
            string = "";
        }
        dA.a("PT", string);
        SharedPreferences sharedPreferences2 = g.f30380a;
        if (sharedPreferences2 == null) {
            l.m("preferences");
            throw null;
        }
        String str = "others";
        String string2 = sharedPreferences2.getString("ctry_target", "others");
        if (string2 != null) {
            str = string2;
        }
        dA.a("Ctry-Target", str);
        SharedPreferences sharedPreferences3 = g.f30380a;
        if (sharedPreferences3 == null) {
            l.m("preferences");
            throw null;
        }
        String string3 = sharedPreferences3.getString("user_token", "");
        if (string3 == null) {
            string3 = "";
        }
        dA.a("Authorization", "Bearer ".concat(string3));
        SharedPreferences sharedPreferences4 = g.f30380a;
        if (sharedPreferences4 == null) {
            l.m("preferences");
            throw null;
        }
        String string4 = sharedPreferences4.getString("device_id", "");
        String str2 = string4 != null ? string4 : "";
        if (str2.length() > 0) {
            dA.a("Device-Id", str2);
        }
        return fVar.b(new E(dA));
    }
}
