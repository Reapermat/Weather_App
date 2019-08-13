package com.matthewferry.ideoweather.model.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds{
    @SerializedName("all")
    @Expose
    public float all;

    public float getAll() {
        return all;
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "all=" + all +
                '}';
    }
}
