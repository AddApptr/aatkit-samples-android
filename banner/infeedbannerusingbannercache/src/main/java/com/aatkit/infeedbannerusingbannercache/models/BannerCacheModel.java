package com.aatkit.infeedbannerusingbannercache.models;

import com.intentsoftware.addapptr.BannerCache;
import com.intentsoftware.addapptr.BannerPlacementLayout;

public class BannerCacheModel {

    private BannerPlacementLayout bannerPlacementLayout;
    private final BannerCache bannerCache;

    public BannerCacheModel(BannerCache bannerCache) {

        this.bannerCache = bannerCache;
    }

    public BannerPlacementLayout getBannerPlacementLayout() {

        if (bannerPlacementLayout == null) {
            bannerPlacementLayout = bannerCache.consume();
        }
        return bannerPlacementLayout;
    }

    public void removeAd() {

        if (bannerPlacementLayout != null) {
            bannerPlacementLayout.destroy(); //destroy the loaded banner
            bannerPlacementLayout = null;
        }
    }
}
