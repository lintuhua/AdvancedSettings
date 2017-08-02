package com.android.advancedsettings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;

public class MainActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener
{
    private SwitchPreference DoubleTap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
        init();
    }

    public void onPostResume()
    {
        super.onPostResume();
        if (Settings.System.getInt(getContentResolver(), "double_tap_enable", 0) == 1){
            DoubleTap.setChecked(true);
        } else {
            DoubleTap.setChecked(false);
        }
    }

    public void init() {
        DoubleTap = (SwitchPreference) findPreference("double_tap");
        DoubleTap.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference Preference, Object Object){
        return true;
    }

    public boolean onPreferenceClick(Preference Preference)
    {
        return false;
    }

    public boolean onPreferenceTreeClick(PreferenceScreen PreferenceScreen, Preference Preference)
    {
        if (Preference == this.DoubleTap) {
            if (DoubleTap.isChecked()) {
                Settings.System.putInt(getContentResolver(), "double_tap_enable", 1);
            } else {
                Settings.System.putInt(getContentResolver(), "double_tap_enable", 0);
            }
        }
        return false;
    }

}
