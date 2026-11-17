package com.aatkit.rewardedvideousingautoreloading;

import androidx.annotation.Nullable;

import com.intentsoftware.addapptr.AATKitReward;

public interface AATKitEventListener {

    void onUserEarnedIncentive(@Nullable AATKitReward aatKitReward);
}