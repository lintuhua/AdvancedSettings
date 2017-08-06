package com.android.advancedsettings;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Miui(this,true);
        Flyme(this,true);
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

    public boolean Miui(Activity activity, boolean dark) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), dark ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            return true;
        }
    }

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