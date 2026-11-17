package com.aatkit.vendorconsentsample;

import android.app.Application;
import android.preference.PreferenceManager;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.AdNetwork;
import com.intentsoftware.addapptr.BannerPlacementSize;
import com.intentsoftware.addapptr.NonIABConsent;
import com.intentsoftware.addapptr.StickyBannerPlacement;
import com.intentsoftware.addapptr.VendorConsent;

import java.util.Arrays;
import java.util.List;

public class MyApplication extends Application implements VendorConsent.VendorConsentDelegate {

    private static final String MORE_PARTNERS_STRING_KEY = "IABTCF_AddtlConsent";

    private StickyBannerPlacement bannerPlacement = null;

    @Override
    public void onCreate() {
        super.onCreate();

        AATKitConfiguration configuration = new AATKitConfiguration(this);
        configuration.setTestModeAccountId(74); //TODO: remember to remove it before publishing the app!
        configuration.setConsent(new VendorConsent(this));

        // Google CMP needs an activity instance, so we cannot simply create it here
        AATKit.init(configuration);
        AATKit.enableDebugScreen();

        bannerPlacement = AATKit.createStickyBannerPlacement("Banner", BannerPlacementSize.Banner320x53);
    }

    public StickyBannerPlacement getBannerPlacement() {
        return bannerPlacement;
    }


    //VendorConsentDelegate implementation:

    @Override
    public NonIABConsent getConsentForNetwork(AdNetwork adNetwork) {
        switch (adNetwork) {
            case APPLOVIN:
                return readConsentForProviderId(1301);
            case METABIDDING:
                return readConsentForProviderId(89);
            case ADMOB:
            case DFP:
            case DFPDIRECT:
                return readConsentForProviderId(229);
            case PUBNATIVE:
                return readConsentForProviderId(2977);
            default:
                // Google CMP does not provide extra mappings for other non-IAB networks, treating them as WITHHELD
                return NonIABConsent.WITHHELD;
        }
    }

    @Override
    public NonIABConsent getConsentForAddapptr() {
        return NonIABConsent.UNKNOWN; //As an TCF2-compatible CMP is used in this sample, AATKit will rely on stored consent strings
    }

    //parse the special string saved by Google CMP with information about non-IAB partners
    private NonIABConsent readConsentForProviderId(int id) {
        String morePartnersConsentString = PreferenceManager.getDefaultSharedPreferences(this).getString(MORE_PARTNERS_STRING_KEY, null);
        if (morePartnersConsentString != null) {
            morePartnersConsentString = morePartnersConsentString.replaceFirst(".*~", ""); //remove "1~" or similar
            String[] partnersArray = morePartnersConsentString.split("\\.");
            List<String> enabledAdditionalPartners = Arrays.asList(partnersArray);
            if (enabledAdditionalPartners.contains(String.valueOf(id))) {
                return NonIABConsent.OBTAINED;
            } else {
                return NonIABConsent.WITHHELD;
            }
        } else {
            return NonIABConsent.WITHHELD;
        }
    }

}
