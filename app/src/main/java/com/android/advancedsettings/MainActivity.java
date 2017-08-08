package com.android.advancedsettings;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Flyme(this,true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
    }

    public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

        private SwitchPreference DoubleTap;
        private SwitchPreference Charging;
        private SwitchPreference Proximity;
        private SwitchPreference COVER;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);
            init();
        }

        public void onResume() {
            super.onResume();
            if (Settings.System.getInt(getContentResolver(), "double_tap_enable", 0) == 1) {
                DoubleTap.setChecked(true);
            } else {
                DoubleTap.setChecked(false);
            }
            if (Settings.System.getInt(getContentResolver(), "CHARGING_LIGHT_PULSE", 0) == 1) {
                Charging.setChecked(true);
            } else {
                Charging.setChecked(false);
            }
            if (Settings.System.getInt(getContentResolver(), "unlock_proximity_enable", 0) == 1) {
                Proximity.setChecked(true);
            } else  {
                Proximity.setChecked(false);
            }
            if (Settings.Secure.getInt(getContentResolver(), "COVER_MODE_STATE", 0) == 1) {
                COVER.setChecked(true);
            } else  {
                COVER.setChecked(false);
            }
        }

        public void init() {
            DoubleTap = (SwitchPreference) findPreference("double_tap");
            DoubleTap.setOnPreferenceChangeListener(this);
            Charging = (SwitchPreference) findPreference("Charging");
            Charging.setOnPreferenceChangeListener(this);
            Proximity = (SwitchPreference) findPreference("proximity");
            Proximity.setOnPreferenceChangeListener(this);
            COVER = (SwitchPreference) findPreference("cover");
            COVER.setOnPreferenceChangeListener(this);
        }

        public boolean onPreferenceChange(Preference Preference, Object Object) {
            if (Preference == DoubleTap) {
                if (!DoubleTap.isChecked()) {
                    Settings.System.putInt(getContentResolver(), "double_tap_enable", 1);
                } else {
                    Settings.System.putInt(getContentResolver(), "double_tap_enable", 0);
                }
            } else if (Preference == Charging) {
                if (!Charging.isChecked()) {
                    Settings.System.putInt(getContentResolver(), "CHARGING_LIGHT_PULSE", 1);
                } else {
                    Settings.System.putInt(getContentResolver(), "CHARGING_LIGHT_PULSE", 0);
                }
            } else if (Preference == Proximity) {
                if (!Proximity.isChecked()) {
                    Settings.System.putInt(getContentResolver(), "unlock_proximity_enable", 1);
                } else {
                    Settings.System.putInt(getContentResolver(), "unlock_proximity_enable", 0);
                }
            } else if (Preference == COVER){
                if (!COVER.isChecked()) {
                    Settings.Secure.putInt(getContentResolver(), "COVER_MODE_STATE", 1);
                } else {
                    Settings.Secure.putInt(getContentResolver(), "COVER_MODE_STATE", 0);
                }
            }
            return true;
        }

        public boolean onPreferenceClick(Preference Preference) {
            return false;
        }

        public boolean onPreferenceTreeClick(PreferenceScreen PreferenceScreen, Preference Preference) {
            if (Preference == DoubleTap) {
                if (DoubleTap.isChecked()) {
                    Settings.System.putInt(getContentResolver(), "double_tap_enable", 1);
                } else {
                    Settings.System.putInt(getContentResolver(), "double_tap_enable", 0);
                }
            } else if (Preference == Charging) {
                if (Charging.isChecked()) {
                    Settings.System.putInt(getContentResolver(), "CHARGING_LIGHT_PULSE", 1);
                } else {
                    Settings.System.putInt(getContentResolver(), "CHARGING_LIGHT_PULSE", 0);
                }
            } else if (Preference == Proximity) {
                if (Proximity.isChecked()) {
                    Settings.System.putInt(getContentResolver(), "unlock_proximity_enable", 1);
                } else {
                    Settings.System.putInt(getContentResolver(), "unlock_proximity_enable", 0);
                }
            } else if (Preference == COVER){
                if (COVER.isChecked()) {
                    Settings.Secure.putInt(getContentResolver(), "COVER_MODE_STATE", 1);
                } else {
                    Settings.Secure.putInt(getContentResolver(), "COVER_MODE_STATE", 0);
                }
            }
                return false;
        }
    }

    //Settings.Secure.putInt(getContentResolver(), "screen_off_fp_unlock_state", 1);   这个为按压解锁
    //Settings.System.putInt(getContentResolver(), "mz_fingerprint_use_unlock", 0);    魅族的指纹解锁开关
    //Settings.System.putInt(getContentResolver(), "mz_fingerprint_use_unlock", 1);    zui的指纹解锁开关

    public boolean Flyme(Activity activity, boolean dark) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field FlymeFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            FlymeFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = FlymeFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            FlymeFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}