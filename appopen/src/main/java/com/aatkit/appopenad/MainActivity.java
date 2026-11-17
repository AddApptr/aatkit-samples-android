package com.aatkit.appopenad;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    AppOpenAdApplication appOpenAdApplication;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appOpenAdApplication = (AppOpenAdApplication) getApplication();
        appOpenAdApplication.setListener(() -> setText(true));
        textView = findViewById(R.id.ad_state);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setText(appOpenAdApplication.isAdAvailable());
    }

    private void setText(boolean isAdAvailable) {
        if (isAdAvailable)
            textView.setText("Ad loaded");
        else
            textView.setText("Loading...");

    }
}