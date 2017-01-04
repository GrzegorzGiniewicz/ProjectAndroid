package com.example.grzegorz.myapplication;

import android.app.Application;

import com.example.grzegorz.myapplication.model.Xyz;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Grzegorz on 2017-01-04.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }
}
