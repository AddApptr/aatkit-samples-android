package com.addapptr.nativeadssample;

import com.intentsoftware.addapptr.NativeAdPlacement;
import com.intentsoftware.addapptr.ad.NativeAdData;

public class AdModel {
    private NativeAdData nativeAd;
    private boolean reportedAdSpace;
    private final NativeAdPlacement nativeAdPlacement;

    public AdModel(NativeAdPlacement nativeAdPlacement) {
        this.nativeAdPlacement = nativeAdPlacement;
    }

    public void setNativeAd(NativeAdData nativeAd) {
        this.nativeAd = nativeAd;
    }

    public NativeAdData getNativeAd() {
        return nativeAd;
    }

    public void reportAdSpace() {
        if (!reportedAdSpace) { //report adspace only once for one ad
            reportedAdSpace = true;
            nativeAdPlacement.countAdSpace();
        }
    }

    // Clear resources for no longer needed ads
    public void destroy() {
        if (nativeAd != null) {
            nativeAd.detachFromLayout();
        }
        nativeAd = null;
        reportedAdSpace = false;
    }
}
