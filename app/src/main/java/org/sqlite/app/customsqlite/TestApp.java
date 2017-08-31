package org.sqlite.app.customsqlite;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by Igor Havrylyuk on 28.08.2017.
 */

public class TestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TestApp","onCreate()");
    }

    @Override
    protected void attachBaseContext(Context base) {
        try {
            System.loadLibrary("sqliteX"); // loads the custom sqlite library
            //System.loadLibrary("sqlitefunctions"); // loads the functions library
        } catch (NullPointerException | UnsatisfiedLinkError ignored) {
        }
        super.attachBaseContext(base);
    }
}
