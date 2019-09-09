package com.miui.aod.widget;

import android.view.View;
import java.util.TimeZone;

public interface IAodClock {
    void bindView(View view);

    int getLayoutResource();

    void setClockMask(int i, int i2);

    void setTimeZone(TimeZone timeZone);

    void setTimeZone2(TimeZone timeZone);

    void updateTime(boolean z);
}
