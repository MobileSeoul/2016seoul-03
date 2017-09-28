package com.example.changk.urionnuri;

import android.app.Application;

import com.example.changk.urionnuri.onnuridb.OnnuriDB;
import com.example.changk.urionnuri.onnurigoogleapi.GoogleApiHelper;
import com.tsengvn.typekit.Typekit;

/**
 * Created by hoc on 2016-10-21.
 */

public class App extends Application {
    private GoogleApiHelper googleApiHelper;
    private OnnuriDB onnuriDB;
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        googleApiHelper = new GoogleApiHelper(this);
        onnuriDB = new OnnuriDB(getApplicationContext(), "Onnuri.db", null, 1);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "BMJUA.ttf"))
                .addBold(Typekit.createFromAsset(this, "BMJUA.ttf"));
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance() {
        return this.googleApiHelper;
    }

    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }

    public OnnuriDB getOnnuriDBInstance() {
        return this.onnuriDB;
    }

    public static OnnuriDB getOnnuriDB() {
        return getInstance().getOnnuriDBInstance();
    }
}
