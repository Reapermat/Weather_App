package com.matthewferry.ideoweather.view;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.model.adapter.RecyclerViewWeatherAdapter;


public class WeatherListFragment extends Fragment {
    RecyclerView recyclerView;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.recycler_weather, container, false);
        recyclerView = view.findViewById(R.id.recyclerWeather);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewWeatherAdapter recyclerViewWeatherAdapter = new RecyclerViewWeatherAdapter();
        recyclerView.setAdapter(recyclerViewWeatherAdapter);
    }
}
