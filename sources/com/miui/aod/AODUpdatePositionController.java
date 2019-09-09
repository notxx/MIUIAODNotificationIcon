package com.miui.aod;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import com.miui.aod.widget.AODSettings;

public class AODUpdatePositionController {
    private static final String TAG = "AODUpdatePositionController";
    private int mAodMoveCurrent;
    private int mAodMovePeriod;
    private int mExtraTranslationY;
    private boolean mIsDisplayUpdateModeOn;
    private int mTranslationX;
    private int mTranslationY;

    public AODUpdatePositionController(Context context) {
        boolean z = true;
        if (Secure.getInt(context.getContentResolver(), "aod_display_update_mode", 1) != 1) {
            z = false;
        }
        this.mIsDisplayUpdateModeOn = z;
        this.mAodMovePeriod = Secure.getInt(context.getContentResolver(), "aod_move_period", 42);
        this.mTranslationX = context.getResources().getDimensionPixelSize(R.dimen.clock_container_translation_x);
        this.mTranslationY = getClockTranslationY(context);
        this.mAodMoveCurrent = Secure.getInt(context.getContentResolver(), "aod_move_current", 20);
        this.mExtraTranslationY = getTranslationY(context);
    }

    public void updatePosition(View view) {
        if (!this.mIsDisplayUpdateModeOn) {
            Log.e(TAG, "updatePosition() blocking on setting value");
            return;
        }
        this.mAodMoveCurrent %= this.mAodMovePeriod;
        int i = this.mAodMoveCurrent / 2;
        int i2 = i % 3;
        int i3 = i / 3;
        view.setTranslationX((float) (this.mTranslationX * (i2 - 1)));
        view.setTranslationY((float) ((this.mTranslationY * (i3 - 3)) + this.mExtraTranslationY));
        this.mAodMoveCurrent++;
    }

    private int getTranslationY(Context context) {
        if (AODSettings.isSunStyle(context)) {
            return context.getResources().getDimensionPixelOffset(R.dimen.sun_translation_y);
        }
        if (AODSettings.isAnimationClockPanel(context)) {
            return context.getResources().getDimensionPixelOffset(R.dimen.animation_clock_panel_translation_y);
        }
        return 0;
    }

    private int getClockTranslationY(Context context) {
        if (AODSettings.isAnimationClockPanel(context)) {
            return context.getResources().getDimensionPixelOffset(R.dimen.animation_clock_container_translation_y);
        }
        return context.getResources().getDimensionPixelSize(R.dimen.clock_container_translation_y);
    }
}
