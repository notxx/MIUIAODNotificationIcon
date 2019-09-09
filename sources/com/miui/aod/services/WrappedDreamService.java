package com.miui.aod.services;

import android.service.dreams.DreamService;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class WrappedDreamService extends DreamService {
    public void setDebug(boolean z) {
        super.setDebug(z);
    }

    public void setWindowless(boolean z) {
        super.setWindowless(z);
    }

    public void setDozeScreenState(int i) {
        super.setDozeScreenState(i);
    }

    public void setDozeScreenBrightness(int i) {
        super.setDozeScreenBrightness(i);
    }

    public void startDozing() {
        super.startDozing();
    }

    /* access modifiers changed from: protected */
    public void dumpOnHandler(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dumpOnHandler(fileDescriptor, printWriter, strArr);
    }
}
