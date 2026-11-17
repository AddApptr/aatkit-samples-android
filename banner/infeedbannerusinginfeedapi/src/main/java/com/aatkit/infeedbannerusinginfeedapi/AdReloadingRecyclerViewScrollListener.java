package com.aatkit.infeedbannerusinginfeedapi;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aatkit.infeedbannerusinginfeedapi.models.BannerModel;

import java.util.List;

public class AdReloadingRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private final List<Object> listModels;
    private boolean resumed;

    public AdReloadingRecyclerViewScrollListener(List<Object> listModels) {
        this.listModels = listModels;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

        if (resumed) { //handles situations when "onScrolled" is called after activity is paused
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstVisiblePosition = 0;
            int lastVisiblePosition = 0;
            if (layoutManager != null) {
                firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
            }

            if (firstVisiblePosition >= 0 && lastVisiblePosition >= 0) {
                int from;
                int to;

                if (dy < 0) { //scrolling up
                    from = Math.max(firstVisiblePosition - 2, 0);
                    to = lastVisiblePosition;
                } else { //scrolling down
                    from = firstVisiblePosition;
                    to = Math.min(lastVisiblePosition + 2, listModels.size());
                }

                ReloadAds(from, to);

            }
        }
        super.onScrolled(recyclerView, dx, dy);
    }

    public void ReloadVisibleAds(@NonNull RecyclerView recyclerView) {

        if (resumed) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstVisiblePosition = 0;
            int lastVisiblePosition = 0;
            if (layoutManager != null) {
                firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
            }

            if (firstVisiblePosition >= 0 && lastVisiblePosition >= 0) {
                int from;
                int to;

                from = firstVisiblePosition;
                to = Math.min(lastVisiblePosition, listModels.size());

                ReloadAds(from, to);
            }
        }
    }

    private void ReloadAds(int from, int to) {

        for (int i = from; i < to; i++) {
            if (listModels.get(i) instanceof BannerModel) {
                if (((BannerModel) listModels.get(i)).canReload()) {
                    ((BannerModel) listModels.get(i)).reloadBanner();
                }
            }
        }
    }

    public void onResume() {
        resumed = true;
    }

    public void onPause() {
        resumed = false;
    }
}
