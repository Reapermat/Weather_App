package com.matthewferry.ideoweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("1h")
    @Expose
    private float h1;
    @SerializedName("3h")
    @Expose
    private float h3;


}
