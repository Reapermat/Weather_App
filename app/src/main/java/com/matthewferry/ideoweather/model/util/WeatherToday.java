package com.matthewferry.ideoweather.model.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherToday{
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

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
