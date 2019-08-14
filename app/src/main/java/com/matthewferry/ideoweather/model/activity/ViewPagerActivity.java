package com.matthewferry.ideoweather.model.activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.model.adapter.MyPagerAdapter;
import com.matthewferry.ideoweather.model.interfaces.WeatherServiceNameNext;
import com.matthewferry.ideoweather.model.serviceGenerator.ServiceGenerator;
import com.matthewferry.ideoweather.model.util.List;
import com.matthewferry.ideoweather.model.util.WeatherResponseNextDays;

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
    MyPagerAdapter myPager;
    TextView textView;
    EditText editText;
    SharedPreferences sharedPreferences;
    String weatherNotFound;
    String yourLocation;
    public String BaseUrl = "http://api.openweathermap.org/";
    public String AppId = "c3ae299cd9fa2fa369c0839cc39e7b84";
    String message;
    String wait;
    ArrayList<String> weatherList;
    String nextDays;
    Toolbar myToolbar;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPreferences();
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

    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            if(i==0){
                getLocationFromName(MainActivity.getCity(), 6);
            }
            else if(i==1){
                getLocationFromName(MainActivity.getCity(), 14);
            }else if(i==2){
                getLocationFromName(MainActivity.getCity(), 22);
            }else if(i==3)
                getLocationFromName(MainActivity.getCity(), 30);
            else if(i==4)
                getLocationFromName(MainActivity.getCity(), 38);
            else
                getLocationFromName(MainActivity.getCity(), 0);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


    private void loadPreferences(){
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Log.i("language", sharedPreferences.getString("language", null));
            //LocaleHelper.setLocale(this, lang);
            setLocal(sharedPreferences.getString("language", null));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setLocal(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        editor.putString("language", lang);
        editor.apply();
    }

    /*public void goToMain(View view){
        Intent intent = new Intent(ViewPagerActivity.this, MainActivity.class);
        startActivity(intent);
    }*/

    public void getLocationFromName (final String City, final int i) {


            WeatherServiceNameNext service = ServiceGenerator.createService(WeatherServiceNameNext.class);
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
                        //weatherList.add(message);
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

        public ArrayList<String> getList(){
        return weatherList;
        }

        private void findViews(){
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
