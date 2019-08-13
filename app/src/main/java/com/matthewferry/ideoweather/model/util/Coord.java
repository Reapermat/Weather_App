package com.matthewferry.ideoweather.model.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord{
        @SerializedName("lon")
        @Expose
        public float lon;
        @SerializedName("lat")
        @Expose
        public float lat;

        public float getLon() {
            return lon;
        }

        public float getLat() {
            return lat;
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "lon=" + lon +
                    ", lat=" + lat +
                    '}';
        }
    }
