package com.matthewferry.ideoweather.model.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.matthewferry.ideoweather.view.TextViewFragment;
import com.matthewferry.ideoweather.view.WeatherListFragment;

public class WeatherViewPagerAdapter extends FragmentPagerAdapter {

    Context context;

    public WeatherViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new TextViewFragment();
            case 1:
                return new WeatherListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
