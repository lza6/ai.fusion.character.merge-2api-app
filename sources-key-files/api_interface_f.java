package p041e;

import Kb.P;
import Ob.a;
import Ob.g;
import Ob.l;
import Ob.o;
import Ob.q;
import Ob.r;
import Ob.t;
import ai.fusion.character.merge.model.CheckLimit;
import ai.fusion.character.merge.model.CheckNSFWResponse;
import ai.fusion.character.merge.model.CheckStatsusResponse;
import ai.fusion.character.merge.model.GenVideoResponse;
import ai.fusion.character.merge.model.Home;
import ai.fusion.character.merge.model.UserInfo;
import com.unity3d.ads.core.domain.HandleInvocationsFromAdViewer;
import java.util.Map;
import p063fb.L;
import p063fb.y;
import p096i9.e;

/* JADX INFO: loaded from: classes.dex */
public interface f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final e f30379a = e.f30376a;

    @o("api/v1/video/t2v")
    Object a(@a L l10, e<? super GenVideoResponse> eVar);

    @l
    @o("api/v1/video/i2v")
    Object b(@q y yVar, @r Map<String, L> map, e<? super GenVideoResponse> eVar);

    @Ob.f("api/v1/user/check-limit")
    Object c(@t(HandleInvocationsFromAdViewer.KEY_AD_TYPE) String str, e<? super CheckLimit> eVar);

    @Ob.f("api/v1/generate/status")
    Object d(@t("id") String str, e<? super CheckStatsusResponse> eVar);

    @g
    Object e(@Ob.y String str, e<? super P<Void>> eVar);

    @l
    @o("api/v1/generate/check-nsfw")
    Object f(@q y yVar, @r Map<String, L> map, e<? super CheckNSFWResponse> eVar);

    @Ob.f("api/v1/user/info")
    Object g(e<? super UserInfo> eVar);

    @Ob.f("api/v1/home")
    Object h(@t("v") int i5, e<? super Home> eVar);
}
