package com.matthewferry.ideoweather.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.matthewferry.ideoweather.R;

public class DetailedWeatherForecastForNextDays extends AppCompatActivity {

    Intent i;
    TextView textView;
    private Toolbar toolbar;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather_forecast_for_next_days);
        textView = findViewById(R.id.detailedWeatherForecast);
        toolbar = findViewById(R.id.toolbar_forecast);
        title = this.getString(R.string.detailed_forecast);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getInt();
    }


    private void getInt(){
        i = getIntent();
        textView.setText(i.getStringExtra("Weather_Forecast"));
    }
}
