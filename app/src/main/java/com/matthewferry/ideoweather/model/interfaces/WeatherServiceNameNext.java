package com.matthewferry.ideoweather.model.interfaces;

import com.matthewferry.ideoweather.model.util.WeatherResponseNextDays;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherServiceNameNext {

    @GET("data/2.5/forecast?")
    Call<WeatherResponseNextDays> getCurrentDataFromNameNextDays(@Query("q") String City, @Query("lang") String language, @Query("units") String units, @Query("APPID") String app_id);

}
