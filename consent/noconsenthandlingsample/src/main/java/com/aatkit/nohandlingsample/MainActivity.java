package com.aatkit.nohandlingsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.StickyBannerPlacement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        FrameLayout bannerFrame = (FrameLayout) findViewById(R.id.bannerFrame);
        bannerFrame.removeAllViews();
    }
}