package com.miui.aod.settings;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceCategory;
import android.provider.Settings.Secure;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.widget.AODSettings;
import miui.app.AlertDialog.Builder;
import miui.app.TimePickerDialog;
import miui.app.TimePickerDialog.OnTimeSetListener;
import miui.preference.PreferenceFragment;
import miui.preference.ValuePreference;
import miui.widget.TimePicker;

public class AODFragment extends PreferenceFragment implements OnPreferenceChangeListener, OnPreferenceClickListener {
    /* access modifiers changed from: private */
    public ValuePreference mAodEndPref;
    private PreferenceCategory mAodShowPref;
    /* access modifiers changed from: private */
    public ValuePreference mAodStartPref;
    /* access modifiers changed from: private */
    public Context mContext;
    private CheckBoxPreference mEnableAODPref;
    /* access modifiers changed from: private */
    public int mEndTime;
    private OnTimeSetListener mOnTimeSetListener = new OnTimeSetListener() {
        public void onTimeSet(TimePicker timePicker, int i, int i2) {
            if (!AODFragment.this.mTimeFlag) {
                AODFragment.this.mStartTime = (i * 60) + i2;
                AODFragment.this.mAodStartPref.setValue(String.format("%02d:%02d", new Object[]{Integer.valueOf(AODFragment.this.mStartTime / 60), Integer.valueOf(AODFragment.this.mStartTime % 60)}));
                Secure.putInt(AODFragment.this.mContext.getContentResolver(), "aod_start", AODFragment.this.mStartTime);
                return;
            }
            AODFragment.this.mEndTime = (i * 60) + i2;
            AODFragment.this.mAodEndPref.setValue(String.format("%02d:%02d", new Object[]{Integer.valueOf(AODFragment.this.mEndTime / 60), Integer.valueOf(AODFragment.this.mEndTime % 60)}));
            Secure.putInt(AODFragment.this.mContext.getContentResolver(), "aod_end", AODFragment.this.mEndTime);
        }
    };
    private AODPreviewPreference mPreviewPref;
    private ListPreference mShowStylePref;
    /* access modifiers changed from: private */
    public int mStartTime;
    private Preference mStylePref;
    private TextView mSummaryText;
    /* access modifiers changed from: private */
    public boolean mTimeFlag;
    private TimePickerDialog mTimePickerDialog;

    public void onCreate(Bundle bundle) {
        AODFragment.super.onCreate(bundle);
    }

    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.aod_settings, viewGroup, false);
        this.mSummaryText = (TextView) inflate.findViewById(R.id.summary);
        addPreferencesFromResource(R.xml.aod_settings);
        this.mEnableAODPref = (CheckBoxPreference) findPreference("aod_mode_enable");
        this.mEnableAODPref.setOnPreferenceChangeListener(this);
        this.mAodStartPref = findPreference("aod_mode_start_time");
        this.mAodStartPref.setOnPreferenceClickListener(this);
        this.mAodEndPref = findPreference("aod_mode_end_time");
        this.mAodEndPref.setOnPreferenceClickListener(this);
        this.mPreviewPref = (AODPreviewPreference) findPreference("aod_preview");
        this.mPreviewPref.setOnPreferenceClickListener(this);
        this.mStylePref = findPreference("aod_mode_style");
        this.mStylePref.setOnPreferenceClickListener(this);
        this.mShowStylePref = (ListPreference) findPreference("aod_show_style");
        this.mShowStylePref.setOnPreferenceChangeListener(this);
        this.mAodShowPref = (PreferenceCategory) findPreference("aod_show");
        this.mContext = getActivity();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.mContext, this.mOnTimeSetListener, this.mStartTime / 60, this.mStartTime % 60, true);
        this.mTimePickerDialog = timePickerDialog;
        boolean isAodEnable = Utils.isAodEnable(this.mContext);
        this.mEnableAODPref.setChecked(isAodEnable);
        this.mShowStylePref.setEnabled(isAodEnable);
        this.mShowStylePref.setEntries(AODSettings.getShowStyleEntries(this.mContext));
        this.mShowStylePref.setEntryValues(AODSettings.getShowStyleValues(this.mContext));
        this.mStylePref.setEnabled(isAodEnable);
        this.mStartTime = Secure.getInt(this.mContext.getContentResolver(), "aod_start", 420);
        this.mEndTime = Secure.getInt(this.mContext.getContentResolver(), "aod_end", 1380);
        this.mAodStartPref.setValue(String.format("%02d:%02d", new Object[]{Integer.valueOf(this.mStartTime / 60), Integer.valueOf(this.mStartTime % 60)}));
        this.mAodEndPref.setValue(String.format("%02d:%02d", new Object[]{Integer.valueOf(this.mEndTime / 60), Integer.valueOf(this.mEndTime % 60)}));
        updateShowStyle(isAodEnable);
        return inflate;
    }

    private void updateShowStyle(boolean z) {
        Utils.updateTouchMode(this.mContext);
        this.mShowStylePref.setEnabled(z);
        this.mAodShowPref.removePreference(this.mAodStartPref);
        this.mAodShowPref.removePreference(this.mAodEndPref);
        int showStyle = Utils.getShowStyle(this.mContext);
        int translateShowStyleValue2Index = AODSettings.translateShowStyleValue2Index(showStyle);
        if (translateShowStyleValue2Index >= 0 && translateShowStyleValue2Index < this.mShowStylePref.getEntries().length) {
            this.mShowStylePref.setValueIndex(translateShowStyleValue2Index);
            this.mShowStylePref.setSummary(this.mShowStylePref.getEntries()[translateShowStyleValue2Index]);
            if (showStyle == 1) {
                this.mAodShowPref.addPreference(this.mAodStartPref);
                this.mAodShowPref.addPreference(this.mAodEndPref);
                this.mAodStartPref.setEnabled(z);
                this.mAodEndPref.setEnabled(z);
            }
            this.mSummaryText.setVisibility(showStyle == 0 ? 8 : 0);
        }
    }

    public void onResume() {
        AODFragment.super.onResume();
        this.mPreviewPref.refreshView();
    }

    public void onStop() {
        AODFragment.super.onStop();
        if (this.mPreviewPref != null && (this.mPreviewPref instanceof AODPreviewPreference)) {
            this.mPreviewPref.onStop();
        }
    }

    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference == this.mEnableAODPref) {
            Builder builder = new Builder(getActivity());
            if (!(Secure.getInt(getActivity().getContentResolver(), "accessibility_display_inversion_enabled", 0) == 1) || !((Boolean) obj).booleanValue()) {
                this.mEnableAODPref.setChecked(((Boolean) obj).booleanValue());
                Secure.putInt(getActivity().getContentResolver(), AODSettings.AOD_MODE, this.mEnableAODPref.isChecked() ? 1 : 0);
                if (this.mEnableAODPref.isChecked()) {
                    this.mStylePref.setEnabled(true);
                    Secure.putInt(getActivity().getContentResolver(), "aod_show_style", Utils.getShowStyle(this.mContext));
                } else {
                    if (Secure.getInt(getActivity().getContentResolver(), "need_reset_aod_time", 0) == 1) {
                        Secure.putInt(getActivity().getContentResolver(), "aod_mode_time", 1);
                        Secure.putInt(getActivity().getContentResolver(), "need_reset_aod_time", 0);
                    }
                    this.mStylePref.setEnabled(false);
                }
                updateShowStyle(this.mEnableAODPref.isChecked());
                Utils.updateTouchMode(this.mContext);
            } else {
                builder.setMessage(R.string.aod_close_color_inversion);
                builder.setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.aod_to_close, new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AODFragment.this.getActivity().startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
                    }
                });
                builder.create().show();
            }
        } else if (preference == this.mShowStylePref) {
            int intValue = Integer.valueOf((String) obj).intValue();
            this.mShowStylePref.setValueIndex(AODSettings.translateShowStyleValue2Index(intValue));
            if (intValue == 1) {
                Secure.putInt(getActivity().getContentResolver(), "need_reset_aod_time", 0);
            }
            Secure.putInt(getActivity().getContentResolver(), "aod_show_style", intValue);
            updateShowStyle(true);
        }
        return false;
    }

    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if ("aod_mode_start_time".equals(key)) {
            this.mTimeFlag = false;
            if (this.mStartTime > 0) {
                this.mTimePickerDialog.updateTime(this.mStartTime / 60, this.mStartTime % 60);
            } else {
                this.mTimePickerDialog.updateTime(0, 0);
            }
            this.mTimePickerDialog.show();
            return true;
        } else if ("aod_mode_end_time".equals(key)) {
            this.mTimeFlag = true;
            if (this.mEndTime > 0) {
                this.mTimePickerDialog.updateTime(this.mEndTime / 60, this.mEndTime % 60);
            } else {
                this.mTimePickerDialog.updateTime(0, 0);
            }
            this.mTimePickerDialog.show();
            return true;
        } else if (!"aod_mode_style".equals(key) && !"aod_preview".equals(key)) {
            return false;
        } else {
            startAODStyleActivity();
            return true;
        }
    }

    private void startAODStyleActivity() {
        Activity activity = getActivity();
        if (activity != null && Secure.getInt(this.mContext.getContentResolver(), AODSettings.AOD_MODE, 0) == 1) {
            this.mContext.startActivity(new Intent(activity, AODStyleActivity.class));
            Pair systemDefaultEnterAnim = getSystemDefaultEnterAnim(activity);
            activity.overridePendingTransition(((Integer) systemDefaultEnterAnim.first).intValue(), ((Integer) systemDefaultEnterAnim.second).intValue());
        }
    }

    private Pair<Integer, Integer> getSystemDefaultEnterAnim(Activity activity) {
        TypedArray obtainStyledAttributes = activity.obtainStyledAttributes(null, new int[]{16842936, 16842937}, 16842926, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        int resourceId2 = obtainStyledAttributes.getResourceId(1, -1);
        obtainStyledAttributes.recycle();
        return new Pair<>(Integer.valueOf(resourceId), Integer.valueOf(resourceId2));
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return AODFragment.super.onOptionsItemSelected(menuItem);
        }
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
        return true;
    }
}
