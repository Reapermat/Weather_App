package com.matthewferry.ideoweather.model.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.matthewferry.ideoweather.model.activity.MainActivity;
import com.matthewferry.ideoweather.model.activity.SettingsActivity;
import com.matthewferry.ideoweather.model.activity.ViewPagerActivity;
import com.matthewferry.ideoweather.model.interfaces.WeatherServiceNameNext;
import com.matthewferry.ideoweather.model.serviceGenerator.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetWeatherForecast extends MainActivity {

    public String mMessage;
    public static String wCity;
    private Context context;
    public static int wI;

    public String getwCity() {
        return wCity;
    }

    public int getwI() {
        return wI;
    }

    public String getMessage() {
        return mMessage;
    }

    public GetWeatherForecast (String message) {
        mMessage=message;
    }
}
