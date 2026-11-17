package com.aatkit.managedconsentsample;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.StickyBannerPlacement;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MyApplication.ManagedConsentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyApplication) getApplication()).onActivityReady(this);

        AATKit.onActivityResume(this);
        StickyBannerPlacement bannerPlacement = ((MyApplication) getApplication())
                .getBannerPlacement();
        addPlacementView(bannerPlacement);
        bannerPlacement.startAutoReload();
    }

    @Override
    protected void onPause() {
        StickyBannerPlacement bannerPlacement = ((MyApplication) getApplication())
                .getBannerPlacement();
        bannerPlacement.stopAutoReload();
        removePlacementView();
        AATKit.onActivityPause(this);
        super.onPause();
    }

    private void addPlacementView(StickyBannerPlacement placement) {
        FrameLayout bannerFrame = findViewById(R.id.bannerFrame);
        View placementView = placement.getPlacementView();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        bannerFrame.addView(placementView, layoutParams);
    }

    private void removePlacementView() {
        FrameLayout bannerFrame = findViewById(R.id.bannerFrame);
        bannerFrame.removeAllViews();
    }

    @Override
    public void managedConsentNeedsUI() {
        ((MyApplication) getApplication()).showCMPIfNeeded(this);
    }

    public void changeConsentSettings(View view) {
        ((MyApplication) getApplication()).editConsent(this);
    }
}