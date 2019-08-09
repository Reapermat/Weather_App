package com.matthewferry.ideoweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;

import java.security.SecureRandom;
import java.util.Locale;

import io.realm.Realm;

public class Favorite extends AppCompatActivity implements NewCityDialog.NewCityListener {

    Button backToMain;
    Realm realm;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    TextView cities;
    EditText dialogCity;
    Button english;
    Button polish;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String lang;
    String backTo;
    String canceled;
    String swipeToDelete;
    boolean ready = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Realm.init(this);
        loadPreferences();
        setContentView(R.layout.activity_favorite);
        realm = Realm.getDefaultInstance();
        recyclerView = findViewById(R.id.myRecyclerView);
        backToMain = findViewById(R.id.back);
        cities = findViewById(R.id.cities);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        dialogCity = findViewById(R.id.dialogCity);
        english = findViewById(R.id.englishButton);
        polish = findViewById(R.id.polishButton);
        backTo = getString(R.string.back_to_main);
        canceled = getString(R.string.canceled);
        swipeToDelete = getString(R.string.swipe_to_delete);
        backTo = getString(R.string.back_to_main);
        pref = getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        final DialogFragment addCityDialog = new NewCityDialog();
        SetUpRecyclerView();
        backToMain.setText(backTo);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCityDialog.show(getSupportFragmentManager(), "New City");

            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String city = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.cities)).getText().toString();
                        Intent intentText = new Intent(Favorite.this, MainActivity.class);
                        intentText.putExtra("City", city);
                        startActivity(intentText);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Toast.makeText(Favorite.this, swipeToDelete, 10).show();
                    }
                })
        );


    }

    public void backToMain(View view){
        ready=false;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onAddCity(String city) {
        SecureRandom secureRandom = new SecureRandom();
        int cityID = secureRandom.nextInt(10000);
        DataHelper.newCity(realm, cityID, city);

    }

    @Override
    public void onCancel(DialogFragment dialogFragment) {

        Toast.makeText(this, canceled, 10).show();

    }

    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback{


        TouchHelperCallback(){
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            DataHelper.deleteCity(realm, viewHolder.getItemId());
        }
    }

    public void SetUpRecyclerView(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(realm.where(CityDB.class).findAll());

        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }catch(Exception e){
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

    private void loadPreferences(){
        try {
            pref = PreferenceManager.getDefaultSharedPreferences(this);
            lang = pref.getString("language", null);
            Log.i("language", lang);
            //LocaleHelper.setLocale(this, lang);
            setLocal(lang);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    }



