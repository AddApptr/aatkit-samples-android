package com.aatkit.startinterstitial.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.intentsoftware.addapptr.AATKit;
import com.aatkit.startinterstitial.MyApplication;
import com.aatkit.startinterstitial.R;

public class SplashScreenActivity extends Activity {

    private MyApplication myApplication;
    private CountDownTimer countDownTimer;
    private TextView timerTextView;
    private static final int SPLASH_TIME = 7000;
    private static final int SECOND = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        timerTextView = findViewById(R.id.timer_view);
        timerTextView.setText((SPLASH_TIME / SECOND) + " seconds");

        myApplication = (MyApplication) getApplication();
        myApplication.setListener(this::startMainActivity);

        countDownTimer = new CountDownTimer(SPLASH_TIME, SECOND / 4) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {

                String secondText;
                if ((millisUntilFinished) / SECOND <= 0)
                    secondText = " second";
                else
                    secondText = " seconds";

                timerTextView.setText((millisUntilFinished + SECOND) / SECOND + secondText);
            }

            @Override
            public void onFinish() {
                if (!myApplication.getFullscreenPlacement().show())
                    startMainActivity();
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AATKit.onActivityResume(this);
        myApplication.getFullscreenPlacement().startAutoReload();
    }

    @Override
    protected void onPause() {
        myApplication.getFullscreenPlacement().stopAutoReload();
        AATKit.onActivityPause(this);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onPause();
    }

    private void startMainActivity() {
        if (countDownTimer != null)
            countDownTimer.cancel();
        Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
        SplashScreenActivity.this.startActivity(mainIntent);
        SplashScreenActivity.this.finish();
    }
}
