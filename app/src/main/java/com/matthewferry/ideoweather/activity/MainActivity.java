package com.matthewferry.ideoweather.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import com.matthewferry.ideoweather.adapter.WeatherViewPagerAdapter;
import com.matthewferry.ideoweather.api.OpenWeatherMap;
import com.matthewferry.ideoweather.api.ServiceGenerator;
import com.matthewferry.ideoweather.helper.DataHelper;
import com.matthewferry.ideoweather.helper.RealmUtil;
import com.matthewferry.ideoweather.helper.SharedPreference;
import com.matthewferry.ideoweather.model.List;
import com.matthewferry.ideoweather.model.WeatherResponseNextDays;
import com.matthewferry.ideoweather.model.WeatherResponseToday;
import com.matthewferry.ideoweather.model.WeatherToday;
import com.matthewferry.ideoweather.realm.CitySearchDB;
import com.matthewferry.ideoweather.realm.CurrentForecast;
import com.matthewferry.ideoweather.view.WeatherListFragment;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.matthewferry.ideoweather.helper.SharedPreference.getCity;
import static com.matthewferry.ideoweather.helper.SharedPreference.message1;
import static com.matthewferry.ideoweather.helper.SharedPreference.savePreferences;


public class MainActivity extends AppCompatActivity {

    static Realm realm;
    private EditText editText;
    private static String units = "imperial";
    private static String language = "en";
    private String message = "";
    private Button check;
    private ImageButton favorite;
    private Intent i;
    private ImageButton setLocation;
    private LatLng userLocation;
    private boolean geo = false;
    private String checkWeather_s;
    private String enterCity;
    private String weatherNotFound;
    private String yourLocation;
    public String nextDay;
    private String appName;
    private String lastSearch;
    private String pressure;
    private String humidity;
    private String minTemp;
    private String maxTemp;
    private String presentTemp;
    private String locatingUser;
    private String userLocated;
    public View view;
    private Toolbar myToolbar;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private CharSequence timeStamp;
    private CharSequence timeStart;
    private double temperature;
    private double pressureCalc;
    private double humidityCalc;
    private double minTempCalc;
    private double maxTempCalc;
    private boolean locationSet = false;
    private String cityAdded;


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
        pressure = this.getString(R.string.pressure);
        humidity = this.getString(R.string.humidity);
        minTemp = this.getString(R.string.temp_min);
        maxTemp = this.getString(R.string.temp_max);
        locatingUser = this.getString(R.string.locating_user);
        userLocated = this.getString(R.string.location_set);
        presentTemp = this.getString(R.string.present_temp);
        cityAdded = this.getString(R.string.city_added);
        favorite = findViewById(R.id.favoriteButton);
        myToolbar = findViewById(R.id.main_toolbar);
        viewPager = findViewById(R.id.mainViewPager);
        viewPager.setAdapter(new WeatherViewPagerAdapter(getSupportFragmentManager()));
        circleIndicator = findViewById(R.id.circleMain);
        circleIndicator.setViewPager(viewPager);
    }

    private void toastMessage() {
        Toast.makeText(getApplicationContext(), weatherNotFound, Toast.LENGTH_SHORT).show();
        SharedPreference.setPreference("weatherSet", "false");
        favorite.setVisibility(View.INVISIBLE);
    }

    public void setLocation(View view) {
        //favorite.setVisibility(View.INVISIBLE);
        if (!locationSet) {
            requestSingleUpdate(getApplicationContext());
            Toast.makeText(this, locatingUser, Toast.LENGTH_SHORT).show();
            setLocation.setClickable(false);
        }
        if (userLocation != null) {
            try {
                geo = true;
                getWeatherFromLocation(String.valueOf(userLocation.latitude), String.valueOf(userLocation.longitude));
                favorite.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                geo = false;
                e.printStackTrace();
                toastMessage();
            }
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //makes keyboard disappear
            mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public void requestSingleUpdate(final Context context) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                }
                locationManager.requestSingleUpdate(criteria, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        setLocation.setClickable(true);
                        Toast.makeText(getApplicationContext(), userLocated, Toast.LENGTH_SHORT).show();
                        locationSet = true;
                        setLocation.callOnClick();
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
                    setLocation.setClickable(true);
                    Toast.makeText(getApplicationContext(), userLocated, Toast.LENGTH_SHORT).show();
                    locationSet = true;
                    setLocation.callOnClick();
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

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
    }


    public void check(View view) {
        try {
            SharedPreference.setPreference("weatherSet", "true");
            message = "";
            geo = false;
            SharedPreference.city = editText.getText().toString();
            if (RealmUtil.getNumberOfElements(CitySearchDB.class) > 4) {
                DataHelper.deleteCitySearch(realm);
                //RealmUtil.delete(CitySearchDB.class,"citySearch", CitySearchDB.getId());
            }
            getWeatherFromName(getCity());
            for (int i = 6; i <= 38; i += 8) {
                getWeatherFromNameNextDays(getCity(), i);
                Log.i("i", String.valueOf(i));
                Thread.sleep(200);
            }

            SharedPreference.setPreference("city", getCity());
        } catch (Exception e) {
            SharedPreference.setPreference("weatherSet", "false");
            favorite.setVisibility(View.INVISIBLE);
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

    private void addCurrentForecast(String forecast) {
        SecureRandom secureRandom = new SecureRandom();
        int idCurrentForecast = secureRandom.nextInt(10000);
        DataHelper.addCurrentForecast(realm, idCurrentForecast, forecast);
    }

    private void addToFavorite(String city) {
        SecureRandom secureRandom = new SecureRandom();
        int cityID = secureRandom.nextInt(10000);
        DataHelper.newCity(realm, cityID, city);
    }

    public void addCityToFavorite(View view) {
        try {
            if (SharedPreference.getPreference("weatherSet").equals("true")) {
                Log.i("city", getCity());
                addToFavorite(getCity());
                Toast.makeText(this, cityAdded, Toast.LENGTH_SHORT).show();
                favorite.setVisibility(View.INVISIBLE);
            } else {
                Log.i("city", getCity());
                toastMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            toastMessage();
        }
    }

    public void goToFavorite(View view) {
        SharedPreference.setPreference("weatherSet", "false");
        Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
        startActivity(intent);

    }

    public void goToSettings(View view) {
        SharedPreference.setPreference("weatherSet", "false");
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }


    private void getInt() {
        i = getIntent();
        editText.setText("");
        try {
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
                        ArrayList<WeatherToday> weatherList = response.body().getWeather();
                        SharedPreference.city = name;
                        editText.setText(getCity());
                        temperature = weatherResponseToday.getMain().getTemp();
                        pressureCalc = roundToHalf(weatherResponseToday.getMain().getPressure());
                        humidityCalc = roundToHalf(weatherResponseToday.getMain().getHumidity());
                        minTempCalc = roundToHalf(weatherResponseToday.getMain().getTemp_min());
                        maxTempCalc = roundToHalf(weatherResponseToday.getMain().getTemp_max());
                        String t = String.valueOf(Math.round(temperature * 2) / 2.0);
                        SharedPreference.message1 = name + "\r\n" + presentTemp + t + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n"
                                + minTemp + minTempCalc + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n"
                                + maxTemp + maxTempCalc + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n"
                                + weatherList.get(0).getDescription() + "\r\n"
                                + pressure + pressureCalc + " hPa" + "\r\n" + humidity + humidityCalc + " %";
                        SharedPreference.city = editText.getText().toString();
                        addCurrentForecast(message1);

                        if (RealmUtil.getNumberOfElements(CurrentForecast.class) > 1) {
                            DataHelper.deleteCurrentForecast(realm);
                        }
                        timeStamp = DateFormat.format("dd-MM-yyyy", new Date());
                        SharedPreference.setPreference("timestamp", timeStamp.toString());
                        SharedPreference.setPreference("city", getCity());
                        if (RealmUtil.getNumberOfElements(CitySearchDB.class) > 4) {
                            DataHelper.deleteCitySearch(realm);
                        }
                        for (int i = 6; i <= 38; i += 8) {
                            getWeatherFromNameNextDays(SharedPreference.city, i);
                            Log.i("i", String.valueOf(i));
                            Thread.sleep(200);
                        }

                        SharedPreference.setPreference("weatherSet", "true");
                        geo = false;
                        favorite.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        toastMessage();
                        SharedPreference.setPreference("weatherSet", "false");
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


    private void getWeatherFromName(final String cityApi) {

        if (cityApi.equals(null)) {
            toastMessage();
        } else {
            OpenWeatherMap service = ServiceGenerator.createService(OpenWeatherMap.class);
            Call<WeatherResponseToday> call = service.getCurrentWeatherDataFromName(cityApi, SharedPreference.getPreference("language"), SharedPreference.getPreference("units"));
            call.enqueue(new Callback<WeatherResponseToday>() {
                @Override
                public void onResponse(Call<WeatherResponseToday> call, Response<WeatherResponseToday> response) {
                    try {
                        WeatherResponseToday weatherResponseToday = response.body();
                        temperature = weatherResponseToday.getMain().getTemp();
                        pressureCalc = roundToHalf(weatherResponseToday.getMain().getPressure());
                        humidityCalc = roundToHalf(weatherResponseToday.getMain().getHumidity());
                        minTempCalc = roundToHalf(weatherResponseToday.getMain().getTemp_min());
                        maxTempCalc = roundToHalf(weatherResponseToday.getMain().getTemp_max());
                        String t = String.valueOf(Math.round(temperature * 2) / 2.0);
                        ArrayList<WeatherToday> weatherList = response.body().getWeather();
                        SharedPreference.message1 = cityApi + "\r\n" + presentTemp + t + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n"
                                + minTemp + minTempCalc + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n"
                                + maxTemp + maxTempCalc + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n"
                                + weatherList.get(0).getDescription() + "\r\n"
                                + pressure + pressureCalc + " hPa" + "\r\n" + humidity + humidityCalc + " %";
                        SharedPreference.city = editText.getText().toString();
                        SharedPreference.setPreference("weatherSet", "true");
                        addCurrentForecast(message1);
                        if (RealmUtil.getNumberOfElements(CurrentForecast.class) > 1) {
                            DataHelper.deleteCurrentForecast(realm);
                        }

                        timeStamp = DateFormat.format("dd-MM-yyyy", new Date());
                        SharedPreference.setPreference("timestamp", timeStamp.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                        toastMessage();
                        SharedPreference.setPreference("weatherSet", "false");
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


        OpenWeatherMap service = ServiceGenerator.createService(OpenWeatherMap.class);
        Call<WeatherResponseNextDays> call = service.getCurrentDataFromNameNextDays(city, SharedPreference.getPreference("language"), SharedPreference.getPreference("units"));
        call.enqueue(new Callback<WeatherResponseNextDays>() {

            @Override
            public void onResponse(Call<WeatherResponseNextDays> call, Response<WeatherResponseNextDays> response) {

                try {
                    WeatherResponseNextDays weatherResponseNextDays = response.body();
                    ArrayList<List> list = response.body().getList();
                    double temperature = roundToHalf(weatherResponseNextDays.getList().get(i).getMain().getTemp());
                    CharSequence time = DateFormat.format("EEEE", ((list.get(i).getDt())) * 1000);
                    message = time + "\r\n" + temperature + (char) 0x00B0 + SharedPreference.getPreference("temperature") + "\r\n" + list.get(i).getWeather().get(0).getDescription();
                    onAddCitySearch(message);
                    if (RealmUtil.getNumberOfElements(CitySearchDB.class) == 5) {
                        WeatherListFragment.addToList();
                        favorite.setVisibility(View.VISIBLE);
                        recreate();
                    }
                    SharedPreference.setPreference("weatherSet", "true");
                } catch (Exception e) {
                    e.printStackTrace();
                    toastMessage();
                    SharedPreference.setPreference("weatherSet", "false");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponseNextDays> call, Throwable t) {
                Log.i("nice", t.getMessage());
            }
        });
    }

    private double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
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
        SharedPreference.loadPreferences(getBaseContext());
        setLangButton();
        setTempButton();
        setContentView(R.layout.activity_main);
        requestPermission();
        findViews();
        getInt();
        myToolbar.setTitle(appName);
        setSupportActionBar(myToolbar);
        check.setText(checkWeather_s);
        editText.setHint(enterCity);
        timeStart = DateFormat.format("dd-MM-yyyy", new Date());

        if (editText.getText().toString().equals("") && RealmUtil.getNumberOfElements(CurrentForecast.class) != 0 && timeStart.equals(SharedPreference.getPreference("timestamp"))) {
            SharedPreference.message1 = RealmUtil.getCitySearch();
        }
        editText.setText(SharedPreference.getPreference("city"));
        //favorite.setVisibility(View.VISIBLE);
        if (!editText.getText().toString().equals("") && SharedPreference.move) {
            check(view);
            SharedPreference.setPreference("weatherSet", "false");
            SharedPreference.move = false;

        }
        try {
            if (SharedPreference.getPreference("weatherSet").equals("true")) {
                favorite.setVisibility(View.VISIBLE);
            } else {
                favorite.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
