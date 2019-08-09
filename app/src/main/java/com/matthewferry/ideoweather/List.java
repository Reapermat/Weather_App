package com.matthewferry.ideoweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class List {

    @SerializedName("dt")
    @Expose
    public Integer dt;
    @SerializedName("main")
    @Expose
    public MainNext main;
    @SerializedName("weather")
    @Expose
    public ArrayList<WeatherNext> weatherNext = null;
    @SerializedName("clouds")
    @Expose
    public Clouds clouds;
    @SerializedName("wind")
    @Expose
    public Wind wind;
    @SerializedName("sys")
    @Expose
    public Sys sys;
    @SerializedName("dt_txt")
    @Expose
    public String dtTxt;
    @SerializedName("rain")
    @Expose
    public Rain rain;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public MainNext getMain() {
        return main;
    }

    public void setMain(MainNext main) {
        this.main = main;
    }

    public ArrayList<WeatherNext> getWeather() {
        return weatherNext;
    }

    public void setWeather (ArrayList<WeatherNext> weather) {
        this.weatherNext = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    @Override
    public String toString() {
        return "List{" +
                "dt=" + dt +
                ", main=" + main +
                ", weatherNext=" + weatherNext +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", sys=" + sys +
                ", dtTxt='" + dtTxt + '\'' +
                ", rain=" + rain +
                '}';
    }
}
class MainNext{
    @SerializedName("temp")
    @Expose
    public float temp;
    @SerializedName("pressure")
    @Expose
    public float pressure;
    @SerializedName("humidity")
    @Expose
    public float humidity;
    @SerializedName("temp_min")
    @Expose
    public float temp_min;
    @SerializedName("temp_max")
    @Expose
    public float temp_max;
    @SerializedName("sea_level")
    @Expose
    public float sea_level;
    @SerializedName("grnd_level")
    @Expose
    public float grnd_level;

    public float getTemp() {
        return temp;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", sea_level=" + sea_level +
                ", grnd_level=" + grnd_level +
                '}';
    }
}
