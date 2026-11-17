package com.aatkit.fullscreenusingautoreloading;

import android.app.Application;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.FullscreenPlacement;

public class MyApplication extends Application {

    private FullscreenPlacement fullscreenPlacement = null;

    @Override
    public void onCreate() {
        super.onCreate();

        AATKitConfiguration configuration = new AATKitConfiguration(this);
        configuration.setUseDebugShake(true);
        configuration.setTestModeAccountId(74);

        AATKit.init(configuration);
        AATKit.enableDebugScreen();

        fullscreenPlacement = AATKit.createFullscreenPlacement("Fullscreen");
    }

    public FullscreenPlacement getFullscreenPlacement() {
        return fullscreenPlacement;
    }
}
