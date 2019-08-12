package com.matthewferry.ideoweather.activity.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.activity.weather_services_for_api.WeatherServiceNameNext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewPagerActivity extends AppCompatActivity {

    ViewPager viewPager;
    CircleIndicator circleIndicator;
    MyPager myPager;
    TextView textView;
    EditText editText;
    SharedPreferences sharedPreferences;
    String weatherNotFound;
    String yourLocation;
    public String BaseUrl = "http://api.openweathermap.org/";
    public String AppId = "c3ae299cd9fa2fa369c0839cc39e7b84";
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.viewPager);
        myPager = new MyPager(this);
        viewPager.setAdapter(myPager);
        circleIndicator = findViewById(R.id.circle);
        circleIndicator.setViewPager(viewPager);
        textView = findViewById(R.id.nextDays);
        editText = findViewById(R.id.editText);
        weatherNotFound = this.getString(R.string.weather_not_found);
        yourLocation = this.getString(R.string.your_location);
        getLocationfromName(MainActivity.getCity(), 6);
        loadPreferences();
        viewPager.addOnPageChangeListener(listener);

    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            if(i==0){
                getLocationfromName(MainActivity.getCity(), 6);
            }
            else if(i==1){
                getLocationfromName(MainActivity.getCity(), 14);
            }else if(i==2){
                getLocationfromName(MainActivity.getCity(), 22);
            }else if(i==3)
                getLocationfromName(MainActivity.getCity(), 30);
            else if(i==4)
                getLocationfromName(MainActivity.getCity(), 38);
            else
                getLocationfromName(MainActivity.getCity(), 0);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


    private void loadPreferences(){
        try{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void goToMain(View view){
        Intent intent = new Intent(ViewPagerActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void getLocationfromName (final String City, final int i) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WeatherServiceNameNext service = retrofit.create(WeatherServiceNameNext.class);
            Call<WeatherResponseNextDays> call = service.getCurrentDataFromNameNextDays(City, MainActivity.getLanguage(), MainActivity.getUnits(), AppId);
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

                        message = date +"\r\n\r\n" + City + "\r\n" + t + (char) 0x00B0 + sharedPreferences.getString("temperature", null) + "\r\n" +  list.get(i).weatherNext.get(0).getDescription();
                        textView.setText(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), City, 10).show();
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponseNextDays> call, Throwable t) {
                    Log.i("nice", t.getMessage());
                }
            });
        }



}
