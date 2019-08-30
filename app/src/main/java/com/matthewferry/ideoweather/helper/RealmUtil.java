package com.matthewferry.ideoweather.helper;

import android.util.Log;

import com.matthewferry.ideoweather.realm.CitySearchDB;
import com.matthewferry.ideoweather.realm.CurrentForecast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmUtil {

    public static void insertOrUpdate(final RealmObject object) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(object);
            }
        });
        realm.close();
    }

    public static void delete(Class<? extends RealmObject> clazz, String columnName, int columnValue) {
        Realm realm = Realm.getDefaultInstance();
        final RealmObject object = realm.where(clazz).equalTo(columnName, columnValue).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                object.deleteFromRealm();
            }
        });
        realm.close();
    }

    public static long getNumberOfElements(Class<? extends RealmObject> clazz) {
        Realm realm = Realm.getDefaultInstance();
        try {
            final long object = realm.where(clazz).count();
            Log.i("number", String.valueOf(object));
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            realm.close();
        }
    }

    public static String getCitySearch() {
        Realm realm = Realm.getDefaultInstance();
        try {
            String lastCity = realm.where(CurrentForecast.class).findAll().last().getForecast();
            return lastCity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            realm.close();
        }
    }
}
