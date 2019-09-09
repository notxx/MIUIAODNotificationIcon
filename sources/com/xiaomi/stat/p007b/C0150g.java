package com.xiaomi.stat.p007b;

import com.xiaomi.stat.p009d.C0177k;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.xiaomi.stat.b.g */
public class C0150g {
    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public HashMap<String, String> mo2405a(String str, JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        if (optJSONObject != null) {
            HashMap<String, String> hashMap = new HashMap<>();
            StringBuilder sb = new StringBuilder();
            sb.append("parse the map contains key:");
            sb.append(str);
            C0177k.m319b("RegionManagerHelper", sb.toString());
            Iterator keys = optJSONObject.keys();
            while (keys.hasNext()) {
                try {
                    String str2 = (String) keys.next();
                    String string = optJSONObject.getString(str2);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("[region]:");
                    sb2.append(str2);
                    sb2.append("\n[domain]:");
                    sb2.append(string);
                    C0177k.m319b("RegionManagerHelper", sb2.toString());
                    hashMap.put(str2, string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return hashMap;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("can not find the specific key");
        sb3.append(str);
        C0177k.m323d("RegionManagerHelper", sb3.toString());
        return null;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public HashMap<String, String> mo2406a(HashMap<String, String> hashMap, HashMap<String, String> hashMap2) {
        HashMap<String, String> hashMap3 = new HashMap<>();
        if (hashMap2 != null) {
            hashMap3.putAll(hashMap2);
        }
        Set<String> keySet = hashMap.keySet();
        Set keySet2 = hashMap3.keySet();
        for (String str : keySet) {
            if (!keySet2.contains(str)) {
                hashMap3.put(str, hashMap.get(str));
            }
        }
        return hashMap3;
    }
}
