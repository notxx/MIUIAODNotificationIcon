package com.miui.aod.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Handler;
import com.miui.aod.common.AsyncSensorManager;
import com.miui.aod.doze.DozeMachine.Part;
import com.miui.aod.doze.DozeMachine.Service;
import com.miui.aod.util.wakelock.DelayedWakeLock;
import com.miui.aod.util.wakelock.WakeLock;

public class DozeFactory {
    public DozeMachine assembleMachine(DozeService dozeService) {
        DozeService dozeService2 = dozeService;
        AsyncSensorManager instance = AsyncSensorManager.getInstance((SensorManager) dozeService2.getSystemService(SensorManager.class));
        AlarmManager alarmManager = (AlarmManager) dozeService2.getSystemService(AlarmManager.class);
        Handler handler = new Handler();
        DelayedWakeLock delayedWakeLock = new DelayedWakeLock(handler, WakeLock.createPartial(dozeService2, "Doze"));
        MiuiDozeBrightnessTimeoutAdapter miuiDozeBrightnessTimeoutAdapter = new MiuiDozeBrightnessTimeoutAdapter(dozeService2, MiuiAnimDozeStatePreventingAdapter.wrapIfNeeded(MiuiGxzwDozeStatePreventingAdapter.wrapIfNeeded(dozeService2, dozeService2), dozeService2), alarmManager, handler);
        DozeMachine dozeMachine = new DozeMachine(miuiDozeBrightnessTimeoutAdapter, delayedWakeLock, dozeService2);
        DozePauser dozePauser = new DozePauser(dozeService2, handler, dozeMachine, alarmManager);
        DozeMachine dozeMachine2 = dozeMachine;
        MiuiDozeTimeController miuiDozeTimeController = new MiuiDozeTimeController(dozeService, handler, dozeMachine2, alarmManager, miuiDozeBrightnessTimeoutAdapter);
        DozeTriggers createDozeTriggers = createDozeTriggers(dozeService2, dozeMachine, miuiDozeBrightnessTimeoutAdapter, miuiDozeTimeController);
        Part[] partArr = new Part[7];
        partArr[0] = dozePauser;
        partArr[1] = createDozeTriggers;
        Part[] partArr2 = partArr;
        partArr2[2] = createDozeUi(dozeService, delayedWakeLock, dozeMachine2, handler, alarmManager);
        partArr2[3] = new DozeScreenState(miuiDozeBrightnessTimeoutAdapter, handler);
        MiuiDozeScreenBrightnessController miuiDozeScreenBrightnessController = new MiuiDozeScreenBrightnessController(handler, dozeMachine, alarmManager, miuiDozeBrightnessTimeoutAdapter, instance, createDozeTriggers, dozeService);
        partArr2[4] = miuiDozeScreenBrightnessController;
        partArr2[5] = miuiDozeTimeController;
        partArr2[6] = new MiuiBgController(dozeService2, handler, alarmManager);
        dozeMachine.setParts(partArr2);
        return dozeMachine;
    }

    private DozeTriggers createDozeTriggers(Context context, DozeMachine dozeMachine, Service service, MiuiDozeTimeController miuiDozeTimeController) {
        DozeTriggers dozeTriggers = new DozeTriggers(context, dozeMachine, true, service, miuiDozeTimeController);
        return dozeTriggers;
    }

    private Part createDozeUi(Context context, WakeLock wakeLock, DozeMachine dozeMachine, Handler handler, AlarmManager alarmManager) {
        DozeUi dozeUi = new DozeUi(context, alarmManager, dozeMachine, wakeLock, handler);
        return dozeUi;
    }
}
