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
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.matthewferry.ideoweather.model.adapter.WeatherViewPagerAdapter;
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
import com.matthewferry.ideoweather.view.WeatherListFragment;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity /*implements RecyclerViewWeatherAdapter.ItemClickListener*/{

    static Realm realm;
    private EditText editText;
    private static String units = "imperial";
    private static String language ="en";
    private static String city;
    public static String message = "";
    private Button check;
    private Button nextDayForecast;
    private Intent i;
    private ImageButton setLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng userLocation;
    private boolean geo = false;
    private boolean done;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
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
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    public static ArrayList<String> messages = new ArrayList<>();
    private ViewPager viewPager;
    public static String message1;
    private CircleIndicator circleIndicator;
    public WeatherViewPagerAdapter weatherViewPagerAdapter;

    public static String getLanguage(){
        return language;
    }



    public static String getCity(){
        return city;
    }

    public void findViews(){

        editText = findViewById(R.id.editText);
       // textView = findViewById(R.id.textView);
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
        viewPager = findViewById(R.id.mainViewPager);
        /*circleIndicator = findViewById(R.id.circleMain);
        circleIndicator.setViewPager(viewPager);*/
    }



    public void toastMessage(){
        Toast.makeText(getApplicationContext(), weatherNotFound, Toast.LENGTH_SHORT).show();
        //textView.setText("");
        done=false;
    }

    public void setLocation(View view) {

        location();

        editText.setText("");
        try {
            geo = true;
            message = yourLocation + "\r\n";
            getWeatherFromLocation(String.valueOf(userLocation.latitude), String.valueOf(userLocation.longitude));
        } catch (Exception e) {
            geo=false;
            e.printStackTrace();
            toastMessage();
        }
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //makes keyboard disappear
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void location(){

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

    public void check(View view){
        try {

            message = "";
            geo = false;
            city=editText.getText().toString();
            if(realm.where(CitySearchDB.class).count()>4){
                DataHelper.deleteCitySearch(realm);
            }
            getWeatherFromName(city);
            for(int i=6; i<=38; i+=8) {
                getWeatherFromNameNextDays(city, i);
                Log.i("i", String.valueOf(i));
            }

            if(realm.where(CitySearchDB.class).count()==5){
                //fragmentManager();
            }


            Log.i("messageWeather",messages.toString());
            //Log.i("createWeatherList", GetWeatherForecast.getMessage());

        } catch (Exception e) {
            done=false;
            e.printStackTrace();
            toastMessage();
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
            check(view);

        if(done) {
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

        if(pref.getString("language", null) == null) {
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
                        //message =  name + "\r\n" + t + (char) 0x00B0 + pref.getString("temperature", null) + "\r\n" + weatherList.get(0).getDescription();
                        //textView.setText(yourLocation + " " +message);
                        if(realm.where(CitySearchDB.class).count()>4){
                            DataHelper.deleteCitySearch(realm);
                        }
                        for(int i=6; i<=38; i+=8) {
                            getWeatherFromNameNextDays(city, i);
                            Log.i("i", String.valueOf(i));
                        }
                        done = true;
                        geo=false;


                    } catch (Exception e) {
                        e.printStackTrace();
                        toastMessage();
                        done = false;
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponseToday> call, Throwable t) {
                    Log.i("nice", t.getMessage());
                    toastMessage();
                }
            });
        }
    }


    public static String getWeatherMessage() {
        return message1;
    }

    public void getWeatherFromName (final String City) {

        if (City.equals(null)) {
            toastMessage();
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
                        message1 = City + "\r\n" + t + (char) 0x00B0 + pref.getString("temperature", null) + "\r\n" + weatherList.get(0).getDescription();
                        //textView.setText(message);
                        city=editText.getText().toString();
                        done=true;
                        //onAddCitySearch(message);
                        Log.i("message", getWeatherMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        toastMessage();
                        done=false;
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponseToday> call, Throwable t) {
                    Log.i("nice", t.getMessage());
                    toastMessage();
                }
            });
        }
    }

    public void getWeatherFromNameNextDays (final String city, final int i) {
        Log.i("value i: ", String.valueOf(i));

        WeatherServiceNameNext service = ServiceGenerator.createService(WeatherServiceNameNext.class);
        Call<WeatherResponseNextDays> call = service.getCurrentDataFromNameNextDays(city, pref.getString("language", null), pref.getString("units", null), AppId);
        call.enqueue(new Callback<WeatherResponseNextDays>() {

            @Override
            public void onResponse(Call<WeatherResponseNextDays> call, Response<WeatherResponseNextDays> response) {
                Log.i("value i: ", String.valueOf(i));
                try {
                    WeatherResponseNextDays weatherResponseNextDays = response.body();
                    Log.i("server Response", response.body().toString());
                    //ArrayList<List> list = response.body().getList();
                    Log.i("working?", MainActivity.getCity());

                    ArrayList<List> list = response.body().getList();
                    String date = list.get(i).getDtTxt();
                    String t = String.valueOf(weatherResponseNextDays.getList().get(i).main.temp);
                    Log.i("what is this", list.get(i).getDtTxt());
                    message = date + "\r\n" + city + "\r\n" + t + (char) 0x00B0 + pref.getString("temperature", null) + "\r\n" + list.get(i).weatherNext.get(0).getDescription();
                    onAddCitySearch(message);
                    Log.i("realm:", realm.where(CitySearchDB.class).findAll().toString());
                    Log.i("realmCount", String.valueOf(realm.where(CitySearchDB.class).count()));
                    if(realm.where(CitySearchDB.class).count()==5){
                        //fragmentManager();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    toastMessage();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponseNextDays> call, Throwable t) {
                Log.i("nice", t.getMessage());
            }
        });
    }


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

    private void fragmentManager(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragment = fragmentManager.findFragmentById(R.id.recyclerWeather);
        fragment = new WeatherListFragment();
        fragmentTransaction.add(R.id.mainViewPager, fragment);
        fragmentTransaction.commit();
    }

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
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        realmConfig();
        pref = getSharedPreferences("MyPref",MODE_PRIVATE);
        editor = pref.edit();
        setLangButton();
        setTempButton();
        loadPreferences();
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        findViews();
        viewPager.setAdapter(new WeatherViewPagerAdapter(getSupportFragmentManager()));
        myToolbar.setTitle(appName);
        setSupportActionBar(myToolbar);
        location();
        check.setText(checkWeather_s);
        editText.setHint(enterCity);
        nextDayForecast.setText(nextDay);
        getInt();
        if(realm.where(CitySearchDB.class).count()==5){
            //fragmentManager();
        }
        editText.setText(getCity());
        if(!editText.getText().toString().equals("")){
            check(view);
        }

    }

}
