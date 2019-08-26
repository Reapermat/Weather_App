package com.matthewferry.ideoweather.model.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponseToday  {

    @SerializedName("coord")
    @Expose
    public Coord coord;
    @SerializedName("weather")
    @Expose
    private ArrayList<WeatherToday> weatherToday = null;
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("main")
    @Expose
    public Main main;
    @SerializedName("wind")
    @Expose
    public Wind wind;
    @SerializedName("clouds")
    @Expose
    public Clouds clouds;
    @SerializedName("dt")
    @Expose
    public float dt;
    @SerializedName("sys")
    @Expose
    public Sys sys;
    @SerializedName("timezone")
    @Expose
    public float timezone;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("cod")
    @Expose
    public float cod;
    @SerializedName("rain")
    @Expose
    public Rain rain;
    @SerializedName("snow")
    @Expose
    public Snow snow;

    public ArrayList<WeatherToday> getWeather() {
        return weatherToday;
    }

    public Rain getRain() {
        return rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public float getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public float getTimezone() {
        return timezone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "WeatherResponseToday{" +
                "coord=" + coord +
                ", weather=" + weatherToday +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dt=" + dt +
                ", sys=" + sys +
                ", timezone=" + timezone +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                ", rain=" + rain +
                ", snow=" + snow +
                '}';
    }

    public float getCod() {
        return cod;

    }


}




