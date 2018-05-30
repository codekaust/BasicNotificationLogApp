package com.codekaust.github.android.basicnotificationlogapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;
import android.os.Bundle;
import android.graphics.Bitmap;

import io.realm.Realm;
import io.realm.RealmResults;

public class NotificationListener_Service extends NotificationListenerService {

    Realm realm;

    @Override
    public IBinder onBind(Intent intent) {

        return super.onBind(intent);
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        realm=Realm.getDefaultInstance();

        Bundle extras=sbn.getNotification().extras;

        String notificationTitle = extras.getString(android.app.Notification.EXTRA_TITLE);
//        int notificationIcon = extras.getInt(android.app.Notification.EXTRA_SMALL_ICON);
//        Bitmap notificationLargeIcon =
//                     ((android.graphics.Bitmap) extras.getParcelable(android.app.Notification.EXTRA_LARGE_ICON));
        String notificationText = null;
        if(extras.getCharSequence(android.app.Notification.EXTRA_TEXT)!=null){
            notificationText=extras.getCharSequence(android.app.Notification.EXTRA_TEXT).toString();
        }
        String notificationSubText = null;
        if(extras.getCharSequence(android.app.Notification.EXTRA_SUB_TEXT)!=null){
            notificationSubText=extras.getCharSequence(android.app.Notification.EXTRA_SUB_TEXT).toString();
        }
        String package_name = sbn.getPackageName();

        updateDB(package_name,notificationTitle,notificationText,notificationSubText);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Toast.makeText(this, "RemovedNotif _ BasicNotifAPP", Toast.LENGTH_SHORT).show();
    }

    public void updateDB(final String pack_name, final String title, final String text, final String subText){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                NotificStorageRealm notificStorageRealm = bgRealm.createObject(NotificStorageRealm.class);
                notificStorageRealm.setPackageName(pack_name);
                notificStorageRealm.setTitle(title);
                notificStorageRealm.setText(text);
                notificStorageRealm.setSubText(subText);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v("NotifService","DB_Inserted_Success");

                Toast.makeText(NotificationListener_Service.this, "Written in DB", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.v("NotifService","DB_Inserted_FAILED");
            }
        });

    }
}
