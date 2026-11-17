package com.aatkit.rewardedvideousingautoreloading;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitConfiguration;
import com.intentsoftware.addapptr.AATKitReward;
import com.intentsoftware.addapptr.Placement;
import com.intentsoftware.addapptr.RewardedVideoPlacement;
import com.intentsoftware.addapptr.RewardedVideoPlacementListener;

public class MyApplication extends Application implements AATKit.Delegate {

    private RewardedVideoPlacement rewardedVideoPlacement = null;
    private AATKitEventListener aatKitEventListener;

    @Override
    public void onCreate() {
        super.onCreate();

        AATKitConfiguration configuration = new AATKitConfiguration(this);
        configuration.setDelegate(this);
        configuration.setUseDebugShake(true);
        configuration.setTestModeAccountId(74);

        AATKit.init(configuration);
        AATKit.enableDebugScreen();

        rewardedVideoPlacement = AATKit.createRewardedVideoPlacement("RewardedVideo");
        if (rewardedVideoPlacement != null)
            rewardedVideoPlacement.setListener(new RewardedVideoPlacementListener() {
                @Override
                public void onUserEarnedIncentive(@NonNull Placement placement, @Nullable AATKitReward aatKitReward) {
                    aatKitEventListener.onUserEarnedIncentive(aatKitReward);
                }

                @Override
                public void onNoAd(@NonNull Placement placement) {

                }

                @Override
                public void onHaveAd(@NonNull Placement placement) {

                }

                @Override
                public void onResumeAfterAd(@NonNull Placement placement) {

                }

                @Override
                public void onPauseForAd(@NonNull Placement placement) {

                }
            });
    }

    public RewardedVideoPlacement getRewardedVideoPlacement() {
        return rewardedVideoPlacement;
    }

    public void setListener(AATKitEventListener aatKitEventListener) {
        this.aatKitEventListener = aatKitEventListener;
    }

    @Override
    public void aatkitObtainedAdRules(boolean fromTheServer) {

    }

    @Override
    public void aatkitUnknownBundleId() {

    }
}
