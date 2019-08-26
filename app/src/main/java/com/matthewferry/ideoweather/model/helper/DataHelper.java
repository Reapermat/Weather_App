package com.matthewferry.ideoweather.model.helper;

import android.util.Log;
import com.matthewferry.ideoweather.model.realm.CityDB;
import com.matthewferry.ideoweather.model.realm.CitySearchDB;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DataHelper {

    public static void newCity(Realm realm, int cityID, String city){
        RealmQuery<CityDB> result = realm.where(CityDB.class).equalTo("city", city);
        if(result.count() == 0) {
            realm.beginTransaction();
            CityDB cityDB = realm.createObject(CityDB.class, cityID);
            cityDB.setCity(city);
            realm.commitTransaction();
        }

    }

    public static void deleteCity(Realm realm, final long id){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CityDB cityDBItem = realm.where(CityDB.class).equalTo("id", id).findFirst();
                if(cityDBItem != null){
                    cityDBItem.deleteFromRealm();
                }

            }
        });
    }

    public static void newCitySearch(Realm realm, int cityID, String city){
        RealmQuery<CitySearchDB> result = realm.where(CitySearchDB.class).equalTo("citySearch", city);
        if(result.count() == 0) {
            realm.beginTransaction();
            CitySearchDB citySearchDB = realm.createObject(CitySearchDB.class, cityID);
            citySearchDB.setCity(city);
            realm.commitTransaction();
        }
        Log.i("result", result.toString());
    }
    public static void deleteCitySearch(Realm realm){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                    RealmResults citySearchDB = realm.where(CitySearchDB.class).findAll();
                    citySearchDB.deleteAllFromRealm();
            }
        });
    }


}
