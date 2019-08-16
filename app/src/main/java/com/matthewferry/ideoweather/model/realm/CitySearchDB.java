package com.matthewferry.ideoweather.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CitySearchDB extends RealmObject {

    @PrimaryKey
    private int idSearch;

    private String citySearch;

    public CitySearchDB(){

    }

    public int getId(){
        return idSearch;
    }

    public void setId(){
        this.idSearch = idSearch;
    }

    public String getCity(){
        return citySearch;
    }

    public void setCity(String city){
        this.citySearch = city;
    }
}
