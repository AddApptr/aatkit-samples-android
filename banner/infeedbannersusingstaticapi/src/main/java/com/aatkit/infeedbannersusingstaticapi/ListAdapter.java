package com.aatkit.infeedbannersusingstaticapi;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aatkit.infeedbannersusingstaticapi.holders.AdViewHolder;
import com.aatkit.infeedbannersusingstaticapi.holders.NewsViewHolder;
import com.aatkit.infeedbannersusingstaticapi.models.BannerModel;
import com.aatkit.infeedbannersusingstaticapi.models.NewsModel;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> newsList;

    private static final int TYPE_NEWS = 1;
    private static final int TYPE_BANNER = 2;

    public ListAdapter(List<Object> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_news, parent, false);
                return new NewsViewHolder(itemView);
            case 2:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_empty, parent, false);
                return new AdViewHolder(itemView);
            default:
                return new RecyclerView.ViewHolder(parent) {
                };
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (newsList.get(position) instanceof NewsModel) {
            NewsModel newsModel = (NewsModel) newsList.get(position);
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            newsViewHolder.titleView.setText(newsModel.getTitle());
            newsViewHolder.newsView.setText(newsModel.getNews());
        } else if (newsList.get(position) instanceof BannerModel) {
            AdViewHolder adViewHolder = (AdViewHolder) holder;
            adViewHolder.adFrame.removeAllViews();
            BannerModel bannerModel = (BannerModel) newsList.get(position);

            View bannerPlacementView = bannerModel.getBannerPlacementView();

            if (bannerPlacementView != null){
                if (bannerPlacementView.getParent() != null) {
                    ViewGroup parent = (ViewGroup) bannerPlacementView.getParent();
                    parent.removeView(bannerPlacementView);
                }
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                adViewHolder.adFrame.addView(bannerPlacementView, layoutParams);
            }
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (newsList.get(position) instanceof NewsModel) {
            return TYPE_NEWS;
        } else {
            return TYPE_BANNER;
        }
    }
}
