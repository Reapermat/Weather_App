package com.matthewferry.ideoweather.model.util;

import com.google.gson.annotations.SerializedName;

public class Snow{
        @SerializedName("1h")
        public float h1;
        @SerializedName("3h")
        public float h3;

        @Override
        public String toString() {
            return "Snow{" +
                    "h1=" + h1 +
                    ", h3=" + h3 +
                    '}';
        }
    }
