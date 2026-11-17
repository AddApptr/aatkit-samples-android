package com.addapptr.nativeadssample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdView;
import com.intentsoftware.addapptr.AdNetwork;
import com.intentsoftware.addapptr.NativeAdPlacement;
import com.intentsoftware.addapptr.NativePlacementListener;
import com.intentsoftware.addapptr.Placement;
import com.intentsoftware.addapptr.ad.NativeAdData;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NativePlacementListener {

    private static final int CONTENT_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;

    private int loadedNativeAds;
    private final List<Object> itemsList;
    private final NativeAdPlacement nativeAdPlacement;
    private final Context context;

    public RecyclerViewAdapter(List<Object> itemsList, NativeAdPlacement nativeAdPlacement, Context context) {
        this.itemsList = itemsList;
        this.nativeAdPlacement = nativeAdPlacement;
        this.context = context;
        nativeAdPlacement.setListener(this);
    }

    public void onDestroy() {
        for (Object o : itemsList) {
            if (o instanceof AdModel) {
                ((AdModel) o).destroy();
            }
        }
        loadedNativeAds = 0;
    }

    @Override
    public void onPauseForAd(@NonNull Placement placement) {
    }

    @Override
    public void onResumeAfterAd(@NonNull Placement placement) {
    }

    @Override
    public void onHaveAd(@NonNull Placement placement) {
        loadedNativeAds++;
    }

    @Override
    public void onNoAd(@NonNull Placement placement) {
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        ContentViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
        }
    }

    public static class AdViewHolder extends RecyclerView.ViewHolder {

        AdViewHolder(View view) {
            super(view);
        }

        public void clear() {
            ((FrameLayout) itemView).removeAllViews();
        }

        @SuppressLint("InflateParams")
        public void showAd(NativeAdData nativeAd, Context context) {
            ViewGroup nativeAdView;
            MediaView mediaView;
            View mainImageView = null;

            TextView titleView = null;
            ImageView iconView = null;
            TextView descriptionView = null;
            Button ctaView = null;

            // Prepare the views for native ad, depending on ad network:
            if ((nativeAd.getNetwork() == AdNetwork.ADMOB || nativeAd.getNetwork() == AdNetwork.DFP)) {
                nativeAdView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.native_ad_google, null);

                titleView = nativeAdView.findViewById(R.id.title_view);
                iconView = nativeAdView.findViewById(R.id.icon_view);
                mainImageView = nativeAdView.findViewById(R.id.media_view);
                descriptionView = nativeAdView.findViewById(R.id.description_view);
                ctaView = nativeAdView.findViewById(R.id.CTA_view);

                ((NativeAdView) nativeAdView).setCallToActionView(ctaView);
                ((NativeAdView) nativeAdView).setBodyView(descriptionView);
                ((NativeAdView) nativeAdView).setHeadlineView(titleView);
                ((NativeAdView) nativeAdView).setIconView(iconView);

            } else if (nativeAd.getNetwork() == AdNetwork.INMOBI) {
                nativeAdView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.native_ad_inmobi, null);
                mainImageView = nativeAdView.findViewById(R.id.main_image_view);
                titleView = nativeAdView.findViewById(R.id.title_view);
                iconView = nativeAdView.findViewById(R.id.icon_view);
                descriptionView = nativeAdView.findViewById(R.id.description_view);
                ctaView = nativeAdView.findViewById(R.id.CTA_view);
            } else {
                nativeAdView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.native_ad, null);
                mainImageView = nativeAdView.findViewById(R.id.main_image_view);
                titleView = nativeAdView.findViewById(R.id.title_view);
                iconView = nativeAdView.findViewById(R.id.icon_view);
                descriptionView = nativeAdView.findViewById(R.id.description_view);
                ctaView = nativeAdView.findViewById(R.id.CTA_view);
            }


            // Fill the views with native ad assets:
            if (mainImageView instanceof ImageView) {
                Utils.loadBitmapForView(nativeAd.getImageUrl(), (ImageView) mainImageView);
            }
            if (iconView != null) {
                Utils.loadBitmapForView(nativeAd.getIconUrl(), iconView);
            }

            if (titleView != null) {
                titleView.setText(nativeAd.getTitle());
            }
            if (descriptionView != null) {
                descriptionView.setText(nativeAd.getDescription());
            }
            if (ctaView != null) {
                ctaView.setText(nativeAd.getCallToAction());
            }

            View sponsoredImage = nativeAd.getBrandingLogo();
            FrameLayout sponsoredImageFL = nativeAdView.findViewById(R.id.sponsored_image);
            if (sponsoredImage != null) {
                if (sponsoredImage.getParent() != null) {
                    ((FrameLayout) sponsoredImage.getParent()).removeAllViews();
                }
                sponsoredImageFL.addView(sponsoredImage);
            }

            // Add the prepared native ad to layout
            ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            ((FrameLayout) itemView).addView(nativeAdView, params);

            nativeAd.attachToLayout(nativeAdView, mainImageView, iconView, ctaView);
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (itemsList.get(position) instanceof AdModel) {
            return AD_VIEW_TYPE;
        } else {
            return CONTENT_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case CONTENT_VIEW_TYPE:
                View contentView = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.content_container,
                        viewGroup, false);
                return new ContentViewHolder(contentView);
            case AD_VIEW_TYPE:
                // fall through
            default:
                View adView = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.ad_container,
                        viewGroup, false);
                return new AdViewHolder(adView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case CONTENT_VIEW_TYPE:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                ContentModel content = (ContentModel) itemsList.get(position);

                contentViewHolder.textView.setText(content.getText());
                break;
            case AD_VIEW_TYPE:
                // fall through
            default:
                AdViewHolder adViewHolder = (AdViewHolder) holder;
                AdModel adModel = (AdModel) itemsList.get(position);
                adModel.reportAdSpace(); //AdModel logic will take care not to report unnecessary adspaces
                NativeAdData nativeAd = adModel.getNativeAd();

                if (nativeAd == null) {
                    nativeAd = nativeAdPlacement.getNativeAd(); //check if there is an ad already loaded
                    if (nativeAd != null) {
                        adModel.setNativeAd(nativeAd);
                    }
                    requestNativeAd();
                }

                adViewHolder.clear();
                if (nativeAd != null) {
                    adViewHolder.showAd(nativeAd, context);
                }
        }
    }

    private int numberOfAdsInFeed() {
        int numberOfAds = 0;
        for (Object o : itemsList) {
            if (o instanceof AdModel) {
                numberOfAds++;
            }
        }

        return numberOfAds;
    }

    private void requestNativeAd() { //makes sure not to request too many ads
        if (loadedNativeAds + nativeAdPlacement.getNumberOfCurrentlyLoadingNativeAds() < numberOfAdsInFeed()) {
            nativeAdPlacement.reload();
        }
    }

}
