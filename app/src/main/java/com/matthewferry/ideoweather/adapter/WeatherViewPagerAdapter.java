package com.matthewferry.ideoweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.matthewferry.ideoweather.view.TextViewFragment;
import com.matthewferry.ideoweather.view.WeatherListFragment;

public class WeatherViewPagerAdapter extends FragmentPagerAdapter {

    public WeatherViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
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

}
