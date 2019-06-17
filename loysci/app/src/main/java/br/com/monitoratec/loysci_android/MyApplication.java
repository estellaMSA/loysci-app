package br.com.monitoratec.loysci_android;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.core.Twitter;

import br.com.monitoratec.loysci_android.util.Prefs;
import io.fabric.sdk.android.Fabric;



/**
 * Created by alejandra on 19/6/17.
 */

public final class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        Prefs.init(this);

        Twitter.initialize(this);

    }
}
