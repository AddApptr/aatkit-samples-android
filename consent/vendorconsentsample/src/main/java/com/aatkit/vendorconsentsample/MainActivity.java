package com.aatkit.vendorconsentsample;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;
import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.AATKitRuntimeConfiguration;
import com.intentsoftware.addapptr.StickyBannerPlacement;
import com.intentsoftware.addapptr.VendorConsent;

public class MainActivity extends AppCompatActivity {

    private ConsentInformation consentInformation;
    private ConsentForm consentForm;
    private ConsentForm.OnConsentFormDismissedListener onConsentFormDismissedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Google CMP implementation:
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        onConsentFormDismissedListener = new ConsentForm.OnConsentFormDismissedListener() {
            @Override
            public void onConsentFormDismissed(FormError formError) {
                MainActivity.this.consentForm = null;
                loadForm();
                AATKitRuntimeConfiguration newConfiguration = new AATKitRuntimeConfiguration();
                MyApplication myApplication = ((MyApplication) getApplication());
                newConfiguration.setConsent(new VendorConsent(myApplication));
                AATKit.reconfigure(newConfiguration); // Reconfigure AATKit every time consent is changed
            }
        };

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
                    @Override
                    public void onConsentInfoUpdateSuccess() {
                        // The consent information state was updated.
                        // You are now ready to check if a form is available.
                        if (consentInformation.isConsentFormAvailable()) {
                            loadForm();
                        }
                    }
                },
                new ConsentInformation.OnConsentInfoUpdateFailureListener() {
                    @Override
                    public void onConsentInfoUpdateFailure(FormError formError) {
                        // Handle the error.
                    }
                });
    }

    private void loadForm() { // Google CMP implementation
        UserMessagingPlatform.loadConsentForm(
                this,
                new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
                    @Override
                    public void onConsentFormLoadSuccess(ConsentForm consentForm) {
                        MainActivity.this.consentForm = consentForm;
                        if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                            consentForm.show(MainActivity.this, onConsentFormDismissedListener);
                        }

                    }
                },
                new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
                    @Override
                    public void onConsentFormLoadFailure(FormError formError) {
                        /// Handle Error.
                    }
                }
        );
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
        FrameLayout bannerFrame = findViewById(R.id.bannerFrame);
        bannerFrame.removeAllViews();
    }

    public void changeConsentSettings(View view) {
        if (consentForm != null) {
            consentForm.show(this, onConsentFormDismissedListener);
        }
    }
}