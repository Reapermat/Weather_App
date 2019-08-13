package com.matthewferry.ideoweather.model.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main{
        @SerializedName("temp")
        @Expose
        public float temp;
        @SerializedName("pressure")
        @Expose
        public float pressure;
        @SerializedName("humidity")
        @Expose
        public float humidity;
        @SerializedName("temp_min")
        @Expose
        public float temp_min;
        @SerializedName("temp_max")
        @Expose
        public float temp_max;
        @SerializedName("sea_level")
        @Expose
        public float sea_level;
        @SerializedName("grnd_level")
        @Expose
        public float grnd_level;

        public float getTemp() {
            return temp;
        }

        public float getPressure() {
            return pressure;
        }

        public float getHumidity() {
            return humidity;
        }

        public float getTemp_min() {
            return temp_min;
        }

        public float getTemp_max() {
            return temp_max;
        }

    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", sea_level=" + sea_level +
                ", grnd_level=" + grnd_level +
                '}';
    }
}
