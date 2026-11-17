package com.aatkit.infeedbannerusinginfeedapi;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aatkit.infeedbannerusinginfeedapi.holders.AdViewHolder;
import com.aatkit.infeedbannerusinginfeedapi.holders.NewsViewHolder;
import com.aatkit.infeedbannerusinginfeedapi.models.BannerModel;
import com.aatkit.infeedbannerusinginfeedapi.R;
import com.aatkit.infeedbannerusinginfeedapi.models.NewsModel;

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

            View bannerPlacementLayout = bannerModel.getBannerPlacementLayout();
            if (bannerPlacementLayout != null) {
                if (bannerPlacementLayout.getParent() != null) {
                    ((FrameLayout) bannerPlacementLayout.getParent()).removeAllViews();
                }
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
                adViewHolder.adFrame.addView(bannerPlacementLayout, params);
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
