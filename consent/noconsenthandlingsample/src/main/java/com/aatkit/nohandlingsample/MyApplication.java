package com.aatkit.nohandlingsample;

import android.app.Application;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.BannerPlacementSize;
import com.intentsoftware.addapptr.StickyBannerPlacement;

public class MyApplication extends Application {

    private StickyBannerPlacement bannerPlacement = null;

    @Override
    public void onCreate() {
        super.onCreate();

        AATKitConfiguration configuration = new AATKitConfiguration(this);
        configuration.setTestModeAccountId(74); //TODO: remember to remove it before publishing the app!
        AATKit.init(configuration);
        AATKit.enableDebugScreen();

        bannerPlacement = AATKit.createStickyBannerPlacement("Banner", BannerPlacementSize.Banner320x53);
    }

    public StickyBannerPlacement getBannerPlacement() {
        return bannerPlacement;
    }
}
