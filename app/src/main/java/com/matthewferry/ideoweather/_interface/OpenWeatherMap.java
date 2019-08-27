package com.matthewferry.ideoweather._interface;

import com.matthewferry.ideoweather.model.WeatherResponseNextDays;
import com.matthewferry.ideoweather.model.WeatherResponseToday;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMap {
    @GET("data/2.5/weather?")
    Call<WeatherResponseToday> getCurrentWeatherDataFromLocation(@Query("lat") String lat, @Query("lon") String lon, @Query("lang") String language, @Query("units") String units);

    @GET("data/2.5/forecast?")
    Call<WeatherResponseNextDays> getCurrentDataFromNameNextDays(@Query("q") String City, @Query("lang") String language, @Query("units") String units);

    @GET("data/2.5/weather?")
    Call<WeatherResponseToday> getCurrentWeatherDataFromName(@Query("q") String City, @Query("lang") String language, @Query("units") String units);

}


