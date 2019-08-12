package com.matthewferry.ideoweather.activity.weather_services_for_api;

import com.matthewferry.ideoweather.activity.activities.WeatherResponseToday;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherServiceLocationToday {
    @GET("data/2.5/weather?")
    Call<WeatherResponseToday> getCurrentWeatherDataFromLocation(@Query("lat") String lat, @Query("lon") String lon, @Query("lang") String language, @Query("units") String units, @Query("APPID") String app_id);
}

