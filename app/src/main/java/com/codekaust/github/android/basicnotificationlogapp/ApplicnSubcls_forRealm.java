package com.codekaust.github.android.basicnotificationlogapp;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ktubuntu on 4/2/18.
 */

public class ApplicnSubcls_forRealm extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);

    }
}
