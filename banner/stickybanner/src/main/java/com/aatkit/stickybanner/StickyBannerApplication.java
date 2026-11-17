package com.aatkit.stickybanner;

import android.app.Application;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.BannerPlacementSize;
import com.intentsoftware.addapptr.StickyBannerPlacement;

public class StickyBannerApplication extends Application {

    private StickyBannerPlacement bannerPlacement = null;

    @Override
    public void onCreate() {
        super.onCreate();

        AATKitConfiguration configuration = new AATKitConfiguration(this);
        configuration.setUseDebugShake(false);
        configuration.setTestModeAccountId(74);

        AATKit.init(configuration);
        AATKit.enableDebugScreen();

        bannerPlacement = AATKit.createStickyBannerPlacement("StickyBanner", BannerPlacementSize.Banner320x53);
    }

    public StickyBannerPlacement getBannerPlacement() {
        return bannerPlacement;
    }
}
