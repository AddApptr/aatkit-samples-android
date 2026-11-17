package com.aatkit.fullscreenusingautoreloading;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.FullscreenPlacement;
import com.aatkit.fullscreenusingautoreloading.R;

public class MainActivity extends AppCompatActivity {

    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApplication = (MyApplication) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AATKit.onActivityResume(this);
        FullscreenPlacement fullscreenPlacement = myApplication.getFullscreenPlacement();
        fullscreenPlacement.startAutoReload();
    }

    @Override
    protected void onPause() {
        FullscreenPlacement fullscreenPlacement = myApplication.getFullscreenPlacement();
        fullscreenPlacement.stopAutoReload();
        AATKit.onActivityPause(this);
        super.onPause();
    }

    public void showInterstitial(View view) {
        FullscreenPlacement fullscreenPlacement = myApplication.getFullscreenPlacement();
        fullscreenPlacement.show();
    }
}