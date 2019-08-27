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
import com.matthewferry.ideoweather.helper.SharedPreference;

public class TextViewFragment extends Fragment {
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_text_view, container, false);
        textView = view.findViewById(R.id.weatherTextView);
        textView.setText(SharedPreference.message1);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
