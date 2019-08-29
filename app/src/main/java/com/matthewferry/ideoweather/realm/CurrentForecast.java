package com.matthewferry.ideoweather.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentForecast extends RealmObject {

    @PrimaryKey
    private int idCurrentForecast;

    private String forecast;

    public CurrentForecast(){

    }

    public int getIdCurrentForecast() {
        return idCurrentForecast;
    }

    public void setIdCurrentForecast(int idCurrentForecast) {
        this.idCurrentForecast = idCurrentForecast;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }
}
