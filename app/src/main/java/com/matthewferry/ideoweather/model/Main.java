package com.matthewferry.ideoweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    @Expose
    private float temp;
    @SerializedName("pressure")
    @Expose
    private float pressure;
    @SerializedName("humidity")
    @Expose
    private float humidity;
    @SerializedName("temp_min")
    @Expose
    private float temp_min;
    @SerializedName("temp_max")
    @Expose
    private float temp_max;
    @SerializedName("sea_level")
    @Expose
    private float sea_level;
    @SerializedName("grnd_level")
    @Expose
    private float grnd_level;

    public float getTemp() {
        return temp;
    }


}
