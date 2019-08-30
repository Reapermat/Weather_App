package com.matthewferry.ideoweather.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.activity.DetailedWeatherForecastForNextDays;
import com.matthewferry.ideoweather.adapter.WeatherRecyclerViewAdapter;
import com.matthewferry.ideoweather.model.GetWeatherForecast;
import com.matthewferry.ideoweather.realm.CitySearchDB;

import java.util.ArrayList;

import io.realm.Realm;


public class WeatherListFragment extends Fragment{
    private RecyclerView recyclerView;
    public WeatherRecyclerViewAdapter weatherRecyclerViewAdapter;
    public static ArrayList<GetWeatherForecast> getWeatherForecasts;
    public static Realm realm;
    private WeatherRecyclerViewAdapter adapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycler_weather, container, false);
        recyclerView = view.findViewById(R.id.recyclerWeather);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        weatherRecyclerViewAdapter = new WeatherRecyclerViewAdapter(getContext(), getWeatherForecasts);
        recyclerView.setAdapter(weatherRecyclerViewAdapter);
        weatherRecyclerViewAdapter.setClickListener(new WeatherRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onItemClickToast(position);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWeatherForecasts = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        doSmth();
    }

    public static void doSmth() {
        try {
            if (realm.where(CitySearchDB.class).count() == 5) {
                for (int i = 0; i < 5; i++) {
                    getWeatherForecasts.add(new GetWeatherForecast(realm.where(CitySearchDB.class).findAll().get(i).getCity()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onItemClickToast(int position) {
        Intent intent = new Intent(getContext(), DetailedWeatherForecastForNextDays.class);
        intent.putExtra("Weather_Forecast", getWeatherForecasts.get(position).getwCity());
        startActivity(intent);
    }
}
