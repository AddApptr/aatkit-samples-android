package com.aatkit.infeedbannersusingstaticapi.models;

import android.view.View;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.StickyBannerPlacement;

public class BannerModel {

    private final StickyBannerPlacement placement;

    public BannerModel(StickyBannerPlacement placement) {
        this.placement = placement;
    }

    public View getBannerPlacementView() {
        return placement.getPlacementView();
    }
}
