package com.aatkit.simpleconsentsample;

import android.app.Application;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.BannerPlacementSize;
import com.intentsoftware.addapptr.NonIABConsent;
import com.intentsoftware.addapptr.SimpleConsent;
import com.intentsoftware.addapptr.StickyBannerPlacement;

public class MyApplication extends Application {

    private StickyBannerPlacement bannerPlacement = null;

    @Override
    public void onCreate() {
        super.onCreate();

        AATKitConfiguration configuration = new AATKitConfiguration(this);
        configuration.setTestModeAccountId(74); //TODO: remember to remove it before publishing the app!

        configuration.setConsent(new SimpleConsent(NonIABConsent.UNKNOWN)); // The value of SimpleConsent is passed to non-IAB partners. AATKit and other IAB vendors will read needed consent string from SharedPreferences.

        // Google CMP needs an activity instance, so we cannot simply create it here
        AATKit.init(configuration);
        AATKit.enableDebugScreen();

        bannerPlacement = AATKit.createStickyBannerPlacement("Banner", BannerPlacementSize.Banner320x53);
    }

    public StickyBannerPlacement getBannerPlacement() {
        return bannerPlacement;
    }

}
