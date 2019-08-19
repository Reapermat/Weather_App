package com.matthewferry.ideoweather.model.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.model.adapter.RecyclerViewWeatherAdapter;
import com.matthewferry.ideoweather.model.helper.DataHelper;
import com.matthewferry.ideoweather.model.interfaces.WeatherServiceLocationToday;
import com.matthewferry.ideoweather.model.interfaces.WeatherServiceNameNext;
import com.matthewferry.ideoweather.model.interfaces.WeatherServiceNameToday;
import com.matthewferry.ideoweather.model.realm.CitySearchDB;
import com.matthewferry.ideoweather.model.serviceGenerator.ServiceGenerator;
import com.matthewferry.ideoweather.model.util.List;
import com.matthewferry.ideoweather.model.util.WeatherResponseNextDays;
import com.matthewferry.ideoweather.model.util.WeatherResponseToday;
import com.matthewferry.ideoweather.model.util.WeatherToday;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity /*implements RecyclerViewWeatherAdapter.ItemClickListener*/{

    static Realm realm;
    private EditText editText;
    private TextView textView;
    private static String units = "imperial";
    private static String language ="en";
    private static String city;
    private String message = "";
    private Button check;
    private Button nextDayForecast;
    private Intent i;
    private ImageButton setLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng userLocation;
    private boolean geo = false;
    private boolean done;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String checkWeather_s;
    private String enterCity;
    private String weatherNotFound;
    private String yourLocation;
    private String lang;
    private String nextDay;
    private String appName;
    private String lastSearch;
    View view;
    public String AppId = "c3ae299cd9fa2fa369c0839cc39e7b84";
    private Toolbar myToolbar;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis;
    private long endTime;
    private static final long START_TIME_IN_MILLIS = 600000;
    RecyclerViewWeatherAdapter adapter;
    ArrayList<String> weatherData = new ArrayList<>();



    public static String getLanguage(){
        return language;
    }



    public static String getCity(){
        return city;
    }

    public void FindViews(){

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        check = findViewById(R.id.button);
        setLocation = findViewById(R.id.setLocation);
        nextDayForecast = findViewById(R.id.nextDayForecast);
        enterCity = this.getString(R.string.enter_city);
        checkWeather_s = this.getString(R.string.check_weather);
        weatherNotFound = this.getString(R.string.weather_not_found);
        yourLocation = this.getString(R.string.your_location);
        nextDay = this.getString(R.string.next_days);
        appName = this.getString(R.string.app_name);
        lastSearch = this.getString(R.string.last_search);
        myToolbar= findViewById(R.id.main_toolbar);
    }



    public void ToastMessage(){
        Toast.makeText(getApplicationContext(), weatherNotFound, 10).show();
        textView.setText("");
        done=false;
    }

    public void SetLocation(View view) {

        Location();

        editText.setText("");
        try {
            geo = true;
            message = yourLocation + "\r\n";
            getWeatherFromLocation(String.valueOf(userLocation.latitude), String.valueOf(userLocation.longitude));
        } catch (Exception e) {
            geo=false;
            e.printStackTrace();
            ToastMessage();
        }
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //makes keyboard disappear
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void Location(){

        locationManager  = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                userLocation = new LatLng(location.getLatitude(), location.getLongitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 100, locationListener);
        }
    }

    public void Check(View view){
        try {

            message = "";
            geo = false;
            city=editText.getText().toString();
            getWeatherFromName(city);
            //startTimer();
            //recyclerView();
            Log.i("timer", String.valueOf(timerRunning));

        } catch (Exception e) {
            done=false;
            e.printStackTrace();
            ToastMessage();
        }

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //makes keyboard disappear
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }

    public static void onAddCitySearch(String city) {
        SecureRandom secureRandom = new SecureRandom();
        int cityID = secureRandom.nextInt(10000);
        DataHelper.newCitySearch(realm, cityID, city);
    }

    private void setLocal(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Log.i("lang=", lang);
        editor.putString("language", lang);
        editor.commit();
    }

    public void loadPreferences(){
        try {
            pref = PreferenceManager.getDefaultSharedPreferences(this);
            lang = pref.getString("language", null);
            Log.i("language", pref.getString("language", null));
            Log.i("units", pref.getString("units", null));
            setLocal(lang);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void goToFavorite(View view){

        Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
        startActivity(intent);

    }

    public void goToSettings(View view){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goToViewPager(View view) {
        if(!geo)
            Check(view);

        if(done==true) {
            Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
            startActivity(intent);
            done = false;
        }
    }

    public void getInt() {
        i = getIntent();
        editText.setText(i.getStringExtra("City"));
        if (!editText.getText().toString().equals("")) {
            check.callOnClick();
        }
    }

    public void setTempButton(){
        if(pref.getString("temperature", null) == null || pref.getString("units", null) == null){
            editor.putString("temperature", "F");
            editor.putString("units", "imperial");
            editor.commit();
            savePreferences("temperature", "F");
            savePreferences("units", "imperial");
        }
        if (pref.getString("temperature", null).equals("C") || pref.getString("units", null).equals("metric")){
            units="metric";

        }else{
            units = "imperial";
        }
    }

    public void setLangButton(){

        if(pref.getString("language", null) == null ) {
            editor.putString("language", "en");
            editor.commit();
            language = "en";
            savePreferences("language", "en");
        }
        if(pref.getString("language",null).equals("en") ){
            language = "en";
            savePreferences("language", "en");
        }else if(pref.getString("language",null).equals("pl")){
            language = "pl";
            savePreferences("language", "pl");
        }
    }

    private void savePreferences(String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void getWeatherFromLocation(String lat, String longi) {

        if (geo) {

            WeatherServiceLocationToday service = ServiceGenerator.createService(WeatherServiceLocationToday.class);
            Call<WeatherResponseToday> call = service.getCurrentWeatherDataFromLocation(lat, longi, pref.getString("language", null ), pref.getString("units", null), AppId);
            call.enqueue(new Callback<WeatherResponseToday>() {
                @Override
                public void onResponse(Call<WeatherResponseToday> call, Response<WeatherResponseToday> response) {
                    try {
                        WeatherResponseToday weatherResponseToday = response.body();
                        String name = weatherResponseToday.name;
                        String t = String.valueOf(weatherResponseToday.main.temp);
                        ArrayList<WeatherToday> weatherList = response.body().getWeather();
                        city = name;
                        editText.setText(getCity());
                        Log.i("name", getCity());
                        message =  name + "\r\n" + t + (char) 0x00B0 + pref.getString("temperature", null) + "\r\n" + weatherList.get(0).getDescription();
                        textView.setText(yourLocation + " " +message);
                        done = true;
                        geo=false;
                        onAddCitySearch(message);
                        if(realm.where(CitySearchDB.class).count()>1){
                            DataHelper.deleteCitySearch(realm);
                        }
                        Log.i("realm:", realm.where(CitySearchDB.class).findAll().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastMessage();
                        done = false;
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponseToday> call, Throwable t) {
                    Log.i("nice", t.getMessage());
                }
            });
        }
    }

    public void getWeatherFromName (final String City) {

        if (City.equals(null)) {
            ToastMessage();
        } else {

            WeatherServiceNameToday service = ServiceGenerator.createService(WeatherServiceNameToday.class);
            Call<WeatherResponseToday> call = service.getCurrentWeatherDataFromName(City, pref.getString("language", null), pref.getString("units", null), AppId);
            call.enqueue(new Callback<WeatherResponseToday>() {
                @Override
                public void onResponse(Call<WeatherResponseToday> call, Response<WeatherResponseToday> response) {
                    try {
                        WeatherResponseToday weatherResponseToday = response.body();
                        Log.i("server Response", response.body().toString());
                        String t = String.valueOf(weatherResponseToday.main.temp);
                        ArrayList<WeatherToday> weatherList = response.body().getWeather();
                        Log.i("weatherList:", weatherList.toString());
                        message = City + "\r\n" + t + (char) 0x00B0 + pref.getString("temperature", null) + "\r\n" + weatherList.get(0).getDescription();
                        textView.setText(message);
                        city=editText.getText().toString();
                        done=true;
                        onAddCitySearch(message);
                        if(realm.where(CitySearchDB.class).count()>1){
                            DataHelper.deleteCitySearch(realm);
                        }
                        Log.i("realm:", realm.where(CitySearchDB.class).findAll().toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastMessage();
                        done=false;
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponseToday> call, Throwable t) {
                    Log.i("nice", t.getMessage());
                }
            });
        }
    }

    /*public void getLocationFromNameNextDays (final String City, final int i) {


        WeatherServiceNameNext service = ServiceGenerator.createService(WeatherServiceNameNext.class);
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
                    *//*textView.setText(message);*//*
                    weatherData.add(message);
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
    }*/


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, locationListener);
            }
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

   /* private void startTimer(){

        *//*timeLeftInMillis=START_TIME_IN_MILLIS;*//*
        endTime = System.currentTimeMillis() + timeLeftInMillis;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
            }

            @Override
            public void onFinish() {
                //timerRunning = false;
            }
        }.start();
        timerRunning = true;
    }



    @Override
    protected void onStop() {
        super.onStop();

        Log.i("a tu? ", String.valueOf(timeLeftInMillis));
        editor.putLong("millisLeft", timeLeftInMillis);
        //editor.putBoolean("timerRunning", timerRunning);
        editor.putLong("endTime", endTime);
        editor.commit();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


        timeLeftInMillis = pref.getLong("millisLeft", START_TIME_IN_MILLIS);
        timerRunning = pref.getBoolean("timerRunning", false);

        Log.i("a tu? ", String.valueOf(timeLeftInMillis));
        Log.i("running", String.valueOf(timerRunning));
        if (timerRunning){
            endTime = pref.getLong("endTime", 0);
            timeLeftInMillis = endTime - System.currentTimeMillis();
            Log.i("a tu? ", String.valueOf(timeLeftInMillis));

            if(timeLeftInMillis<0){
                timeLeftInMillis = 0;
                timerRunning = false;
            }else{
                startTimer();
            }
        }
    }*/

    /*public void recyclerView(){
        getLocationFromNameNextDays(getCity(), 6);
        weatherData.add(message);
        getLocationFromNameNextDays(getCity(), 14);
        weatherData.add("asdasd");
        getLocationFromNameNextDays(getCity(), 22);
        weatherData.add("adfasdf");
        getLocationFromNameNextDays(getCity(), 30);
        weatherData.add("asdff");
        getLocationFromNameNextDays(getCity(), 38);
        weatherData.add("asdf");

        Log.i("weatherData", weatherData.toString());
        try {
            RecyclerView recyclerView = findViewById(R.id.recyclerWeather);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new RecyclerViewWeatherAdapter(this, weatherData);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        }catch(Exception e){
            e.printStackTrace();
        }


    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }*/

    public void realmConfig(){
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.settings:
                goToSettings(view);
                break;
            case R.id.favorite:
                goToFavorite(view);
                break;
            default:
                Toast.makeText(this, "error", 10).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        realmConfig();
        pref = getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        setLangButton();
        setTempButton();
        loadPreferences();
        setContentView(R.layout.activity_main);
        //recyclerView();
        realm = Realm.getDefaultInstance();
        FindViews();
        myToolbar.setTitle(appName);
        setSupportActionBar(myToolbar);
        Location();
        check.setText(checkWeather_s);
        editText.setHint(enterCity);
        nextDayForecast.setText(nextDay);
        getInt();

        //if(timerRunning) {
            if (realm.where(CitySearchDB.class).count() != 0) {
                textView.setText(lastSearch + "\r\n" + realm.where(CitySearchDB.class).findAll().last().getCity());
            }
       // }
        editText.setText(getCity());
        if(!editText.getText().toString().equals("")){
            Check(view);
        }
    }

}
