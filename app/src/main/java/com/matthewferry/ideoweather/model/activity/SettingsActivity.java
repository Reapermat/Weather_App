package com.matthewferry.ideoweather.model.activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.model.helper.LocaleHelper;

import java.util.Locale;

import static com.matthewferry.ideoweather.model.activity.MainActivity.deleteCache;
import static com.matthewferry.ideoweather.model.activity.MainActivity.getCity;

public class SettingsActivity extends AppCompatActivity {


    Toolbar toolbar;
    String title;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String lang;
    CheckBox englishCheckBox;
    CheckBox polishCheckBox;
    CheckBox celsiusCheckBox;
    CheckBox fahrenheitCheckBox;
    TextView settingsLang;
    TextView settingsUnits;
    View view;
    String setUnits;
    String setLanguage;
    static String language = "en";
    static String units = "F";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        loadPreferences();
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


    }


    public void loadPreferences(){
        try {
            pref = PreferenceManager.getDefaultSharedPreferences(this);
            lang = pref.getString("language", null);
            Log.i("language", pref.getString("language", null));
            Log.i("units", pref.getString("units", null));
            //LocaleHelper.setLocale(this, lang);
            setLocal(lang);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setLocal(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        editor.putString("language", lang);
        editor.apply();
    }

    public void findViews(View view){
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

    public void toCelsius(View view){

        units="metric";
        celsiusCheckBox.setChecked(true);
        celsiusCheckBox.setEnabled(false);
        fahrenheitCheckBox.setChecked(false);
        fahrenheitCheckBox.setEnabled(true);
        editor.putString("temperature", "C");
        editor.putString("units", "metric");
        editor.commit();
        savePreferences("temperature", "C");
        savePreferences("units", "metric");
    }

    public void toFahrenheit(View view){

        units="imperial";
        fahrenheitCheckBox.setChecked(true);
        fahrenheitCheckBox.setEnabled(false);
        celsiusCheckBox.setChecked(false);
        celsiusCheckBox.setEnabled(true);
        editor.putString("temperature", "F");
        editor.putString("units", "imperial");
        editor.commit();
        savePreferences("temperature", "F");
        savePreferences("units", "imperial");

    }

    public void toEnglish(View view){

        //LocaleHelper.setLocale(this, "en");

        setLocal("en");
        englishCheckBox.setChecked(true);
        englishCheckBox.setEnabled(false);
        polishCheckBox.setChecked(false);
        polishCheckBox.setEnabled(true);

        language = "en";
        editor.putString("language", "en");
        editor.commit();
        savePreferences("language", "en");
        recreate();
        deleteCache(this);
        setLangButton();

    }

    public void toPolish(View view){

        //LocaleHelper.setLocale(this, "pl");
        englishCheckBox.setChecked(false);
        englishCheckBox.setEnabled(true);
        polishCheckBox.setChecked(true);
        polishCheckBox.setEnabled(false);
        setLocal("pl");
        language = "pl";
        editor.putString("language", "pl");
        editor.commit();
        savePreferences("language", "pl");
        pref.getString("language", null);
        recreate();
        deleteCache(this);
        setLangButton();

    }
    private void savePreferences(String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String getUnits(){
        return units;
    }

    public static String getLanguage(){
        return language;
    }

    public void setTempButton(){
        if(pref.getString("temperature", null) == null || pref.getString("units", null) == null){
            editor.putString("temperature", "F");
            editor.putString("units", "imperial");
            editor.commit();
            savePreferences("temperature", "F");
            savePreferences("units", "imperial");
        }
        if (pref.getString("temperature", null).equals("C") || pref.getString("units", null).equals("metric")){
            units="metric";
            celsiusCheckBox.setChecked(true);
            celsiusCheckBox.setEnabled(false);
            fahrenheitCheckBox.setChecked(false);
            fahrenheitCheckBox.setEnabled(true);

        }else{
            units="imperial";
            fahrenheitCheckBox.setChecked(true);
            fahrenheitCheckBox.setEnabled(false);
            celsiusCheckBox.setChecked(false);
            celsiusCheckBox.setEnabled(true);

        }
    }

    public void setLangButton(){

        if(pref.getString("language", null) == null ) {
            editor.putString("language", "en");
            editor.commit();
            englishCheckBox.setChecked(true);
            englishCheckBox.setEnabled(false);
            polishCheckBox.setChecked(false);
            polishCheckBox.setEnabled(true);
            language = "en";
            savePreferences("language", "en");
        }
        if(pref.getString("language",null).equals("en") ){
            englishCheckBox.setChecked(true);
            englishCheckBox.setEnabled(false);
            polishCheckBox.setChecked(false);
            polishCheckBox.setEnabled(true);
            language = "en";
            savePreferences("language", "en");
        }else if(pref.getString("language",null).equals("pl")){
            englishCheckBox.setChecked(false);
            englishCheckBox.setEnabled(true);
            polishCheckBox.setChecked(true);
            polishCheckBox.setEnabled(false);
            language = "pl";
            savePreferences("language", "pl");
        }
    }
}
