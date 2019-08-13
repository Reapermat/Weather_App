package com.matthewferry.ideoweather.model.service;

import com.matthewferry.ideoweather.model.util.WeatherResponseToday;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherServiceNameToday{
    @GET("data/2.5/weather")
    Call<WeatherResponseToday> getCurrentWeatherDataFromName(@Query("q") String City, @Query("lang") String language, @Query("units") String units, @Query("APPID") String app_id);
}
