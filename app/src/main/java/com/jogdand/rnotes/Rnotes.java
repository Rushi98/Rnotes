package com.jogdand.rnotes;

import android.app.Application;

import io.realm.Realm;

/**
 * @author Rushikesh Jogdand.
 */

public class Rnotes extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
