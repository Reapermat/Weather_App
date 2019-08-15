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
import android.support.v4.content.ContextCompat;
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
import com.matthewferry.ideoweather.model.helper.LocaleHelper;
import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.model.interfaces.WeatherServiceLocationToday;
import com.matthewferry.ideoweather.model.interfaces.WeatherServiceNameToday;
import com.matthewferry.ideoweather.model.serviceGenerator.ServiceGenerator;
import com.matthewferry.ideoweather.model.util.WeatherResponseToday;
import com.matthewferry.ideoweather.model.util.WeatherToday;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    private static String units = "imperial";
    private static String language ="en";
    private static String city;
    String message = "";
    Button celsius;
    Button fahrenheit;
    Button english;
    Button polish;
    Button check;
    Button nextDayForecast;
    Intent i;
    ImageButton favorite;
    ImageButton setLocation;
    char temp = 'F';
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLocation;
    boolean geo = false;
    boolean done;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String checkWeather_s;
    String enterCity;
    String weatherNotFound;
    String yourLocation;
    String lang;
    String nextDay;
    String appname;
    View view;
    public String BaseUrl = "http://api.openweathermap.org/";
    public String AppId = "c3ae299cd9fa2fa369c0839cc39e7b84";
    Toolbar myToolbar;




    /*public void ToCelsius(View view){

        units="metric";
        celsius.setClickable(false);
        celsius.setAlpha(1);
        fahrenheit.setClickable(true);
        fahrenheit.setAlpha(0.5f);
        temp = 'C';
        if(!textView.getText().toString().equals("")) {
            if (!geo) {
                check.callOnClick();
            } else {
                setLocation.callOnClick();
            }
        }
        editor.putString("temperature", "C");
        editor.putString("units", "metric");
        editor.commit();
        savePreferences("temperature", "C");
    }

    public void ToFahrenheit(View view){

        units="imperial";
        fahrenheit.setClickable(false);
        fahrenheit.setAlpha(1);
        celsius.setAlpha(0.5f);
        celsius.setClickable(true);
        temp = 'F';
        if(!textView.getText().toString().equals("")) {
            if (!geo) {
                check.callOnClick();
            } else {
                setLocation.callOnClick();
            }
        }
        editor.putString("temperature", "F");
        editor.putString("units", "imperial");
        editor.commit();
        savePreferences("temperature", "F");

    }

    public void ToEnglish(View view){

        LocaleHelper.setLocale(this, "en");
        check.setText(checkWeather_s);
        editText.setHint(enterCity);
        nextDayForecast.setText(nextDay);
        english.setClickable(false);
        english.setAlpha(1);
        polish.setAlpha(0.5f);
        polish.setClickable(true);
        language = "en";
        editor.putString("language", "en");
        editor.commit();
        savePreferences("language", "en");
        recreate();
        deleteCache(this);
        setLangButton();
        try {
            editText.setText(getCity());
            Log.i("city", getCity());
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!textView.getText().toString().equals("")) {
            if (!geo) {
                check.callOnClick();
            } else {
                setLocation.callOnClick();
            }
        }

    }

    public void ToPolish(View view){

        LocaleHelper.setLocale(this, "pl");
        check.setText(checkWeather_s);
        editText.setHint(enterCity);
        nextDayForecast.setText(nextDay);
        polish.setClickable(false);
        polish.setAlpha(1);
        english.setAlpha(0.5f);
        english.setClickable(true);
        language = "pl";
        editor.putString("language", "pl");
        editor.commit();
        savePreferences("language", "pl");
        recreate();
        deleteCache(this);
        setLangButton();
        try {
            editText.setText(getCity());
            Log.i("city", getCity());
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!textView.getText().toString().equals("")) {
            if (!geo) {
                check.callOnClick();
            } else {
                setLocation.callOnClick();
            }
        }

    }*/

    /*public void loadLocale(){
        lang = pref.getString("language", "");
        LocaleHelper.setLocale(this, lang);
    }*/


    public static String getLanguage(){
        return language;
    }



    public static String getCity(){
        return city;
    }

    public void FindViews(){

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        /*celsius = findViewById(R.id.celsiusButton);
        fahrenheit = findViewById(R.id.fahrenheitButton);
        english = findViewById(R.id.englishButton);
        polish = findViewById(R.id.polishButton);*/
        check = findViewById(R.id.button);
        setLocation = findViewById(R.id.setLocation);
        //favorite = findViewById(R.id.back);
        nextDayForecast = findViewById(R.id.nextDayForecast);
        enterCity = this.getString(R.string.enter_city);
        checkWeather_s = this.getString(R.string.check_weather);
        weatherNotFound = this.getString(R.string.weather_not_found);
        yourLocation = this.getString(R.string.your_location);
        nextDay = this.getString(R.string.next_days);
        appname = this.getString(R.string.app_name);
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
        } catch (Exception e) {
            done=false;
            e.printStackTrace();
            ToastMessage();

        }

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //makes keyboard disappear
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);

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
            //LocaleHelper.setLocale(this, lang);
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
            /*check.setText(checkWeather_s);
            nextDayForecast.setText(nextDay);
            editText.setHint(enterCity);*/
            language = "en";
            savePreferences("language", "en");
        }
        if(pref.getString("language",null).equals("en") ){
            /*check.setText(checkWeather_s);
            nextDayForecast.setText(nextDay);
            editText.setHint(enterCity);*/
            language = "en";
            savePreferences("language", "en");
        }else if(pref.getString("language",null).equals("pl")){
            /*check.setText(checkWeather_s);
            nextDayForecast.setText(nextDay);
            editText.setHint(enterCity);*/
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
            Call<WeatherResponseToday> call = service.getCurrentWeatherDataFromLocation(lat, longi, SettingsActivity.getLanguage(), pref.getString("units", null), AppId);
            call.enqueue(new Callback<WeatherResponseToday>() {
                @Override
                public void onResponse(Call<WeatherResponseToday> call, Response<WeatherResponseToday> response) {
                    try {
                        WeatherResponseToday weatherResponseToday = response.body();
                        //Log.i("server Response", response.body().toString());
                        String name = weatherResponseToday.name;
                        String t = String.valueOf(weatherResponseToday.main.temp);
                        ArrayList<WeatherToday> weatherList = response.body().getWeather();
                        city = name;
                        editText.setText(getCity());
                        Log.i("name", getCity());

                        message = yourLocation + " " + name + "\r\n" + t + (char) 0x00B0 + pref.getString("temperature", null) + "\r\n" + weatherList.get(0).getDescription();
                        textView.setText(message);
                        done = true;
                        geo=false;
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
            Call<WeatherResponseToday> call = service.getCurrentWeatherDataFromName(City, SettingsActivity.getLanguage(), pref.getString("units", null), AppId);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, locationListener);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
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

        pref = getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        setLangButton();
        setTempButton();
        loadPreferences();
        setContentView(R.layout.activity_main);
        FindViews();
        myToolbar.setTitle(appname);
        setSupportActionBar(myToolbar);
        Location();
        check.setText(checkWeather_s);
        editText.setHint(enterCity);
        nextDayForecast.setText(nextDay);
        getInt();
        /*loadLocale();*/
        editText.setText(getCity());
        if(!editText.getText().toString().equals("")){
            Check(view);
        }
    }

}
