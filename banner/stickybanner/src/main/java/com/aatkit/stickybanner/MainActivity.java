package com.aatkit.stickybanner;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.StickyBannerPlacement;
import com.aatkit.stickybanner.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onResume() {
        super.onResume();
        AATKit.onActivityResume(this);
        StickyBannerPlacement bannerPlacement = ((StickyBannerApplication) getApplication())
                .getBannerPlacement();
        addPlacementView(bannerPlacement);
        bannerPlacement.startAutoReload();
    }

    @Override
    protected void onPause() {
        StickyBannerPlacement bannerPlacement = ((StickyBannerApplication) getApplication())
                .getBannerPlacement();
        bannerPlacement.stopAutoReload();
        removePlacementView(bannerPlacement);
        AATKit.onActivityPause(this);
        super.onPause();
    }

    private void addPlacementView(StickyBannerPlacement placement) {
        FrameLayout mainLayout = (FrameLayout) findViewById(R.id.main_layout);
        View placementView = placement.getPlacementView();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        mainLayout.addView(placementView, layoutParams);
    }

    private void removePlacementView(StickyBannerPlacement placement) {
        View placementView = placement.getPlacementView();

        if (placementView != null && placementView.getParent() != null) {
            ViewGroup parent = (ViewGroup) placementView.getParent();
            parent.removeView(placementView);
        }
    }
}