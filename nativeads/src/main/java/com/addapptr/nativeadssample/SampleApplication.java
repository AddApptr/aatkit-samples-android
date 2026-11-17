package com.addapptr.nativeadssample;

import android.app.Application;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.NativeAdPlacement;

public class SampleApplication extends Application {

    private NativeAdPlacement nativeAdPlacement = null;

    public void onCreate() {
        super.onCreate();
        AATKitConfiguration configuration = new AATKitConfiguration(this);

        configuration.setTestModeAccountId(74); //TODO: remember to remove it before publishing the app!

        AATKit.init(configuration);
        nativeAdPlacement = AATKit.createNativeAdPlacement("Native", true);
    }

    public NativeAdPlacement getNativeAdPlacement() {
        return nativeAdPlacement;
    }

}
