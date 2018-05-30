package com.codekaust.github.android.basicnotificationlogapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    public Realm realm;
    public List<NotificStorageRealm> listNotifs;
    private ListView listView;
    private ArrayAdapter<NotificStorageRealm> arrayAdapter;

    public static Activity mainActy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listNotifs=new ArrayList<NotificStorageRealm>();

        realm = Realm.getDefaultInstance();

        Toast.makeText(this, "Please give Notification Access Permission to \nNotificationListener_Service\n if not given...", Toast.LENGTH_LONG).show();

        startActivity(new android.content.Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));

        updateArrayList();

        listView=(ListView)findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<NotificStorageRealm>( this,
                android.R.layout.simple_list_item_1,
                listNotifs);

        listView.setAdapter(arrayAdapter);
    }

    public void updateArrayList(){

        RealmResults<NotificStorageRealm> RRobjectNotificStorageRealms = realm.where(NotificStorageRealm.class).findAll();

        realm.beginTransaction();
        for (NotificStorageRealm notificStorageRealm : RRobjectNotificStorageRealms){
            listNotifs.add(notificStorageRealm);
        }
        realm.commitTransaction();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
