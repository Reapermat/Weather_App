package com.matthewferry.ideoweather.activity.activities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponseToday  {



    @SerializedName("coord")
    @Expose
    public Coord coord;
    @SerializedName("weather")
    @Expose
    private ArrayList<WeatherToday> weatherToday = null;
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("main")
    @Expose
    public Main main;
    @SerializedName("wind")
    @Expose
    public Wind wind;
    @SerializedName("clouds")
    @Expose
    public Clouds clouds;
    @SerializedName("dt")
    @Expose
    public float dt;
    @SerializedName("sys")
    @Expose
    public Sys sys;
    @SerializedName("timezone")
    @Expose
    public float timezone;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("cod")
    @Expose
    public float cod;
    @SerializedName("rain")
    @Expose
    public Rain rain;
    @SerializedName("snow")
    @Expose
    public Snow snow;


    public ArrayList<WeatherToday> getWeather() {
        return weatherToday;
    }
    public Rain getRain() {
        return rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }


    public float getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public float getTimezone() {
        return timezone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "WeatherResponseToday{" +
                "coord=" + coord +
                ", weather=" + weatherToday +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dt=" + dt +
                ", sys=" + sys +
                ", timezone=" + timezone +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                ", rain=" + rain +
                ", snow=" + snow +
                '}';
    }

    public float getCod() {
        return cod;


    }


}

    class Coord{
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

class Main{
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

    class Wind{
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

    class Clouds{
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

    class Sys{
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("sunrise")
        @Expose
        public long sunrise;
        @SerializedName("sunset")
        @Expose
        public long sunset;

        public String getCountry() {
            return country;
        }

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        @Override
        public String toString() {
            return "Sys{" +
                    "country='" + country + '\'' +
                    ", sunrise=" + sunrise +
                    ", sunset=" + sunset +
                    '}';
        }
    }

    class Rain{
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

    class Snow{
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




