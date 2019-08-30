package com.matthewferry.ideoweather.helper;

import com.matthewferry.ideoweather.realm.CityDB;
import com.matthewferry.ideoweather.realm.CitySearchDB;
import com.matthewferry.ideoweather.realm.CurrentForecast;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DataHelper {

    public static void newCity(Realm realm, int cityID, String city) {
        realm = Realm.getDefaultInstance();
        try {
            RealmQuery<CityDB> result = realm.where(CityDB.class).equalTo("city", city);
            if (result.count() == 0) {
                realm.beginTransaction();
                CityDB cityDB = realm.createObject(CityDB.class, cityID);
                cityDB.setCity(city);
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }

    }

    public static void deleteCity(Realm realm, final long id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CityDB cityDBItem = realm.where(CityDB.class).equalTo("id", id).findFirst();
                if (cityDBItem != null) {
                    cityDBItem.deleteFromRealm();
                }

            }
        });
    }

    public static void newCitySearch(Realm realm, int cityID, String city) {
        realm = Realm.getDefaultInstance();
        try {
            RealmQuery<CitySearchDB> result = realm.where(CitySearchDB.class).equalTo("citySearch", city);
            if (result.count() == 0) {
                realm.beginTransaction();
                CitySearchDB citySearchDB = realm.createObject(CitySearchDB.class, cityID);
                citySearchDB.setCity(city);
                realm.commitTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
    }

    public static void deleteCitySearch(Realm realm) {
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults citySearchDB = realm.where(CitySearchDB.class).findAll();
                    citySearchDB.deleteAllFromRealm();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            realm.close();
        }
    }


    public static void addCurrentForecast(Realm realm, int currentForecastID, String forecast) {
        realm = Realm.getDefaultInstance();
        try {
            RealmQuery<CurrentForecast> result = realm.where(CurrentForecast.class).equalTo("idCurrentForecast", currentForecastID);
            if (result.count() == 0) {
                realm.beginTransaction();
                CurrentForecast currentForecast = realm.createObject(CurrentForecast.class, currentForecastID);
                currentForecast.setForecast(forecast);
                realm.commitTransaction();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            realm.close();
        }

    }

    public static void deleteCurrentForecast(Realm realm) {
        realm = Realm.getDefaultInstance();
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    CurrentForecast currentForecast = realm.where(CurrentForecast.class).findFirst();
                    if (currentForecast != null) {
                        currentForecast.deleteFromRealm();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            realm.close();
        }
    }


}
