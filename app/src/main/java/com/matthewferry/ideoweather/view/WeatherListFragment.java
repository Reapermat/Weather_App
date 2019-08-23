package com.matthewferry.ideoweather.view;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.model.activity.FavoriteActivity;
import com.matthewferry.ideoweather.model.activity.MainActivity;
import com.matthewferry.ideoweather.model.adapter.RecyclerViewWeatherAdapter;
import com.matthewferry.ideoweather.model.realm.CitySearchDB;
import com.matthewferry.ideoweather.model.util.GetWeatherForecast;

import java.util.ArrayList;

import io.realm.Realm;


public class WeatherListFragment extends Fragment {
    private RecyclerView recyclerView;
    public static ArrayList<GetWeatherForecast> getWeatherForecasts;
    public static Realm realm;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.recycler_weather, container, false);
        recyclerView = view.findViewById(R.id.recyclerWeather);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewWeatherAdapter recyclerViewWeatherAdapter = new RecyclerViewWeatherAdapter(getContext(), getWeatherForecasts);
        recyclerView.setAdapter(recyclerViewWeatherAdapter);
        Realm.init(getContext());
        doSmth();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWeatherForecasts = new ArrayList<>();
        realm = Realm.getDefaultInstance();

    }
    public static void doSmth(){
        try {
            if (realm.where(CitySearchDB.class).count() == 5) {
                getWeatherForecasts.add(new GetWeatherForecast(realm.where(CitySearchDB.class).findAll().get(0).getCity()));
                getWeatherForecasts.add(new GetWeatherForecast(realm.where(CitySearchDB.class).findAll().get(1).getCity()));
                getWeatherForecasts.add(new GetWeatherForecast(realm.where(CitySearchDB.class).findAll().get(2).getCity()));
                getWeatherForecasts.add(new GetWeatherForecast(realm.where(CitySearchDB.class).findAll().get(3).getCity()));
                getWeatherForecasts.add(new GetWeatherForecast(realm.where(CitySearchDB.class).findAll().get(4).getCity()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("your problem?", String.valueOf(realm.where(CitySearchDB.class).count()));
    }



}
