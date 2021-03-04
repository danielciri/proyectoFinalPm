package com.danielcirilo.frasescelebres.preferences;


import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.danielcirilo.frasescelebres.MainActivity;
import com.danielcirilo.frasescelebres.R;

import java.util.Calendar;


public class PreferenceFragment extends PreferenceFragmentCompat {

    private CheckBoxPreference checkBoxPreference;
    private ListPreference listPreference;
    private SharedPreferences settings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listPreference = (ListPreference) findPreference("values_encryption");


    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

    }
}