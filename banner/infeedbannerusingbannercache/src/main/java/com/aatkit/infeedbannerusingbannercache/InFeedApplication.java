package com.aatkit.infeedbannerusingbannercache;

import android.app.Application;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.BannerCache;
import com.intentsoftware.addapptr.BannerCacheConfiguration;

public class InFeedApplication extends Application {

    private BannerCache bannerCache;

    @Override
    public void onCreate() {
        super.onCreate();

        AATKitConfiguration configuration = new AATKitConfiguration(this);
        configuration.setUseDebugShake(false);
        configuration.setTestModeAccountId(74);

        AATKit.init(configuration);
        AATKit.enableDebugScreen();

        BannerCacheConfiguration cacheConfiguration = new BannerCacheConfiguration("bannerPlacementForCache", 2);
        cacheConfiguration.setShouldCacheAdditionalAdAtStart(true);

        bannerCache = AATKit.createBannerCache(cacheConfiguration);
    }

    public BannerCache getBannerCache() {
        return bannerCache;
    }
}
