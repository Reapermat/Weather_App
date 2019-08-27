package com.matthewferry.ideoweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord{
        @SerializedName("lon")
        @Expose
        private float lon;
        @SerializedName("lat")
        @Expose
        private float lat;

    }
