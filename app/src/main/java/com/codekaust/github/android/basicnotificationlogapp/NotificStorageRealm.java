package com.codekaust.github.android.basicnotificationlogapp;

import io.realm.RealmObject;

public class NotificStorageRealm extends RealmObject {

    String packageName,text,subText,title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    @Override
    public String toString() {
        return "PackageName:-  "+packageName+"\nTitle:-  "+title+"\nText:-  "+text+"\nSubText:-  "+subText;
    }
}
