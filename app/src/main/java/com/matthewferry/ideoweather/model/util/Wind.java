package com.matthewferry.ideoweather.model.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind{
        @SerializedName("speed")
        @Expose
        public float speed;
        @SerializedName("deg")
        @Expose
        public float deg;

        @Override
        public String toString() {
            return "Wind{" +
                    "speed=" + speed +
                    ", deg=" + deg +
                    '}';
        }

        public float getSpeed() {
            return speed;
        }

        public float getDeg() {
            return deg;
        }
    }
