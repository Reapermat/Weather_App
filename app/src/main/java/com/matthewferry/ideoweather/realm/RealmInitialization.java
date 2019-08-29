package com.matthewferry.ideoweather.realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmInitialization extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        realmConfig();
    }

    private void realmConfig() {
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
