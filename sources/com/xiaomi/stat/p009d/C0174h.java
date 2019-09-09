package com.xiaomi.stat.p009d;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* renamed from: com.xiaomi.stat.d.h */
public class C0174h {
    /* renamed from: a */
    public static Object m300a(String str) {
        File file = new File(str);
        Object obj = null;
        if (!file.exists()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object readObject = objectInputStream.readObject();
            try {
                objectInputStream.close();
                fileInputStream.close();
                return readObject;
            } catch (IOException e) {
                e = e;
                obj = readObject;
                e.printStackTrace();
                return obj;
            } catch (ClassNotFoundException e2) {
                e = e2;
                obj = readObject;
                e.printStackTrace();
                return obj;
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            return obj;
        } catch (ClassNotFoundException e4) {
            e = e4;
            e.printStackTrace();
            return obj;
        }
    }

    /* renamed from: a */
    public static void m301a(Object obj, String str) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(str)));
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
