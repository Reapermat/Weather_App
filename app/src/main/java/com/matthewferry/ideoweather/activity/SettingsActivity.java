package com.matthewferry.ideoweather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.helper.SharedPreference;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String title;
    private String lang;
    private CheckBox englishCheckBox;
    private CheckBox polishCheckBox;
    private CheckBox celsiusCheckBox;
    private CheckBox fahrenheitCheckBox;
    private TextView settingsLang;
    private TextView settingsUnits;
    public View view;
    private String setUnits;
    private String setLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreference.loadPreferences(getApplicationContext());
        setContentView(R.layout.activity_settings);
        findViews(view);
        setLangButton();
        setTempButton();
        celsiusCheckBox.setText((char) 0x00B0 + "C");
        fahrenheitCheckBox.setText((char) 0x00B0 + "F");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settingsLang.setText(setLanguage);
        settingsUnits.setText(setUnits);
        SharedPreference.move = true;

    }

    private void findViews(View view) {
        title = getString(R.string.settings_name);
        toolbar = findViewById(R.id.settings_toolbar);
        englishCheckBox = findViewById(R.id.englishCheckBox);
        polishCheckBox = findViewById(R.id.polishCheckBox);
        celsiusCheckBox = findViewById(R.id.celsiusCheckBox);
        fahrenheitCheckBox = findViewById(R.id.fahrenheitCheckBox);
        setUnits = getString(R.string.units);
        setLanguage = getString(R.string.language);
        settingsLang = findViewById(R.id.settings_language);
        settingsUnits = findViewById(R.id.settings_units);
    }

    public void toCelsius(View view) {

        SharedPreference.units = "metric";
        celsiusCheckBox.setChecked(true);
        celsiusCheckBox.setEnabled(false);
        fahrenheitCheckBox.setChecked(false);
        fahrenheitCheckBox.setEnabled(true);
        SharedPreference.setPreference("temperature", "C");
        SharedPreference.setPreference("units", "metric");
        SharedPreference.savePreferences("temperature", "C", getApplicationContext());
        SharedPreference.savePreferences("units", "metric", getApplicationContext());
    }

    public void toFahrenheit(View view) {

        SharedPreference.units = "imperial";
        fahrenheitCheckBox.setChecked(true);
        fahrenheitCheckBox.setEnabled(false);
        celsiusCheckBox.setChecked(false);
        celsiusCheckBox.setEnabled(true);
        SharedPreference.setPreference("temperature", "F");
        SharedPreference.setPreference("units", "imperial");
        SharedPreference.savePreferences("temperature", "F", getApplicationContext());
        SharedPreference.savePreferences("units", "imperial", getApplicationContext());
    }

    public void toEnglish(View view) {

        SharedPreference.setLocal("en", getBaseContext());
        englishCheckBox.setChecked(true);
        englishCheckBox.setEnabled(false);
        polishCheckBox.setChecked(false);
        polishCheckBox.setEnabled(true);
        SharedPreference.language = "en";
        SharedPreference.setPreference("language", "en");
        SharedPreference.savePreferences("language", "en", getApplicationContext());
        recreate();
        SharedPreference.deleteCache(this);
        setLangButton();
    }

    public void toPolish(View view) {

        SharedPreference.setLocal("pl", getBaseContext());
        englishCheckBox.setChecked(false);
        englishCheckBox.setEnabled(true);
        polishCheckBox.setChecked(true);
        polishCheckBox.setEnabled(false);
        SharedPreference.language = "pl";
        SharedPreference.setPreference("language", "pl");
        SharedPreference.savePreferences("language", "pl", getApplicationContext());
        SharedPreference.getPreference("language");
        recreate();
        SharedPreference.deleteCache(this);
        setLangButton();

    }




    private void setTempButton() {
        if (SharedPreference.getPreference("temperature") == null || SharedPreference.getPreference("units") == null) {
            SharedPreference.setPreference("temperature", "F");
            SharedPreference.setPreference("units", "imperial");
            SharedPreference.savePreferences("temperature", "F", getApplicationContext());
            SharedPreference.savePreferences("units", "imperial", getApplicationContext());
        }
        if (SharedPreference.getPreference("temperature").equals("C") || SharedPreference.getPreference("units").equals("metric")) {
            SharedPreference.units = "metric";
            celsiusCheckBox.setChecked(true);
            celsiusCheckBox.setEnabled(false);
            fahrenheitCheckBox.setChecked(false);
            fahrenheitCheckBox.setEnabled(true);

        } else {
            SharedPreference.units = "imperial";
            fahrenheitCheckBox.setChecked(true);
            fahrenheitCheckBox.setEnabled(false);
            celsiusCheckBox.setChecked(false);
            celsiusCheckBox.setEnabled(true);

        }
    }

    private void setLangButton() {

        if (SharedPreference.getPreference("language") == null) {
            SharedPreference.setPreference("language", "en");
            englishCheckBox.setChecked(true);
            englishCheckBox.setEnabled(false);
            polishCheckBox.setChecked(false);
            polishCheckBox.setEnabled(true);
            SharedPreference.language = "en";
            SharedPreference.savePreferences("language", "en", getApplicationContext());
        }
        if (SharedPreference.getPreference("language").equals("en")) {
            englishCheckBox.setChecked(true);
            englishCheckBox.setEnabled(false);
            polishCheckBox.setChecked(false);
            polishCheckBox.setEnabled(true);
            SharedPreference.language = "en";
            SharedPreference.savePreferences("language", "en", getApplicationContext());
        } else if (SharedPreference.getPreference("language").equals("pl")) {
            englishCheckBox.setChecked(false);
            englishCheckBox.setEnabled(true);
            polishCheckBox.setChecked(true);
            polishCheckBox.setEnabled(false);
            SharedPreference.language = "pl";
            SharedPreference.savePreferences("language", "pl", getApplicationContext());
        }
    }
}
