package com.matthewferry.ideoweather.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.model.util.GetWeatherForecast;
import java.util.ArrayList;

public class RecyclerViewWeatherAdapter extends RecyclerView.Adapter<RecyclerViewWeatherAdapter.ViewHolder> {


    private ItemClickListener mClickListener;
    Context context;
    ArrayList<GetWeatherForecast> getWeatherForecasts;

    public RecyclerViewWeatherAdapter(Context context, ArrayList<GetWeatherForecast> getWeatherForecasts) {
        this.context = context;
        this.getWeatherForecasts = getWeatherForecasts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.weather_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewWeatherAdapter.ViewHolder viewHolder, int i) {
        viewHolder.myTextView.setText(getWeatherForecasts.get(i).getMessage());
    }

    @Override
    public int getItemCount() {
        return getWeatherForecasts.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.weatherRow);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null){
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public String getItem(int id){
        return getWeatherForecasts.get(id).getMessage();
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}