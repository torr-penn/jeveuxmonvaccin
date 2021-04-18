package com.gtasoft.jeveuxmonvaccin;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Launches the Android application.
 */
public class AndroidLauncher extends AndroidApplication {

    private static JeVeuxMonVaccin app;

    public static JeVeuxMonVaccin getApp() {
        return app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        app = new JeVeuxMonVaccin(new AndroidPlatform(this));
        initialize(app, configuration);
    }
}