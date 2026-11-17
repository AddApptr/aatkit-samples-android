package com.aatkit.infeedbannerusingbannercache;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.BannerCache;
import com.intentsoftware.addapptr.BannerPlacementLayout;

public class ActivityWithBanner extends AppCompatActivity {

    private FrameLayout addView;
    private BannerPlacementLayout bannerPlacementLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_row_empty);

        addView = findViewById(R.id.empty_ad_view);
    }

    @Override
    protected void onPause() {
        AATKit.onActivityPause(this);

        if (bannerPlacementLayout != null) {
            bannerPlacementLayout.destroy(); //destroy the loaded banner
            bannerPlacementLayout = null;
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AATKit.onActivityResume(this);

        InFeedApplication inFeedApplication = (InFeedApplication) getApplication();
        BannerCache bannerCache = inFeedApplication.getBannerCache();
        bannerPlacementLayout = bannerCache.consume();

        if (bannerPlacementLayout != null) {
            if (bannerPlacementLayout.getParent() != null) {
                ((FrameLayout) bannerPlacementLayout.getParent()).removeAllViews();
            }
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            addView.addView(bannerPlacementLayout, params);
        }
    }
}
