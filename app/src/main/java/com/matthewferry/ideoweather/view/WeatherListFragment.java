package com.matthewferry.ideoweather.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.model.adapter.RecyclerViewWeatherAdapter;
import com.matthewferry.ideoweather.model.realm.CitySearchDB;
import com.matthewferry.ideoweather.model.util.GetWeatherForecast;
import java.util.ArrayList;
import io.realm.Realm;


public class WeatherListFragment extends Fragment implements RecyclerViewWeatherAdapter.ItemClickListener{
    private RecyclerView recyclerView;
    public RecyclerViewWeatherAdapter recyclerViewWeatherAdapter;
    public static ArrayList<GetWeatherForecast> getWeatherForecasts;
    public static Realm realm;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.recycler_weather, container, false);
        recyclerView = view.findViewById(R.id.recyclerWeather);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewWeatherAdapter = new RecyclerViewWeatherAdapter(getContext(), getWeatherForecasts);
        recyclerView.setAdapter(recyclerViewWeatherAdapter);
        recyclerViewWeatherAdapter.setClickListener(this);
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
    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(),recyclerViewWeatherAdapter.getItem(position), Toast.LENGTH_SHORT).show();
    }
}
