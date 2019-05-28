package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.util.Prefs;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {

                if (!Prefs.isLoggedIn()) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                    return;
                }
/*
                if(!Prefs.isSliderHome()){
                    startActivity(new Intent(SplashActivity.this, SliderHome.class));
                    finish();
                    return;
                }
*/
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
