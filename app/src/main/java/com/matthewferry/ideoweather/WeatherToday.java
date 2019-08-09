package com.matthewferry.ideoweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherToday{
   /* @SerializedName("id")
    public int id;
    @SerializedName("main")
    public String main;*/
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("icon")
    @Expose
    public String icon;

    @Override
    public String toString() {
        return "Weather{" +
                "description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
/*public int getId() {
        return id;
    }*/

    /*public String getMain() {
        return main;
    }*/

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
