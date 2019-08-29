package com.matthewferry.ideoweather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.adapter.RecyclerViewAdapter;
import com.matthewferry.ideoweather.helper.DataHelper;
import com.matthewferry.ideoweather.helper.SharedPreference;
import com.matthewferry.ideoweather.realm.CityDB;
import com.matthewferry.ideoweather.view.NewCityDialog;
import com.matthewferry.ideoweather.view.RecyclerItemClickListener;

import java.security.SecureRandom;

import io.realm.Realm;

public class FavoriteActivity extends AppCompatActivity/* implements NewCityDialog.NewCityListener */{

    private Realm realm;
    private RecyclerView recyclerView;
    //private FloatingActionButton floatingActionButton;
    public TextView cities;
    public EditText dialogCity;
    private String lang;
    private String backTo;
    private String canceled;
    private String swipeToDelete;
    private String fav;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreference.loadPreferences(getApplicationContext());
        setContentView(R.layout.activity_favorite);
        realm = Realm.getDefaultInstance();
        findViews();
        final DialogFragment addCityDialog = new NewCityDialog();
        SetUpRecyclerView();
        toolbar.setTitle(fav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreference.move = true;

        /*floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCityDialog.show(getSupportFragmentManager(), "New City");

            }
        });*/

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String city = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.cities)).getText().toString();
                        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
                        SharedPreference.setPreference("city", city);
                        startActivity(intent);
                        // MainActivity.onAddCitySearch(city);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(FavoriteActivity.this, swipeToDelete, Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }



    /*@Override
    public void onCancel(DialogFragment dialogFragment) {

        Toast.makeText(this, canceled, Toast.LENGTH_SHORT).show();

    }*/

    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {

        TouchHelperCallback() {
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

    private void SetUpRecyclerView() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(realm.where(CityDB.class).findAll());

        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void findViews() {
        recyclerView = findViewById(R.id.myRecyclerView);
        cities = findViewById(R.id.cities);
        //floatingActionButton = findViewById(R.id.floatingActionButton);
        dialogCity = findViewById(R.id.dialogCity);
        backTo = getString(R.string.back_to_main);
        canceled = getString(R.string.canceled);
        swipeToDelete = getString(R.string.swipe_to_delete);
        backTo = getString(R.string.back_to_main);
        fav = getString(R.string.favorite);
        toolbar = findViewById(R.id.my_toolbar);
    }

}



