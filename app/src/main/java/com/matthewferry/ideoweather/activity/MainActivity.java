package com.matthewferry.ideoweather.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather._interface.OpenWeatherMap;
import com.matthewferry.ideoweather.adapter.WeatherViewPagerAdapter;
import com.matthewferry.ideoweather.helper.DataHelper;
import com.matthewferry.ideoweather.helper.SharedPreference;
import com.matthewferry.ideoweather.model.List;
import com.matthewferry.ideoweather.model.WeatherResponseNextDays;
import com.matthewferry.ideoweather.model.WeatherResponseToday;
import com.matthewferry.ideoweather.model.WeatherToday;
import com.matthewferry.ideoweather.realm.CitySearchDB;
import com.matthewferry.ideoweather.servicegenerator.ServiceGenerator;
import com.matthewferry.ideoweather.view.WeatherListFragment;

import java.security.SecureRandom;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.matthewferry.ideoweather.helper.SharedPreference.savePreferences;


public class MainActivity extends AppCompatActivity {

    static Realm realm;
    private EditText editText;
    private static String units = "imperial";
    private static String language = "en";
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
    private String checkWeather_s;
    private String enterCity;
    private String weatherNotFound;
    private String yourLocation;
    public String nextDay;
    private String appName;
    private String lastSearch;
    public View view;
    private Toolbar myToolbar;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;


    public static String getLanguage() {
        return language;
    }

    public static String getCity() {
        return city;
    }

    private void findViews() {

        editText = findViewById(R.id.editText);
        check = findViewById(R.id.button);
        setLocation = findViewById(R.id.setLocation);
        //nextDayForecast = findViewById(R.id.nextDayForecast);
        enterCity = this.getString(R.string.enter_city);
        checkWeather_s = this.getString(R.string.check_weather);
        weatherNotFound = this.getString(R.string.weather_not_found);
        yourLocation = this.getString(R.string.your_location);
        nextDay = this.getString(R.string.next_days);
        appName = this.getString(R.string.app_name);
        lastSearch = this.getString(R.string.last_search);
        myToolbar = findViewById(R.id.main_toolbar);
        viewPager = findViewById(R.id.mainViewPager);
        viewPager.setAdapter(new WeatherViewPagerAdapter(getSupportFragmentManager()));
        circleIndicator = findViewById(R.id.circleMain);
        circleIndicator.setViewPager(viewPager);
    }

    private void toastMessage() {
        Toast.makeText(getApplicationContext(), weatherNotFound, Toast.LENGTH_SHORT).show();
        done = false;
    }

    public void setLocation(View view) {

        //location();
        requestSingleUpdate(getApplicationContext());
        editText.setText("");
        try {
            geo = true;
            message = yourLocation + "\r\n";
            getWeatherFromLocation(String.valueOf(userLocation.latitude), String.valueOf(userLocation.longitude));
        } catch (Exception e) {
            geo = false;
            e.printStackTrace();
            toastMessage();
        }
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //makes keyboard disappear
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void requestSingleUpdate(final Context context) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }/*else{
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1000, locationListener);
                }*/
                locationManager.requestSingleUpdate(criteria, new LocationListener() {
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
                }, null);
            }

        } else {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            locationManager.requestSingleUpdate(criteria, new LocationListener() {
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
            }, null);

        }


    }
   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, locationListener);
            }
        }
    }*/

    public void check(View view) {
        try {
            done = true;
            message = "";
            geo = false;
            city = editText.getText().toString();
            if (realm.where(CitySearchDB.class).count() > 4) {
                DataHelper.deleteCitySearch(realm);
            }
            getWeatherFromName(city);
            for (int i = 6; i <= 38; i += 8) {
                getWeatherFromNameNextDays(city, i);
                Log.i("i", String.valueOf(i));
                Thread.sleep(200);
            }

        } catch (Exception e) {
            done = false;
            e.printStackTrace();
            toastMessage();
        }
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //makes keyboard disappear
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void onAddCitySearch(String city) {
        SecureRandom secureRandom = new SecureRandom();
        int cityID = secureRandom.nextInt(10000);
        DataHelper.newCitySearch(realm, cityID, city);
    }


    public void goToFavorite(View view) {

        Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
        startActivity(intent);

    }

    public void goToSettings(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goToViewPager(View view) {
        if (getCity() != null) {
            if (!geo)
                check(view);
            if (done) {
                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                startActivity(intent);
                done = false;
            }
        } else {
            toastMessage();
        }
    }

    private void getInt() {
        i = getIntent();
        Bundle b = i.getExtras();
        try {
            if (b != null) {
                editText.setText(b.get("City").toString());
            }
            if (!editText.getText().toString().equals("") && SharedPreference.move) {
                check(view);
                SharedPreference.move = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTempButton() {
        if (SharedPreference.getPreference("temperature") == null || SharedPreference.getPreference("units") == null) {
            SharedPreference.setPreference("temperature", "F");
            SharedPreference.setPreference("units", "imperial");
            savePreferences("temperature", "F", getApplicationContext());
            savePreferences("units", "imperial", getApplicationContext());
        }
        if (SharedPreference.getPreference("temperature").equals("C") || SharedPreference.getPreference("units").equals("metric")) {
            units = "metric";
        } else {
            units = "imperial";
        }
    }

    private void setLangButton() {

        if (SharedPreference.getPreference("language") == null) {
            SharedPreference.setPreference("language", "en");
            language = "en";
            savePreferences("language", "en", getApplicationContext());
        }
        if (SharedPreference.getPreference("language").equals("en")) {
            language = "en";
            savePreferences("language", "en", getApplicationContext());
        } else if (SharedPreference.getPreference("language").equals("pl")) {
            language = "pl";
            savePreferences("language", "pl", getApplicationContext());
        }
    }


    private void getWeatherFromLocation(String lat, String longi) {

        if (geo) {
            OpenWeatherMap service = ServiceGenerator.createService(OpenWeatherMap.class);
            Call<WeatherResponseToday> call = service.getCurrentWeatherDataFromLocation(lat, longi, SharedPreference.getPreference("language"), SharedPreference.getPreference("units"));
            call.enqueue(new Callback<WeatherResponseToday>() {
                @Override
                public void onResponse(Call<WeatherResponseToday> call, Response<WeatherResponseToday> response) {
                    try {
                        WeatherResponseToday weatherResponseToday = response.body();
                        String name = weatherResponseToday.getName();
                        String t = String.valueOf(weatherResponseToday.getMain().getTemp());
                        ArrayList<WeatherToday> weatherList = response.body().getWeather();
                        city = name;
                        editText.setText(getCity());
                        Log.i("name", getCity());
                        SharedPreference.message1 = name + "\r\n" + t + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n" + weatherList.get(0).getDescription();
                        //textView.setText(yourLocation + " " +message);
                        if (realm.where(CitySearchDB.class).count() > 4) {
                            DataHelper.deleteCitySearch(realm);
                        }
                        for (int i = 6; i <= 38; i += 8) {
                            getWeatherFromNameNextDays(city, i);
                            Log.i("i", String.valueOf(i));
                            Thread.sleep(200);
                        }
                        done = true;
                        geo = false;
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


    private void getWeatherFromName(final String City) {

        if (City.equals(null)) {
            toastMessage();
        } else {
            OpenWeatherMap service = ServiceGenerator.createService(OpenWeatherMap.class);
            Call<WeatherResponseToday> call = service.getCurrentWeatherDataFromName(City, SharedPreference.getPreference("language"), SharedPreference.getPreference("units"));
            call.enqueue(new Callback<WeatherResponseToday>() {
                @Override
                public void onResponse(Call<WeatherResponseToday> call, Response<WeatherResponseToday> response) {
                    try {
                        WeatherResponseToday weatherResponseToday = response.body();
                        Log.i("server Response", response.body().toString());
                        String t = String.valueOf(weatherResponseToday.getMain().getTemp());
                        ArrayList<WeatherToday> weatherList = response.body().getWeather();
                        Log.i("weatherList:", weatherList.toString());
                        SharedPreference.message1 = City + "\r\n" + t + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n" + weatherList.get(0).getDescription();
                        city = editText.getText().toString();
                        done = true;
                        //onAddCitySearch(message);
                        Log.i("message", SharedPreference.getWeatherMessage());
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

    private void getWeatherFromNameNextDays(final String city, final int i) {
        Log.i("value i: ", String.valueOf(i));

        OpenWeatherMap service = ServiceGenerator.createService(OpenWeatherMap.class);
        Call<WeatherResponseNextDays> call = service.getCurrentDataFromNameNextDays(city, SharedPreference.getPreference("language"), SharedPreference.getPreference("units"));
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
                    String t = String.valueOf(weatherResponseNextDays.getList().get(i).getMain().getTemp());
                    Log.i("date", list.get(i).getDt().toString());
                    CharSequence time = DateFormat.format("EEEE", (list.get(i).getDt()) * 1000);
                    Log.i("time", time.toString());
                    message = time + "\r\n" + "(" + date + ")" + "\r\n" + city + "\r\n" + t + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n" + list.get(i).getWeather().get(0).getDescription();
                    onAddCitySearch(message);

                    if (realm.where(CitySearchDB.class).count() == 5) {
                        WeatherListFragment.doSmth();
                        recreate();
                    }
                    done = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    toastMessage();
                    done = false;
                }
            }

            @Override
            public void onFailure(Call<WeatherResponseNextDays> call, Throwable t) {
                Log.i("nice", t.getMessage());
            }
        });
    }




    private void realmConfig() {
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

        switch (item.getItemId()) {
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
        SharedPreference.loadPreferences(getBaseContext());
        setLangButton();
        setTempButton();

        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        findViews();
        getInt();
        myToolbar.setTitle(appName);
        setSupportActionBar(myToolbar);
        requestSingleUpdate(getApplicationContext());
        check.setText(checkWeather_s);
        editText.setHint(enterCity);
        //nextDayForecast.setText(nextDay);
        editText.setText(getCity());
        if (!editText.getText().toString().equals("") && SharedPreference.move) {
            check(view);
            SharedPreference.move = false;
        } else if (editText.getText().toString().equals("")) {
            SharedPreference.message1 = lastSearch;
        }

    }
}
