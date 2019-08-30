package com.matthewferry.ideoweather.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.realm.CurrentForecast;

import io.realm.Realm;

public class TextViewFragment extends Fragment {
    TextView textView;
    Realm realm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_text_view, container, false);
        textView = view.findViewById(R.id.weatherTextView);
        realm = Realm.getDefaultInstance();
        try {
            textView.setText(realm.where(CurrentForecast.class).findAll().first().getForecast());
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
