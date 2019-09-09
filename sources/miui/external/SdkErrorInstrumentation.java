package miui.external;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import java.lang.reflect.Field;
import miui.external.SdkConstants.SdkError;

final class SdkErrorInstrumentation extends Instrumentation implements SdkConstants {
    private SdkError mError;

    private SdkErrorInstrumentation(SdkError sdkError) {
        this.mError = sdkError;
    }

    static void handleSdkError(SdkError sdkError) {
        Field[] declaredFields;
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field declaredField = getDeclaredField(cls, invoke, (Instrumentation) cls.getMethod("getInstrumentation", new Class[0]).invoke(invoke, new Object[0]), null, null);
            Instrumentation instrumentation = (Instrumentation) declaredField.get(invoke);
            SdkErrorInstrumentation sdkErrorInstrumentation = new SdkErrorInstrumentation(sdkError);
            for (Class<Instrumentation> cls2 = Instrumentation.class; cls2 != null; cls2 = cls2.getSuperclass()) {
                for (Field field : cls2.getDeclaredFields()) {
                    field.setAccessible(true);
                    field.set(sdkErrorInstrumentation, field.get(instrumentation));
                }
            }
            declaredField.set(invoke, sdkErrorInstrumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Field getDeclaredField(Class<?> cls, Object obj, Object obj2, String str, Class<?> cls2) throws NoSuchFieldException {
        Field[] declaredFields = cls.getDeclaredFields();
        if (!(obj == null || obj2 == null)) {
            int length = declaredFields.length;
            int i = 0;
            while (i < length) {
                Field field = declaredFields[i];
                field.setAccessible(true);
                try {
                    if (field.get(obj) == obj2) {
                        return field;
                    }
                    i++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (str != null) {
            for (Field field2 : declaredFields) {
                if (field2.getName().equals(str)) {
                    field2.setAccessible(true);
                    return field2;
                }
            }
        }
        Field field3 = null;
        if (cls2 == null) {
            for (Field field4 : declaredFields) {
                if (field4.getType() == cls2 || field4.getType().isInstance(cls2)) {
                    if (field3 == null) {
                        field3 = field4;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("More than one matched field found: ");
                        sb.append(field3.getName());
                        sb.append(" and ");
                        sb.append(field4.getName());
                        throw new NoSuchFieldException(sb.toString());
                    }
                }
            }
            if (field3 != null) {
                field3.setAccessible(true);
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("No such field found of value ");
                sb2.append(obj2);
                throw new NoSuchFieldException(sb2.toString());
            }
        }
        return field3;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r15v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.app.Activity newActivity(java.lang.Class r15, android.content.Context r16, android.os.IBinder r17, android.app.Application r18, android.content.Intent r19, android.content.pm.ActivityInfo r20, java.lang.CharSequence r21, android.app.Activity r22, java.lang.String r23, java.lang.Object r24) throws java.lang.InstantiationException, java.lang.IllegalAccessException {
        /*
            r14 = this;
            java.lang.String r0 = r15.getSimpleName()
            java.lang.String r1 = "SdkError"
            boolean r0 = r0.startsWith(r1)
            if (r0 != 0) goto L_0x0023
            java.lang.Class<miui.external.SdkErrorActivity> r0 = miui.external.SdkErrorActivity.class
            if (r19 != 0) goto L_0x0016
            android.content.Intent r1 = new android.content.Intent
            r1.<init>()
            goto L_0x0018
        L_0x0016:
            r1 = r19
        L_0x0018:
            java.lang.String r2 = "com.miui.sdk.error"
            r3 = r14
            miui.external.SdkConstants$SdkError r4 = r3.mError
            r1.putExtra(r2, r4)
            r4 = r0
            r8 = r1
            goto L_0x0027
        L_0x0023:
            r3 = r14
            r4 = r15
            r8 = r19
        L_0x0027:
            r3 = r14
            r5 = r16
            r6 = r17
            r7 = r18
            r9 = r20
            r10 = r21
            r11 = r22
            r12 = r23
            r13 = r24
            android.app.Activity r0 = super.newActivity(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: miui.external.SdkErrorInstrumentation.newActivity(java.lang.Class, android.content.Context, android.os.IBinder, android.app.Application, android.content.Intent, android.content.pm.ActivityInfo, java.lang.CharSequence, android.app.Activity, java.lang.String, java.lang.Object):android.app.Activity");
    }

    public Activity newActivity(ClassLoader classLoader, String str, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (!str.startsWith("SdkError")) {
            str = SdkErrorActivity.class.getName();
            if (intent == null) {
                intent = new Intent();
            }
            intent.putExtra("com.miui.sdk.error", this.mError);
        }
        return super.newActivity(classLoader, str, intent);
    }
}
