package com.aatkit.infeedbannerusinginfeedapi.models;

import android.view.View;
import android.view.ViewGroup;

import com.intentsoftware.addapptr.BannerRequest;
import com.intentsoftware.addapptr.BannerRequestCompletionListener;
import com.intentsoftware.addapptr.InfeedBannerPlacement;
import com.aatkit.infeedbannerusinginfeedapi.AdLoadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BannerModel {

    private View bannerPlacementLayout;

    private final int bannerPosition;
    private final InfeedBannerPlacement bannerPlacement;
    private final AdLoadListener adLoadListener;
    private BannerRequest bannerRequest;

    public BannerModel(AdLoadListener adLoadListener, int bannerPosition, InfeedBannerPlacement bannerPlacement) {
        this.adLoadListener = adLoadListener;
        this.bannerPosition = bannerPosition;
        this.bannerPlacement = bannerPlacement;
    }

    public View getBannerPlacementLayout() {
        return bannerPlacementLayout;
    }

    public void reloadBanner() {

        bannerRequest = new BannerRequest(null);
        Map<String, List<String>> targetingInformation = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(bannerPosition));
        targetingInformation.put("position", list);
        bannerRequest.setTargetingInformation(targetingInformation);
        bannerPlacement.requestAd(bannerRequest, createBannerRequestCompletionListener());

    }

    public boolean canReload() {
        return (bannerPlacementLayout == null && bannerRequest == null);
    }

    public void removeAd() {

        if (bannerRequest != null)
            bannerPlacement.cancel(bannerRequest);

        bannerRequest = null;

        if (bannerPlacementLayout != null) {

            if (bannerPlacementLayout.getParent() != null) {
                ViewGroup parent = (ViewGroup) bannerPlacementLayout.getParent();
                parent.removeView(bannerPlacementLayout);
            }
            bannerPlacementLayout = null;
        }
    }

    private BannerRequestCompletionListener createBannerRequestCompletionListener() {
        return (layout, error) -> {
            bannerPlacementLayout = layout;
            adLoadListener.onRequestCompleted(bannerPosition);
            if (error != null) {
                bannerRequest = null;
            }
        };
    }
}
