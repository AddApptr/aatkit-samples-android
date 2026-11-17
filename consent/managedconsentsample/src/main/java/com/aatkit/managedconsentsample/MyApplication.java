package com.aatkit.managedconsentsample;

import android.app.Activity;
import android.app.Application;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.AATKitRuntimeConfiguration;
import com.intentsoftware.addapptr.BannerPlacementSize;
import com.intentsoftware.addapptr.ManagedConsent;
import com.intentsoftware.addapptr.StickyBannerPlacement;
import com.intentsoftware.addapptr.consent.CMP;
import com.intentsoftware.addapptr.consent.CMPGoogle;

public class MyApplication extends Application implements ManagedConsent.ManagedConsentDelegate {

    interface ManagedConsentListener {
        void managedConsentNeedsUI();
    }

    private StickyBannerPlacement bannerPlacement = null;
    private ManagedConsent managedConsent;
    private ManagedConsentListener listener;

    @Override
    public void onCreate() {
        super.onCreate();

        AATKitConfiguration configuration = new AATKitConfiguration(this);
        configuration.setTestModeAccountId(74); //TODO: remember to remove it before publishing the app!

        // Google CMP needs an activity instance, so we cannot simply create it here
        AATKit.init(configuration);
        AATKit.enableDebugScreen();

        bannerPlacement = AATKit.createStickyBannerPlacement("Banner", BannerPlacementSize.Banner320x53);
    }

    public StickyBannerPlacement getBannerPlacement() {
        return bannerPlacement;
    }

    public void onActivityReady(MainActivity mainActivity) {
        if (managedConsent == null) { //we want to do it only once
            CMP cmp = new CMPGoogle(mainActivity);
            managedConsent = new ManagedConsent(cmp, this, this);
            AATKitRuntimeConfiguration newConf = new AATKitRuntimeConfiguration();
            newConf.setConsent(managedConsent);
            AATKit.reconfigure(newConf);
        } else {
            managedConsent.showIfNeeded(mainActivity); //if managedConsent was already initialized - show CMP if it is needed
        }
        listener = mainActivity;
    }

    void showCMPIfNeeded(Activity activity) {
        if (managedConsent != null) {
            managedConsent.showIfNeeded(activity);
        }
    }

    void editConsent(Activity activity) {
        if (managedConsent != null) {
            managedConsent.editConsent(activity);
        }
    }

    // ManagedConsentDelegate implementation:

    @Override
    public void managedConsentNeedsUserInterface(ManagedConsent managedConsent) {
        if (listener != null) {
            listener.managedConsentNeedsUI();
        }
    }

    @Override
    public void managedConsentCMPFinished(ManagedConsent.ManagedConsentState state) {
    }

    @Override
    public void managedConsentCMPFailedToLoad(ManagedConsent managedConsent, String error) {
        // consider reloading, by managedConsent.reload(activity); call
    }

    @Override
    public void managedConsentCMPFailedToShow(ManagedConsent managedConsent, String error) {
        // consider reloading, by managedConsent.reload(activity); call
    }
}
