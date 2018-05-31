package com.codekaust.github.android.basicnotificationlogapp;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    private Button createNotif, refreshList;

    public static boolean isNotificationAccessEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listNotifs = new ArrayList<NotificStorageRealm>();

        realm = Realm.getDefaultInstance();

        if (!isNotificationAccessEnabled) {
            Toast.makeText(this, "Please give Notification Access Permission to \nNotificationListener_Service\n if not given...", Toast.LENGTH_LONG).show();
            startActivity(new android.content.Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }

        listView = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<NotificStorageRealm>(this,
                android.R.layout.simple_list_item_1,
                listNotifs);

        listView.setAdapter(arrayAdapter);

        createNotif = (Button) findViewById(R.id.btn_createNotif);
        createNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification(getApplicationContext());
            }
        });

        refreshList = (Button) findViewById(R.id.btn_refreshList);
        refreshList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateArrayList();
            }
        });
    }

    public void updateArrayList() {
        RealmResults<NotificStorageRealm> RRobjectNotificStorageRealms = realm.where(NotificStorageRealm.class).findAll();

        realm.beginTransaction();
        for (NotificStorageRealm notificStorageRealm : RRobjectNotificStorageRealms) {
            listNotifs.add(notificStorageRealm);
        }
        realm.commitTransaction();

        arrayAdapter.notifyDataSetChanged();
    }

    private void createNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("My Notification");
        builder.setContentText("Notification created.");
        builder.setTicker("Notification created.");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
