package com.matthewferry.ideoweather.model.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain{
    @SerializedName("1h")
    @Expose
    public float h1;
    @SerializedName("3h")
    @Expose
    public float h3;

    @Override
    public String toString() {
        return "Rain{" +
                "h1=" + h1 +
                ", h3=" + h3 +
                '}';
    }
}
