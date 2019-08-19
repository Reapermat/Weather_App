package com.matthewferry.ideoweather.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.model.activity.ViewPagerActivity;

public class MyPagerAdapter extends PagerAdapter {

    private Context context;
    ViewPagerActivity viewPagerActivity;
//    ArrayList<String> list = viewPagerActivity.getList();


    public MyPagerAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_view_pager, null);
        TextView textView = view.findViewById(R.id.firstDay);
        //textView.setText(list.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }





}
