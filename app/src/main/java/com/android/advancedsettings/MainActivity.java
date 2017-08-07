package com.android.advancedsettings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        getFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
    }

    public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

        private SwitchPreference DoubleTap;
        private SwitchPreference Charging;

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

        }

        public void init() {
            DoubleTap = (SwitchPreference) findPreference("double_tap");
            DoubleTap.setOnPreferenceChangeListener(this);
            Charging = (SwitchPreference) findPreference("Charging");
            Charging.setOnPreferenceChangeListener(this);
        }

        public boolean onPreferenceChange(Preference Preference, Object Object) {
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
            }
            return false;
        }
    }

}