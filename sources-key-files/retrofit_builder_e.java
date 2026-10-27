package p041e;

import Kb.C0511j;
import X7.d;
import android.content.SharedPreferences;
import com.google.gson.h;
import com.google.gson.i;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.sql.b;
import com.google.gson.j;
import com.google.gson.p;
import com.google.gson.q;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.l;
import p063fb.A;
import p063fb.B;
import ub.a;
import ub.c;

/* JADX INFO: loaded from: classes.dex */
public final class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ e f30376a = new e();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final B f30377b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final j f30378c;

    static {
        A a4 = new A();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        a4.c(60L, timeUnit);
        a4.b(60L, timeUnit);
        a4.a(new h());
        c cVar = new c();
        cVar.f36982b = a.f36978c;
        a4.a(cVar);
        f30377b = new B(a4);
        Excluder excluder = Excluder.f22723f;
        com.google.gson.a aVar = h.f22717a;
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        i iVar = j.f22891o;
        p pVar = j.f22893q;
        q qVar = j.f22894r;
        ArrayDeque arrayDeque = new ArrayDeque();
        ArrayList arrayList3 = new ArrayList(arrayList2.size() + arrayList.size() + 3);
        arrayList3.addAll(arrayList);
        Collections.reverse(arrayList3);
        ArrayList arrayList4 = new ArrayList(arrayList2);
        Collections.reverse(arrayList4);
        arrayList3.addAll(arrayList4);
        boolean z3 = b.f22886a;
        f30378c = new j(excluder, aVar, new HashMap(map), iVar, 1, new ArrayList(arrayList), new ArrayList(arrayList2), arrayList3, pVar, qVar, new ArrayList(arrayDeque));
    }

    public static f a() {
        d dVar = new d();
        ((ArrayList) dVar.f9761d).add(new C0511j(1));
        j jVar = f30378c;
        if (jVar == null) {
            throw new NullPointerException("gson == null");
        }
        ((ArrayList) dVar.f9760c).add(new Mb.a(jVar, 0));
        SharedPreferences sharedPreferences = g.f30380a;
        if (sharedPreferences == null) {
            l.m("preferences");
            throw null;
        }
        String string = sharedPreferences.getString("api_url", "https://aifusion.aritek.app/");
        l.c(string);
        dVar.a(string);
        B b2 = f30377b;
        Objects.requireNonNull(b2, "client == null");
        dVar.f9758a = b2;
        Object objB = dVar.b().b(f.class);
        l.e(objB, "create(...)");
        return (f) objB;
    }
}
