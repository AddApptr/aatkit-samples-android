package com.aatkit.infeedbannerusinginfeedapi;

import android.app.Application;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.BannerConfiguration;
import com.intentsoftware.addapptr.InfeedBannerPlacement;

public class InFeedApplication extends Application {

    private InfeedBannerPlacement inFeedBannerPlacement;

    @Override
    public void onCreate() {
        super.onCreate();

        AATKitConfiguration configuration = new AATKitConfiguration(this);
        configuration.setUseDebugShake(false);
        configuration.setTestModeAccountId(74);

        AATKit.init(configuration);
        AATKit.enableDebugScreen();

        BannerConfiguration bannerConfiguration = new BannerConfiguration();
        inFeedBannerPlacement = AATKit.createInfeedBannerPlacement("InFeedBanner", bannerConfiguration);
    }

    public InfeedBannerPlacement getInFeedBannerPlacement() {
        return inFeedBannerPlacement;
    }
}
