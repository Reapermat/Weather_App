package com.matthewferry.ideoweather.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather._interface.OpenWeatherMap;
import com.matthewferry.ideoweather.adapter.MyPagerAdapter;
import com.matthewferry.ideoweather.helper.SharedPreference;
import com.matthewferry.ideoweather.model.List;
import com.matthewferry.ideoweather.model.WeatherResponseNextDays;
import com.matthewferry.ideoweather.servicegenerator.ServiceGenerator;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private MyPagerAdapter myPager;
    private TextView textView;
    public EditText editText;
    public String weatherNotFound;
    public String yourLocation;
    private String message;
    private String wait;
    private String nextDays;
    private Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreference.loadPreferences(getApplicationContext());
        setContentView(R.layout.activity_view_pager);
        viewPager = findViewById(R.id.viewPager);
        myPager = new MyPagerAdapter(this);
        viewPager.setAdapter(myPager);
        findViews();
        textView.setText(wait);
        myToolbar.setTitle(nextDays);
        getLocationFromName(MainActivity.getCity(), 6);
        viewPager.addOnPageChangeListener(listener);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreference.move = true;

    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            if (i == 0) {
                getLocationFromName(MainActivity.getCity(), 6);
            } else if (i == 1) {
                getLocationFromName(MainActivity.getCity(), 14);
            } else if (i == 2) {
                getLocationFromName(MainActivity.getCity(), 22);
            } else if (i == 3)
                getLocationFromName(MainActivity.getCity(), 30);
            else if (i == 4)
                getLocationFromName(MainActivity.getCity(), 38);
            else
                getLocationFromName(MainActivity.getCity(), 0);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private void getLocationFromName(final String City, final int i) {

        OpenWeatherMap service = ServiceGenerator.createService(OpenWeatherMap.class);
        Call<WeatherResponseNextDays> call = service.getCurrentDataFromNameNextDays(City, SharedPreference.getPreference("language"), SharedPreference.getPreference("units"));
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
                    String t = String.valueOf(weatherResponseNextDays.getList().get(i).getMain().getTemp());
                    Log.i("what is this", list.get(i).getDtTxt());

                    message = date + "\r\n\r\n" + City + "\r\n" + t + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n" + list.get(i).getWeather().get(0).getDescription();
                    textView.setText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), City, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponseNextDays> call, Throwable t) {
                Log.i("nice", t.getMessage());
            }
        });
    }


    private void findViews() {
        viewPager = findViewById(R.id.viewPager);
        myPager = new MyPagerAdapter(this);
        viewPager.setAdapter(myPager);
        circleIndicator = findViewById(R.id.circle);
        circleIndicator.setViewPager(viewPager);
        textView = findViewById(R.id.nextDays);
        editText = findViewById(R.id.editText);
        weatherNotFound = this.getString(R.string.weather_not_found);
        yourLocation = this.getString(R.string.your_location);
        wait = this.getString(R.string.please_wait);
        nextDays = this.getString(R.string.next_days);
        myToolbar = findViewById(R.id.view_toolbar);
    }


}
