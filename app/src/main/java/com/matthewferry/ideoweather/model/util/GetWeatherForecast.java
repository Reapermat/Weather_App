package com.matthewferry.ideoweather.model.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.matthewferry.ideoweather.model.activity.MainActivity;
import com.matthewferry.ideoweather.model.activity.SettingsActivity;
import com.matthewferry.ideoweather.model.activity.ViewPagerActivity;
import com.matthewferry.ideoweather.model.interfaces.WeatherServiceNameNext;
import com.matthewferry.ideoweather.model.serviceGenerator.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetWeatherForecast extends MainActivity {

    private static String message;
    private String AppId = "5817ad2133c8314219db601b74a40a2b";
    public static String wCity;
    private Context context;
    public static int wI;

    public static String getwCity() {
        return wCity;
    }

    public static int getwI() {
        return wI;
    }

    public static String getMessage() {
        return message;
    }

    public GetWeatherForecast (final String city, final int i) {
        wCity = city;
        wI = i;
    }

    private static int nextDay = 6;

    public static void getLocationFromNameNextDays (final String City, final int i) {


        /*WeatherServiceNameNext service = ServiceGenerator.createService(WeatherServiceNameNext.class);
        Call<WeatherResponseNextDays> call = service.getCurrentDataFromNameNextDays(City, pref.getString("language", null), pref.getString("units", null), AppId);
        call.enqueue(new Callback<WeatherResponseNextDays>() {
            @Override
            public void onResponse(Call<WeatherResponseNextDays> call, Response<WeatherResponseNextDays> response) {
                try {
                    WeatherResponseNextDays weatherResponseNextDays = response.body();
                    Log.i("server Response", response.body().toString());
                    //ArrayList<List> list = response.body().getList();
                    Log.i("working?", MainActivity.getCity());
                    ArrayList<List> list = response.body().getList();
                    String date = list.get(i).getDtTxt();
                    String t = String.valueOf(weatherResponseNextDays.getList().get(i).main.temp);
                    Log.i("what is this",list.get(i).getDtTxt());

                    message = date +"\r\n\r\n" + City + "\r\n" + t + (char) 0x00B0 + pref.getString("temperature", null) + "\r\n" +  list.get(i).weatherNext.get(0).getDescription();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponseNextDays> call, Throwable t) {
                Log.i("nice", t.getMessage());
            }
        });*/
        message="hi";
        Log.i("hello", "asdasd");


    }

    public static ArrayList<String> createWeatherList(){
        ArrayList<String> messages = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                new GetWeatherForecast(MainActivity.getCity(), 6);
                Log.i("hello", ":)");
                getLocationFromNameNextDays(getwCity(), getwI());
                messages.add(getMessage());
                //  nextDay+=8;
            }

        return messages;
    }

}
