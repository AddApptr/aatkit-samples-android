package com.aatkit.rewardedvideousingautoreloading;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.RewardedVideoPlacement;
import com.aatkit.rewardedvideousingautoreloading.R;

public class MainActivity extends AppCompatActivity {

    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApplication = (MyApplication) getApplication();
        myApplication.setListener((aatKitReward) -> {
            String message = "Incentive earned! " + (aatKitReward != null ? (", " + aatKitReward) : "") + ")";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AATKit.onActivityResume(this);
        RewardedVideoPlacement rewardedVideoPlacement = myApplication.getRewardedVideoPlacement();
        rewardedVideoPlacement.startAutoReload();
    }

    @Override
    protected void onPause() {
        RewardedVideoPlacement rewardedVideoPlacement = myApplication.getRewardedVideoPlacement();
        rewardedVideoPlacement.stopAutoReload();
        AATKit.onActivityPause(this);
        super.onPause();
    }

    public void showRewardedVideo(View view) {
        RewardedVideoPlacement rewardedVideoPlacement = myApplication.getRewardedVideoPlacement();
        rewardedVideoPlacement.show();
    }
}