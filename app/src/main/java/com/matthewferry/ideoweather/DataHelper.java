package com.matthewferry.ideoweather;


import io.realm.Realm;
import io.realm.RealmQuery;

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
}
