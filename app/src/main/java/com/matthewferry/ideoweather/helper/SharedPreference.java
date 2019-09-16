package com.matthewferry.ideoweather.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.util.Locale;

public class SharedPreference {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private static String lang;
    private static Context context;
    public static boolean move = false;
    public static String message1;
    public static String language = "en";
    public static String units = "F";
    public static String city;


    public static String getCity() {
        return city;
    }

    public static void savePreferences(String key, String value, Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void loadPreferences(Context context) {
        try {
            Log.i("language", Locale.getDefault().getDisplayLanguage());
            pref = PreferenceManager.getDefaultSharedPreferences(context);
            lang = pref.getString("language", null);
            Log.i("language", SharedPreference.getPreference("language"));
            Log.i("units", SharedPreference.getPreference("units"));
            Log.i("visibility", SharedPreference.getPreference("weatherSet"));
            setLocal(lang, context);
        } catch (Exception e) {
            if(Locale.getDefault().getDisplayLanguage().equals("English")){
                savePreferences("language", "en", context);
            }else
                savePreferences("language", "pl", context);
            e.printStackTrace();
        }
    }

    public static String getPreference(String key) {
        if (key.equals(null)) {
            key = "temperature";
            setPreference(key, "F");
            savePreferences(key, "F", context);
            key = "units";
            setPreference(key, "imperial");
            savePreferences(key, "imperial", context);
            return pref.getString(key, null);
        }
        return pref.getString(key, null);
    }

    public static void setPreference(String key, String value) {

        try {
            editor.putString(key, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLocal(String lang, Context context) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        Log.i("lang= from pref", lang);
        setPreference("language", lang);
    }

    public static String getWeatherMessage() {
        return message1;
    }

    public static String getUnits() {
        return units;
    }

    public static String getLanguage() {
        return language;
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
