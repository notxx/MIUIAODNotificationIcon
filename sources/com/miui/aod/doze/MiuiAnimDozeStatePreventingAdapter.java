package com.miui.aod.doze;

import android.content.Context;
import com.miui.aod.AODApplication;
import com.miui.aod.Utils;
import com.miui.aod.doze.DozeMachine.Service;
import com.miui.aod.doze.DozeMachine.Service.Delegate;

public class MiuiAnimDozeStatePreventingAdapter extends Delegate {
    private Context mContext;

    private MiuiAnimDozeStatePreventingAdapter(Service service, Context context) {
        super(service);
        this.mContext = context;
    }

    public static Service wrapIfNeeded(Service service, Context context) {
        return Utils.isAodAnimateEnable(context) ? new MiuiAnimDozeStatePreventingAdapter(service, context) : service;
    }

    public void setDozeScreenState(int i) {
        if ((i != 2 && i != 3 && i != 4) || AODApplication.getHost().isAnimateShowing() || !Utils.isAodClockDisable(this.mContext)) {
            super.setDozeScreenState(i);
        }
    }
}
