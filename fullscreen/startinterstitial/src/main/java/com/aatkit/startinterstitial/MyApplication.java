package com.aatkit.startinterstitial;

import android.app.Application;

import androidx.annotation.NonNull;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.FullscreenPlacement;
import com.intentsoftware.addapptr.FullscreenPlacementListener;
import com.intentsoftware.addapptr.Placement;

public class MyApplication extends Application implements AATKit.Delegate {

    private FullscreenPlacement fullscreenPlacement = null;
    private AATKitEventListener aatKitEventListener;

    @Override
    public void onCreate() {
        super.onCreate();

        AATKitConfiguration configuration = new AATKitConfiguration(this);
        configuration.setUseDebugShake(true);
        configuration.setDelegate(this);
        configuration.setTestModeAccountId(74);

        AATKit.init(configuration);
        AATKit.enableDebugScreen();

        fullscreenPlacement = AATKit.createFullscreenPlacement("Fullscreen");
        if (fullscreenPlacement != null)
            fullscreenPlacement.setListener(new FullscreenPlacementListener() {
                @Override
                public void onNoAd(@NonNull Placement placement) {

                }

                @Override
                public void onHaveAd(@NonNull Placement placement) {

                }

                @Override
                public void onResumeAfterAd(@NonNull Placement placement) {
                    aatKitEventListener.onResumeAfterAd();
                }

                @Override
                public void onPauseForAd(@NonNull Placement placement) {

                }
            });
    }

    public FullscreenPlacement getFullscreenPlacement() {
        return fullscreenPlacement;
    }

    public void setListener(AATKitEventListener aatKitEventListener) {
        this.aatKitEventListener = aatKitEventListener;
    }

    @Override
    public void aatkitObtainedAdRules(boolean fromTheServer) {

    }

    @Override
    public void aatkitUnknownBundleId() {

    }
}
