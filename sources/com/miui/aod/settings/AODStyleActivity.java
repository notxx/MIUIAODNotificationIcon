package com.miui.aod.settings;

import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.widget.AODSettings;
import java.util.HashMap;
import java.util.TimeZone;
import miui.app.Activity;
import miui.date.Calendar;
import miui.view.animation.SineEaseInOutInterpolator;

public class AODStyleActivity extends Activity {
    /* access modifiers changed from: private */
    public boolean m24HourFormat;
    /* access modifiers changed from: private */
    public AODStyleController mAodStyleController;
    private ImageView mBackIcon;
    private Calendar mCal;
    private FrameLayout mContent;
    private boolean mDualClock;
    private GridView mGridView;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public ImageAdapter mImageAdapter;
    /* access modifiers changed from: private */
    public LayoutInflater mInflater;
    private int mMSecond;
    /* access modifiers changed from: private */
    public HashMap<String, StyleControllerViewPair> mName2ViewMap = new HashMap<>();
    private boolean mPreviewMode = false;
    private int mSecond;
    /* access modifiers changed from: private */
    public String mSelectItemName;
    /* access modifiers changed from: private */
    public Runnable mUpdateRunnable;

    class ImageAdapter extends BaseAdapter {
        public Object getItem(int i) {
            return null;
        }

        public long getItemId(int i) {
            return 0;
        }

        ImageAdapter() {
        }

        public int getCount() {
            return AODSettings.getClockStylesSize(AODStyleActivity.this.getBaseContext());
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2;
            String aodStyleName = AODSettings.getAodStyleName(AODStyleActivity.this.getBaseContext(), i);
            if (AODStyleActivity.this.mName2ViewMap.get(aodStyleName) == null) {
                view2 = AODStyleActivity.this.mInflater.inflate(R.layout.aod_type_settings, null);
                AODStyleController aODStyleController = new AODStyleController(2, AODStyleActivity.this.mInflater);
                aODStyleController.inflateView((FrameLayout) view2.findViewById(R.id.aod_style_item), aodStyleName);
                AODStyleActivity.this.mName2ViewMap.put(aodStyleName, new StyleControllerViewPair(aODStyleController, view2));
            } else {
                view2 = ((StyleControllerViewPair) AODStyleActivity.this.mName2ViewMap.get(aodStyleName)).mAodView;
            }
            FrameLayout frameLayout = (FrameLayout) view2.findViewById(R.id.aod_style_item);
            ((StyleControllerViewPair) AODStyleActivity.this.mName2ViewMap.get(aodStyleName)).mStyleController.handleUpdateTime(AODStyleActivity.this.m24HourFormat);
            frameLayout.findViewById(R.id.aod_style_item).setForeground(AODStyleActivity.this.getDrawable(AODStyleActivity.this.mSelectItemName.equals(aodStyleName) ? R.drawable.aod_style_fg_p : R.drawable.aod_style_fg_n));
            frameLayout.setTag(aodStyleName);
            view2.setTag(aodStyleName);
            return view2;
        }
    }

    class StyleControllerViewPair {
        /* access modifiers changed from: private */
        public View mAodView;
        /* access modifiers changed from: private */
        public AODStyleController mStyleController;

        public StyleControllerViewPair(AODStyleController aODStyleController, View view) {
            this.mStyleController = aODStyleController;
            this.mAodView = view;
        }
    }

    public void onCreate(Bundle bundle) {
        AODStyleActivity.super.onCreate(bundle);
        this.mInflater = getLayoutInflater();
        this.mAodStyleController = new AODStyleController(1, this.mInflater);
        this.mCal = new Calendar(TimeZone.getDefault());
        this.mSecond = this.mCal.get(21);
        this.mMSecond = this.mCal.get(22);
        Window window = getWindow();
        window.clearFlags(201326592);
        window.getDecorView().setSystemUiVisibility(1280);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
        window.setNavigationBarColor(-16777216);
        setContentView(R.layout.aod_style);
        findViewById(R.id.aod_style_root).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                AODStyleActivity.this.showPreviewMode(!AODStyleActivity.this.mPreviewMode);
            }
        });
        this.mContent = (FrameLayout) findViewById(R.id.aod_content);
        this.mBackIcon = (ImageView) findViewById(R.id.aod_back);
        this.mBackIcon.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                AODStyleActivity.this.finish();
            }
        });
        this.mGridView = (GridView) findViewById(R.id.gridview);
        if (AODSettings.isDualClock(getApplicationContext())) {
            this.mGridView.setStretchMode(2);
        }
        this.mImageAdapter = new ImageAdapter();
        this.mGridView.setAdapter(this.mImageAdapter);
        init();
        this.mHandler = new Handler();
        int i = ((60 - this.mSecond) * 1000) + (1000 - this.mMSecond);
        this.mUpdateRunnable = new Runnable() {
            public void run() {
                AODStyleActivity.this.mAodStyleController.handleUpdateTime(AODStyleActivity.this.m24HourFormat);
                AODStyleActivity.this.mImageAdapter.notifyDataSetChanged();
                AODStyleActivity.this.mHandler.postDelayed(AODStyleActivity.this.mUpdateRunnable, 60000);
            }
        };
        this.mHandler.postDelayed(this.mUpdateRunnable, (long) i);
    }

    public void finish() {
        AODStyleActivity.super.finish();
        appointExitAnim();
    }

    /* access modifiers changed from: private */
    public void showPreviewMode(boolean z) {
        this.mBackIcon.setVisibility(z ? 4 : 0);
        if (!z) {
            this.mGridView.clearAnimation();
            this.mGridView.setEnabled(true);
            this.mGridView.animate().alpha(1.0f).setInterpolator(new SineEaseInOutInterpolator()).setDuration(200).start();
        } else {
            this.mGridView.clearAnimation();
            this.mGridView.setEnabled(false);
            this.mGridView.animate().alpha(0.0f).setInterpolator(new SineEaseInOutInterpolator()).setDuration(200).start();
        }
        this.mPreviewMode = z;
    }

    /* access modifiers changed from: protected */
    public void appointExitAnim() {
        TypedArray obtainStyledAttributes = obtainStyledAttributes(null, new int[]{16842938, 16842939}, 16842926, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        int resourceId2 = obtainStyledAttributes.getResourceId(1, -1);
        obtainStyledAttributes.recycle();
        if (resourceId != -1 && resourceId2 != -1) {
            overridePendingTransition(resourceId, resourceId2);
        }
    }

    public void onResume() {
        AODStyleActivity.super.onResume();
        init();
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [android.content.Context, com.miui.aod.settings.AODStyleActivity] */
    private void init() {
        this.m24HourFormat = DateFormat.is24HourFormat(this);
        this.mAodStyleController.handleUpdateTime(this.m24HourFormat);
        this.mSelectItemName = AODSettings.getAodStyleName(getBaseContext());
        this.mDualClock = AODSettings.isDualClock(this);
        this.mGridView.setNumColumns(this.mDualClock ? 3 : 4);
        this.mGridView.setSelector(new ColorDrawable(0));
        this.mGridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                AODStyleActivity.this.updateAODStyle((String) view.getTag());
            }
        });
        this.mImageAdapter.notifyDataSetChanged();
        updateAODStyle(this.mSelectItemName);
    }

    /* access modifiers changed from: private */
    public void updateAODStyle(String str) {
        if (!TextUtils.isEmpty(this.mSelectItemName) && !this.mSelectItemName.equals(str)) {
            View findView = findView(this.mSelectItemName);
            if (findView != null) {
                findView.findViewById(R.id.aod_style_item).setForeground(getDrawable(R.drawable.aod_style_fg_n));
            }
            this.mSelectItemName = str;
            Secure.putString(getBaseContext().getContentResolver(), this.mDualClock ? AODSettings.AOD_STYLE_NAME_DUAL : AODSettings.AOD_STYLE_NAME, this.mSelectItemName);
        }
        View findView2 = findView(str);
        if (findView2 != null) {
            findView2.findViewById(R.id.aod_style_item).setForeground(getDrawable(R.drawable.aod_style_fg_p));
        }
        this.mAodStyleController.inflateView(getWindow().getDecorView(), str);
        this.mAodStyleController.handleUpdateTime(this.m24HourFormat);
    }

    private View findView(String str) {
        if (this.mGridView.getChildCount() > 0) {
            for (int i = 0; i < this.mGridView.getChildCount(); i++) {
                View childAt = this.mGridView.getChildAt(i);
                if (str.equals((String) childAt.getTag())) {
                    return childAt;
                }
            }
        }
        return null;
    }

    public void onDestroy() {
        AODStyleActivity.super.onDestroy();
        this.mHandler.removeCallbacks(this.mUpdateRunnable);
    }
}
