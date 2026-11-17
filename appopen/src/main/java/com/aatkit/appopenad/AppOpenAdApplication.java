package com.aatkit.appopenad;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.AATKitDebugScreenConfiguration;
import com.intentsoftware.addapptr.AppOpenAdPlacement;
import com.intentsoftware.addapptr.AppOpenPlacementListener;
import com.intentsoftware.addapptr.Placement;

public class AppOpenAdApplication extends Application implements AATKit.Delegate, LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private AATKitEventListener listener;
    private boolean isAdAvailable = false;
    private AppOpenAdPlacement appOpenFullscreenAdPlacement = null;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        AATKitConfiguration config = new AATKitConfiguration(this);
        config.setDelegate(this);
        config.setConsentRequired(true);
        AATKit.init(config);

        AATKitDebugScreenConfiguration aatKitDebugScreenConfiguration = new AATKitDebugScreenConfiguration(null, "App Open Ad  ");
        AATKit.configureDebugScreen(aatKitDebugScreenConfiguration);

        appOpenFullscreenAdPlacement = AATKit.createAppOpenAdPlacement("AppOpen");

        if (appOpenFullscreenAdPlacement != null) {
            appOpenFullscreenAdPlacement.setListener(createAppOpenPlacementListener());
            appOpenFullscreenAdPlacement.startAutoReload();
        }

    }

    public void setListener(AATKitEventListener listener) {
        this.listener = listener;
    }

    public boolean isAdAvailable() {
        return isAdAvailable;
    }

    private AppOpenPlacementListener createAppOpenPlacementListener() {
        return new AppOpenPlacementListener() {
            @Override
            public void onNoAd(@NonNull Placement placement) {
                isAdAvailable = false;
            }

            @Override
            public void onHaveAd(@NonNull Placement placement) {
                if (listener != null)
                    listener.onHaveAd();
                isAdAvailable = true;
            }

            @Override
            public void onResumeAfterAd(@NonNull Placement placement) {

            }

            @Override
            public void onPauseForAd(@NonNull Placement placement) {
                isAdAvailable = false;
            }
        };
    }

    /**
     * AATKit.Delegate methods
     */

    @Override
    public void aatkitObtainedAdRules(boolean fromTheServer) {

    }

    @Override
    public void aatkitUnknownBundleId() {

    }

    /**
     * Application.ActivityLifecycleCallbacks methods
     */
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (activity instanceof MainActivity) {
            AATKit.onActivityResume(activity);
            appOpenFullscreenAdPlacement.startAutoReload();
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        if (activity instanceof MainActivity) {
            appOpenFullscreenAdPlacement.stopAutoReload();
            AATKit.onActivityPause(activity);
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    /**
     * LifecycleObserver methods
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        appOpenFullscreenAdPlacement.show();
        isAdAvailable = false;
    }

}
