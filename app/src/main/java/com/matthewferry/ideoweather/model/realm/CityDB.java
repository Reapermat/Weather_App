package com.matthewferry.ideoweather.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CityDB extends RealmObject {

    @PrimaryKey
    private int id;

    private String city;

    public CityDB(){

    }

    public int getId(){
        return id;
    }

    public void setId(){
        this.id = id;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

}
