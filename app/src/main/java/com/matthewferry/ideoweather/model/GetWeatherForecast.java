package com.matthewferry.ideoweather.model;

import com.matthewferry.ideoweather.activity.MainActivity;


public class GetWeatherForecast extends MainActivity {

    public String mMessage;
    private String wCity;
    private int wI;

    public String getMessage() {
        return mMessage;
    }

    public GetWeatherForecast(String message) {
        mMessage = message;
    }
}
