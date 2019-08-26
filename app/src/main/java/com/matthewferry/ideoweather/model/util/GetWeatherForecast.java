package com.matthewferry.ideoweather.model.util;

import android.content.Context;
import com.matthewferry.ideoweather.model.activity.MainActivity;


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
