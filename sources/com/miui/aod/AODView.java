package com.miui.aod;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.CallLog.Calls;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.aod.util.ContentProviderBinder;
import com.miui.aod.util.ContentProviderBinder.Builder;
import com.miui.aod.util.ContentProviderBinder.QueryCompleteListener;
import com.miui.aod.util.NotificationController;
import com.miui.aod.util.NotificationController.NotificationChangeListener;
import com.miui.aod.utils.CommonUtils;
import com.miui.aod.widget.ClockPanelView;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AODView extends FrameLayout implements QueryCompleteListener {
    private static final String TAG = "AODView";
    private boolean m24HourFormat;
    Runnable mAnimateInvisible = new Runnable() {
        public void run() {
            AODApplication.getHost().setNotificationAnimate(false);
            AODApplication.getHost().fireAnimateState();
        }
    };
    private AODStyleController mAodStyleController;
    private ArrayList<ContentProviderBinder> mBinders = new ArrayList<>();
    private Context mContext;
    private BadgetImageView mFirstIcon;
    /* access modifiers changed from: private */
    public List<String> mIconList;
    private HashMap<String, Integer> mIconMap = new HashMap<>();
    Interpolator mInterpolator = new Interpolator() {
        public float getInterpolation(float f) {
            float f2 = (f / 1.0f) - 1.0f;
            return ((f2 * f2 * f2) + 1.0f) * 1.0f;
        }
    };
    private ImageView mLeftImage;
    protected int mMissCallNum;
    private List<String> mNotificationArray = new ArrayList();
    private AODUpdatePositionController mPosictionController;
    private boolean mRegisteredCallLog = false;
    private ImageView mRightImage;
    private BadgetImageView mSecondIcon;
    protected boolean mShowMissCall;
    private View mTableModeContainer;
    private BadgetImageView mThirdIcon;

    public AODView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.mPosictionController = new AODUpdatePositionController(context);
        this.mIconMap.put("com.tencent.mm", Integer.valueOf(R.drawable.wechat));
        this.mIconMap.put("com.tencent.mobileqq", Integer.valueOf(R.drawable.qq));
        this.mIconMap.put("com.whatsapp", Integer.valueOf(R.drawable.whatsapp));
        this.mIconMap.put("com.facebook.orca", Integer.valueOf(R.drawable.facebookmsg));
        this.mIconMap.put("jp.naver.line.android", Integer.valueOf(R.drawable.line));
        this.mIconMap.put("com.google.android.gm", Integer.valueOf(R.drawable.gmail));
        this.mIconMap.put("com.android.email", Integer.valueOf(R.drawable.mail));
        this.mIconMap.put("com.google.android.calendar", Integer.valueOf(R.drawable.gcalendar));
        this.mIconMap.put("com.android.calendar", Integer.valueOf(R.drawable.calendarbg));
        this.mIconMap.put("com.android.server.telecom", Integer.valueOf(R.drawable.phone));
        this.mIconMap.put("com.android.mms", Integer.valueOf(R.drawable.sms));
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        makeNormalPanel();
        handleUpdateView();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 354) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (keyEvent.getAction() == 0) {
            if (!Utils.isAodClockDisable(getContext()) && Utils.isShowTemporary(getContext())) {
                AODApplication.getHost().fireAodState(true, "reason_keycode_goto");
            }
            AODApplication.getHost().notifyKeycodeGoto();
        }
        return true;
    }

    private void makeNormalPanel() {
        this.mAodStyleController = new AODStyleController(3, LayoutInflater.from(this.mContext));
        this.m24HourFormat = DateFormat.is24HourFormat(this.mContext);
        this.mTableModeContainer = findViewById(R.id.clock_container);
        this.mAodStyleController.inflateClockView(this);
        this.mFirstIcon = (BadgetImageView) findViewById(R.id.first);
        this.mSecondIcon = (BadgetImageView) findViewById(R.id.second);
        this.mThirdIcon = (BadgetImageView) findViewById(R.id.third);
        this.mLeftImage = (ImageView) findViewById(R.id.aod_left_image);
        this.mRightImage = (ImageView) findViewById(R.id.aod_right_image);
    }

    public void handleUpdateView() {
        this.mPosictionController.updatePosition(this.mTableModeContainer);
        handleUpdateTime();
        handleUpdateIcons();
    }

    public void setSunImage(int i) {
        this.mAodStyleController.setSunImage(i, this);
    }

    public void onStartDoze() {
        if (!this.mRegisteredCallLog && Utils.isBootCompleted()) {
            fillMissedCall();
            this.mRegisteredCallLog = true;
            Iterator it = this.mBinders.iterator();
            while (it.hasNext()) {
                ((ContentProviderBinder) it.next()).init();
            }
        }
    }

    public void onStopDoze() {
        if (this.mRegisteredCallLog) {
            Iterator it = this.mBinders.iterator();
            while (it.hasNext()) {
                ((ContentProviderBinder) it.next()).finish();
            }
            this.mBinders.clear();
            this.mRegisteredCallLog = false;
        }
    }

    private String getPkg(int i) {
        if (i < 0 || i >= this.mNotificationArray.size()) {
            return null;
        }
        return (String) this.mNotificationArray.get(i);
    }

    private void bindView(BadgetImageView badgetImageView, int i) {
        String pkg = getPkg(i);
        Integer num = (Integer) this.mIconMap.get(pkg);
        if (num != null) {
            int i2 = 0;
            badgetImageView.setVisibility(0);
            if ("com.android.server.telecom".equals(pkg)) {
                i2 = this.mMissCallNum;
            }
            badgetImageView.setBadget(i2, "com.android.calendar".equals(pkg) ? 1 : 0);
            Drawable drawable = getResources().getDrawable(num.intValue());
            drawable.setAlpha(255);
            badgetImageView.setBackground(drawable);
            return;
        }
        badgetImageView.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void handleUpdateIcons() {
        bindView(this.mFirstIcon, 0);
        bindView(this.mSecondIcon, 1);
        bindView(this.mThirdIcon, 2);
    }

    private void handleUpdateTime() {
        this.mAodStyleController.handleUpdateTime(this.m24HourFormat);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIconList = new ArrayList();
        updateNotificationList();
        NotificationController.getInstance(this.mContext).setListener(new NotificationChangeListener() {
            public void onUpdate(final ArrayList<String> arrayList) {
                AODView.this.mIconList.clear();
                if (arrayList != null && arrayList.size() > 0) {
                    AODView.this.post(new Runnable() {
                        public void run() {
                            AODView.this.showAnimate((String) arrayList.get(0));
                        }
                    });
                    for (int i = 0; i < arrayList.size(); i++) {
                        AODView.this.mIconList.add(arrayList.get(i));
                    }
                }
                AODView.this.updateNotificationList();
            }
        });
    }

    /* access modifiers changed from: private */
    public void showAnimate(String str) {
        if (Utils.isAodAnimateEnable(this.mContext)) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("showAnimate pkg:");
            sb.append(str);
            Log.v(str2, sb.toString());
            AODApplication.getHost().setNotificationAnimate(true);
            AODApplication.getHost().fireAnimateState();
            this.mLeftImage.setImageResource(AODNotificationColor.getColorItem(str).left);
            this.mRightImage.setImageResource(AODNotificationColor.getColorItem(str).right);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setInterpolator(this.mInterpolator);
            alphaAnimation.setDuration(2400);
            this.mRightImage.startAnimation(alphaAnimation);
            this.mLeftImage.startAnimation(alphaAnimation);
            setAnimateInvisible();
        }
    }

    private void setAnimateInvisible() {
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.mAnimateInvisible);
            handler.postDelayed(this.mAnimateInvisible, 2400);
        }
    }

    /* access modifiers changed from: private */
    public void updateNotificationList() {
        if (this.mIconList != null) {
            this.mIconList.sort(new Comparator<String>() {
                public int compare(String str, String str2) {
                    if ("com.android.server.telecom".equals(str)) {
                        return -1;
                    }
                    return "com.android.server.telecom".equals(str2) ? 1 : 0;
                }
            });
            this.mNotificationArray.clear();
            for (String str : this.mIconList) {
                if (this.mIconMap.containsKey(str) && !this.mNotificationArray.contains(str)) {
                    this.mNotificationArray.add(str);
                    if (this.mNotificationArray.size() == 3) {
                        break;
                    }
                }
            }
        }
        post(new Runnable() {
            public void run() {
                AODView.this.handleUpdateIcons();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onQueryCompleted(Uri uri, int i) {
        if (Calls.CONTENT_URI.equals(uri)) {
            this.mShowMissCall = i > 0;
            this.mMissCallNum = i;
        }
        handleUpdateIcons();
    }

    private void fillMissedCall() {
        String[] strArr = {"number"};
        String str = "type=3 AND new=1";
        if (CommonUtils.isProviderAccess(Calls.CONTENT_URI.getAuthority(), -2)) {
            addContentProviderBinder(Calls.CONTENT_URI).setColumns(strArr).setWhere(str);
        }
    }

    public Builder addContentProviderBinder(Uri uri) {
        ContentProviderBinder contentProviderBinder = new ContentProviderBinder(this.mContext);
        contentProviderBinder.setQueryCompleteListener(this);
        contentProviderBinder.setUri(uri);
        this.mBinders.add(contentProviderBinder);
        return new Builder(contentProviderBinder);
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            this.m24HourFormat = DateFormat.is24HourFormat(this.mContext);
        }
    }

    public void clearClockPanelAnimation() {
        View findViewById = findViewById(R.id.clock_panel);
        if (findViewById != null && (findViewById instanceof ClockPanelView)) {
            ((ClockPanelView) findViewById).clearAnimationView();
        }
    }

    public void startClockPanelAnimation() {
        View findViewById = findViewById(R.id.clock_panel);
        if (findViewById != null && (findViewById instanceof ClockPanelView)) {
            ((ClockPanelView) findViewById).startAnimation();
        }
    }
}
