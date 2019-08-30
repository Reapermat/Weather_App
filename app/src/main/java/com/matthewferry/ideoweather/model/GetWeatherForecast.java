package com.matthewferry.ideoweather.model;

import com.matthewferry.ideoweather.activity.MainActivity;
import com.matthewferry.ideoweather.helper.SharedPreference;


public class GetWeatherForecast extends MainActivity {

    public String mMessage;
    public String wCity = SharedPreference.getCity();
    private int wI;

    public String getMessage() {
        return mMessage;
    }

    public String getwCity(){
        return wCity;
    }

    public GetWeatherForecast(String message) {
        mMessage = message;
    }
}
