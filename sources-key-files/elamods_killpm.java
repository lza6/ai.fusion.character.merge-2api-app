package com;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import com.mbridge.msdk.MBridgeConstans;
import com.mbridge.msdk.foundation.download.core.DownloadCommon;
import dalvik.system.VMRuntime;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes4.dex */
public class elamods extends Application {
    public static final String URL = "https://github.com/L-JINBIN/ApkSignatureKillerEx";

    public class a {
        private boolean override;
    }

    public final class b {
        private transient int accessFlags;
        private transient int classFlags;
        private transient ClassLoader classLoader;
        private transient int classSize;
        private transient int clinitThreadId;
        private transient Class componentType;
        private transient short copiedMethodsOffset;
        private transient Object dexCache;
        private transient int dexClassDefIndex;
        private volatile transient int dexTypeIndex;
        private transient Object extData;
        private transient long iFields;
        private transient Object[] ifTable;
        private transient long methods;
        private transient String name;
        private transient int numReferenceInstanceFields;
        private transient int numReferenceStaticFields;
        private transient int objectSize;
        private transient int objectSizeAllocFastPath;
        private transient int primitiveType;
        private transient int referenceInstanceOffsets;
        private transient long sFields;
        private transient int status;
        private transient Class superClass;
        private transient short virtualMethodsOffset;
        private transient Object vtable;
    }

    public final class c extends a {
        private int accessFlags;
        private long artMethod;
        private b declaringClass;
        private b declaringClassOfOverriddenMethod;
        private Object[] parameters;
    }

    public final class d {
        private final Member member = null;
        private final f handle = null;
    }

    public final class e {
        private e(Object... objArr) {
            throw new IllegalStateException("Failed to new a instance");
        }

        private static Object invoke(Object... objArr) {
            throw new IllegalStateException("Failed to invoke the method");
        }
    }

    public class f {
        private f cachedSpreadInvoker;
        private MethodType nominalType;
        private final MethodType type = null;
        protected final int handleKind = 0;
        protected final long artFieldOrMethod = 0;
    }

    public final class g extends f {
        private final MethodHandleInfo info = null;
    }

    public final class h {

        /* JADX INFO: renamed from: s, reason: collision with root package name */
        private static int f19518s;

        /* JADX INFO: renamed from: t, reason: collision with root package name */
        private static int f19519t;

        /* JADX INFO: renamed from: i, reason: collision with root package name */
        private int f19520i;

        /* JADX INFO: renamed from: j, reason: collision with root package name */
        private int f19521j;

        private static void a() {
        }

        private static void b() {
        }
    }

    public final /* synthetic */ class i {
    }

    public final class j {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public static final Unsafe f19522a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public static final long f19523b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public static final long f19524c;

        /* JADX INFO: renamed from: d, reason: collision with root package name */
        public static final long f19525d;

        /* JADX INFO: renamed from: e, reason: collision with root package name */
        public static final long f19526e;

        /* JADX INFO: renamed from: f, reason: collision with root package name */
        public static final HashSet f19527f = new HashSet();

        static {
            try {
                Unsafe unsafe = (Unsafe) Unsafe.class.getDeclaredMethod("getUnsafe", null).invoke(null, null);
                f19522a = unsafe;
                f19523b = unsafe.objectFieldOffset(c.class.getDeclaredField("artMethod"));
                unsafe.objectFieldOffset(c.class.getDeclaredField("declaringClass"));
                long jObjectFieldOffset = unsafe.objectFieldOffset(f.class.getDeclaredField("artFieldOrMethod"));
                unsafe.objectFieldOffset(g.class.getDeclaredField("info"));
                long jObjectFieldOffset2 = unsafe.objectFieldOffset(b.class.getDeclaredField("methods"));
                f19524c = jObjectFieldOffset2;
                long jObjectFieldOffset3 = unsafe.objectFieldOffset(b.class.getDeclaredField("iFields"));
                unsafe.objectFieldOffset(b.class.getDeclaredField("sFields"));
                unsafe.objectFieldOffset(d.class.getDeclaredField("member"));
                Method declaredMethod = h.class.getDeclaredMethod("a", null);
                Method declaredMethod2 = h.class.getDeclaredMethod(DownloadCommon.DOWNLOAD_REPORT_FIND_FILE_RESULT_VALUE_B, null);
                declaredMethod.setAccessible(true);
                declaredMethod2.setAccessible(true);
                MethodHandle methodHandleUnreflect = MethodHandles.lookup().unreflect(declaredMethod);
                MethodHandle methodHandleUnreflect2 = MethodHandles.lookup().unreflect(declaredMethod2);
                long j7 = unsafe.getLong(methodHandleUnreflect, jObjectFieldOffset);
                long j10 = unsafe.getLong(methodHandleUnreflect2, jObjectFieldOffset);
                long j11 = unsafe.getLong(h.class, jObjectFieldOffset2);
                long j12 = j10 - j7;
                f19525d = j12;
                f19526e = (j7 - j11) - j12;
                Field declaredField = h.class.getDeclaredField("i");
                Field declaredField2 = h.class.getDeclaredField("j");
                declaredField.setAccessible(true);
                declaredField2.setAccessible(true);
                MethodHandle methodHandleUnreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
                MethodHandle methodHandleUnreflectGetter2 = MethodHandles.lookup().unreflectGetter(declaredField2);
                unsafe.getLong(methodHandleUnreflectGetter, jObjectFieldOffset);
                unsafe.getLong(methodHandleUnreflectGetter2, jObjectFieldOffset);
                unsafe.getLong(h.class, jObjectFieldOffset3);
            } catch (ReflectiveOperationException e10) {
                Log.e("HiddenApiBypass", "Initialize error", e10);
                throw new ExceptionInInitializerError(e10);
            }
        }

        public static Object a(Class cls, Object obj, String str, Object... objArr) throws NoSuchMethodException {
            if (obj != null && !cls.isInstance(obj)) {
                throw new IllegalArgumentException("this object is not an instance of the given class");
            }
            Method declaredMethod = e.class.getDeclaredMethod("invoke", Object[].class);
            declaredMethod.setAccessible(true);
            Unsafe unsafe = f19522a;
            long j7 = unsafe.getLong(cls, f19524c);
            if (j7 == 0) {
                throw new NoSuchMethodException("Cannot find matching method");
            }
            int i5 = unsafe.getInt(j7);
            for (int i10 = 0; i10 < i5; i10++) {
                f19522a.putLong(declaredMethod, f19523b, (((long) i10) * f19525d) + j7 + f19526e);
                if (str.equals(declaredMethod.getName())) {
                    Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                    if (parameterTypes.length == objArr.length) {
                        for (int i11 = 0; i11 < parameterTypes.length; i11++) {
                            if (parameterTypes[i11].isPrimitive()) {
                                Class<?> cls2 = parameterTypes[i11];
                                if ((cls2 != Integer.TYPE || (objArr[i11] instanceof Integer)) && ((cls2 != Byte.TYPE || (objArr[i11] instanceof Byte)) && ((cls2 != Character.TYPE || (objArr[i11] instanceof Character)) && ((cls2 != Boolean.TYPE || (objArr[i11] instanceof Boolean)) && ((cls2 != Double.TYPE || (objArr[i11] instanceof Double)) && ((cls2 != Float.TYPE || (objArr[i11] instanceof Float)) && ((cls2 != Long.TYPE || (objArr[i11] instanceof Long)) && (cls2 != Short.TYPE || (objArr[i11] instanceof Short))))))))) {
                                }
                            } else {
                                Object obj2 = objArr[i11];
                                if (obj2 == null || parameterTypes[i11].isInstance(obj2)) {
                                }
                            }
                        }
                        return declaredMethod.invoke(obj, objArr);
                    }
                    continue;
                }
            }
            throw new NoSuchMethodException("Cannot find matching method");
        }

        public static boolean b(String... strArr) {
            try {
                a(VMRuntime.class, a(VMRuntime.class, null, "getRuntime", new Object[0]), "setHiddenApiExemptions", strArr);
                return true;
            } catch (Throwable th) {
                Log.w("HiddenApiBypass", "setHiddenApiExemptions", th);
                return false;
            }
        }
    }

    public final /* synthetic */ class k {
    }

    public final class l implements Parcelable.Creator {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final /* synthetic */ Parcelable.Creator f19528a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ String f19529b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final /* synthetic */ Signature f19530c;

        public l(Parcelable.Creator creator, String str, Signature signature) {
            this.f19528a = creator;
            this.f19529b = str;
            this.f19530c = signature;
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Signature[] apkContentsSigners;
            PackageInfo packageInfo = (PackageInfo) this.f19528a.createFromParcel(parcel);
            if (packageInfo.packageName.equals(this.f19529b)) {
                Signature[] signatureArr = packageInfo.signatures;
                Signature signature = this.f19530c;
                if (signatureArr != null && signatureArr.length > 0) {
                    signatureArr[0] = signature;
                }
                if (Build.VERSION.SDK_INT >= 28 && packageInfo.signingInfo != null && (apkContentsSigners = packageInfo.signingInfo.getApkContentsSigners()) != null && apkContentsSigners.length > 0) {
                    apkContentsSigners[0] = signature;
                }
            }
            return packageInfo;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i5) {
            return (PackageInfo[]) this.f19528a.newArray(i5);
        }
    }

    static {
        killPM("ai.fusion.character.merge", "MIIFiDCCA3CgAwIBAgIUc508vedL+xZMswAJ0vd/YGiXiLgwDQYJKoZIhvcNAQELBQAwdDELMAkG\nA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDU1vdW50YWluIFZpZXcxFDAS\nBgNVBAoTC0dvb2dsZSBJbmMuMRAwDgYDVQQLEwdBbmRyb2lkMRAwDgYDVQQDEwdBbmRyb2lkMCAX\nDTI1MDMyMDAzMzQ1N1oYDzIwNTUwMzIwMDMzNDU3WjB0MQswCQYDVQQGEwJVUzETMBEGA1UECBMK\nQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEUMBIGA1UEChMLR29vZ2xlIEluYy4x\nEDAOBgNVBAsTB0FuZHJvaWQxEDAOBgNVBAMTB0FuZHJvaWQwggIiMA0GCSqGSIb3DQEBAQUAA4IC\nDwAwggIKAoICAQCtkptKrLugj3YY1Vcbkui4rvPinVgyFfiCTm4yucQkONC0HJbKhTGgEpLVAryD\nfiJUxa8Kb2FBdZ9bzzrsWNp+a3VPqCiMt26ylvLk00YtCNyvCF/UzsjyrFCm2nLsH+5776ZTj+zo\nno5f8OwonmDiPUCakETmPRz2Uh8/FTYsr/AQhEFtcZ+nFpMIRRcbq/5kKrkpZQLTgUlOMKvdwY8A\njTvCv8d3tR9hKatR/DRGOFtX/O65+q/i/Ons8pg7lWAU+qFH7zXKYftB2U1uFvFZgYkjJcyYl/o0\nzn2fzZjGy7LtbiYWPI26jvPi11J4FEdhIkN2ZKaC64JS9EG25MmO4MmNpMNGTZhZYE0J7BR4Ht2B\nCRY0jEFNuFMgMYD8acMK/uF8m+2khknf1LzfoF2VJ2AtDU6UYPh0S9W8/Ad9LpJWPWbl2m2oE7eS\ncO860ugVj7cT1+V+eFjNQsopX1YTBuEKbZRTo+6SoUtkGEtu8qKhna4ckpWNfLzL2Egi/Uay/KNl\n74a/SloVNLCAAJ5bTyiTvzS7KWL2Y4ZnVYyjdc2/MmePoxK7V6ZFtOtMEX6ZHTz8dMXG5aSEMod5\nkajZnOqS+GRA8pncdonUyh6DQkQrFpsCd/o6HzwFJUjaFscOY7mEo0mYNwLsIMO0fxMtssdqXGar\n5PODi8dClhOKWwIDAQABoxAwDjAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBCwUAA4ICAQCYNiRg\nv175V1CvbBrmX+Maofi9yNdlT/y7YWCoeORq3Wd2cxBaCkpfs6YbNO0Zb5FnRc6U9TNTwwi36qKw\nAE3jT/2KwgicS0AcbsmsYp+FnaH9hiBe1MCmGkOS3yk8gj3xVcFBSlNYOLAoxSdF5tiSXVTZAIf2\n/CbYnO3rL6VB9YihAYUorEkGmvjQAkMu8z5aymSMsum2n9TW21080FOeoeRNKTJe+xN5X2SrfQoG\nleEZfE08HC5iQXDLmwX7un36ancN9RlwYk7wYlLGwV5vRY/Q5ROCBuDd0hBvzS8JzFp/GBL8BRlR\nsOGEHPOM2bNI2y7z3zsQEhhu/imK2E4CcCLICBl2l1p9t2dkuduSAqx3QWHm9QPqXi5uiI/Z7cAc\njGMOwGNXuyNs4e9Ub1cTeX/1M5qyjDCI81W6eCmkIri7wnMzY44TcnhPji76tIBSItNp/8b+lo4F\nVqPzBMYtoWQCGsDREr1y+/XMd63fhTKVniwE6BoD/CT3tgdnlgt8eSgGLdmKN5UXffrgR2G4CLj+\nUUxY4rvR+Iys9MeRaZRKs88jgDwrQW8TNIAbBt0kiu8PTP2sUjdm0C7i3spIhNr9TpFDlbNtTSKL\nLOIHSqm7GhPCrlq6RUyj4GYjYkVjsb260yMgodnyZOp6KMLL2Ra0MHnD8OAugcf7kU+YxQ==\n");
        killOpen("ai.fusion.character.merge", "", "", "");
    }

    public static Field a(Class cls, String str) throws NoSuchFieldException {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (NoSuchFieldException e10) {
            while (true) {
                cls = cls.getSuperclass();
                if (cls == null || cls.equals(Object.class)) {
                    break;
                }
                try {
                    Field declaredField2 = cls.getDeclaredField(str);
                    declaredField2.setAccessible(true);
                    return declaredField2;
                } catch (NoSuchFieldException unused) {
                }
            }
            throw e10;
        }
    }

    public static boolean b(String str, String str2) {
        if (str2.startsWith("/") && str2.endsWith(".apk")) {
            String[] strArrSplit = str2.substring(1).split("/", 6);
            int length = strArrSplit.length;
            if (length == 4 || length == 5) {
                if (strArrSplit[0].equals("data") && strArrSplit[1].equals(MBridgeConstans.DYNAMIC_VIEW_WX_APP) && strArrSplit[length - 1].equals("base.apk")) {
                    return strArrSplit[length - 2].startsWith(str);
                }
                if (strArrSplit[0].equals("mnt") && strArrSplit[1].equals("asec") && strArrSplit[length - 1].equals("pkg.apk")) {
                    return strArrSplit[length - 2].startsWith(str);
                }
            } else if (length == 3) {
                if (strArrSplit[0].equals("data") && strArrSplit[1].equals(MBridgeConstans.DYNAMIC_VIEW_WX_APP)) {
                    return strArrSplit[2].startsWith(str);
                }
            } else if (length == 6 && strArrSplit[0].equals("mnt") && strArrSplit[1].equals("expand") && strArrSplit[3].equals(MBridgeConstans.DYNAMIC_VIEW_WX_APP) && strArrSplit[5].equals("base.apk")) {
                return strArrSplit[4].endsWith(str);
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x008c  */
    /* JADX WARN: Code duplicated, block: B:98:0x011e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:29:0x008c, please report this as an issue */
    private static void killOpen(String str, String str2, String str3, String str4) {
        String str5;
        File file;
        if (str2.isEmpty() || str3.isEmpty() || str4.isEmpty()) {
            return;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/self/maps"));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        bufferedReader.close();
                        str5 = null;
                        break;
                    } else {
                        String[] strArrSplit = line.split("\\s+");
                        str5 = strArrSplit[strArrSplit.length - 1];
                        if (b(str, str5)) {
                            bufferedReader.close();
                            break;
                        }
                    }
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            if (str5 == null) {
                System.err.println("Get apk path failed");
                return;
            }
            File file2 = new File(str5);
            String name = Environment.getExternalStorageDirectory().getName();
            if (name.matches("\\d+")) {
                file = new File("/data/user/" + name + "/" + str);
                if (!file.canWrite()) {
                    file = new File("/data/data/" + str);
                }
            } else {
                file = new File("/data/data/" + str);
            }
            File file3 = new File(file, str4);
            file3.getParentFile().mkdirs();
            try {
                ZipFile zipFile = new ZipFile(file2);
                try {
                    ZipEntry entry = zipFile.getEntry(str3);
                    if (entry == null) {
                        throw new RuntimeException("Entry not found: " + str3);
                    }
                    if (!file3.exists() || file3.length() != entry.getSize()) {
                        InputStream inputStream = zipFile.getInputStream(entry);
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file3);
                            try {
                                byte[] bArr = new byte[102400];
                                while (true) {
                                    int i5 = inputStream.read(bArr);
                                    if (i5 == -1) {
                                        break;
                                    } else {
                                        fileOutputStream.write(bArr, 0, i5);
                                    }
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (Throwable th3) {
                                            th.addSuppressed(th3);
                                        }
                                    }
                                    throw th;
                                }
                                fileOutputStream.close();
                                inputStream.close();
                            } catch (Throwable th4) {
                                try {
                                    fileOutputStream.close();
                                } catch (Throwable th5) {
                                    th4.addSuppressed(th5);
                                }
                                throw th4;
                            }
                        } catch (Throwable th6) {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            throw th6;
                        }
                    }
                    zipFile.close();
                    try {
                        System.setProperty("mt.signature.killer.path1", file2.getAbsolutePath());
                        System.setProperty("mt.signature.killer.path2", file3.getAbsolutePath());
                        System.loadLibrary(str2);
                        return;
                    } finally {
                        System.clearProperty("mt.signature.killer.path1");
                        System.clearProperty("mt.signature.killer.path2");
                    }
                } catch (Throwable th7) {
                    try {
                        zipFile.close();
                    } catch (Throwable th8) {
                        th7.addSuppressed(th8);
                    }
                    throw th7;
                }
            } catch (IOException e10) {
                throw new RuntimeException(e10);
            }
            throw new RuntimeException(e10);
        } catch (Exception e11) {
            throw new RuntimeException(e11);
        }
    }

    private static void killPM(String str, String str2) {
        try {
            a(PackageInfo.class, "CREATOR").set(null, new l(PackageInfo.CREATOR, str, new Signature(Base64.decode(str2, 0))));
            if (Build.VERSION.SDK_INT >= 28) {
                HashSet hashSet = j.f19527f;
                hashSet.addAll(Arrays.asList("Landroid/os/Parcel;", "Landroid/content/pm", "Landroid/app"));
                String[] strArr = new String[hashSet.size()];
                hashSet.toArray(strArr);
                j.b(strArr);
            }
            try {
                Object obj = a(PackageManager.class, "sPackageInfoCache").get(null);
                obj.getClass().getMethod("clear", null).invoke(obj, null);
            } catch (Throwable unused) {
            }
            try {
                ((Map) a(Parcel.class, "mCreators").get(null)).clear();
            } catch (Throwable unused2) {
            }
            try {
                ((Map) a(Parcel.class, "sPairedCreators").get(null)).clear();
            } catch (Throwable unused3) {
            }
        } catch (Exception e10) {
            throw new RuntimeException(e10);
        }
    }
}
