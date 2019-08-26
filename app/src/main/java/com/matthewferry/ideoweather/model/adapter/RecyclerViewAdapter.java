package com.matthewferry.ideoweather.model.adapter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.matthewferry.ideoweather.R;
import com.matthewferry.ideoweather.model.realm.CityDB;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<CityDB, RecyclerViewAdapter.MyViewHolder>  {


    public RecyclerViewAdapter(@Nullable OrderedRealmCollection<CityDB> data) {
        super(data, true);

        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder myViewHolder, int i) {

        final CityDB cityDB = getItem(i);
        myViewHolder.txtCity.setText(cityDB.getCity());

    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtCity;
        MyViewHolder(View view){
            super(view);
            txtCity = view.findViewById(R.id.cities);
        }
    }


}
