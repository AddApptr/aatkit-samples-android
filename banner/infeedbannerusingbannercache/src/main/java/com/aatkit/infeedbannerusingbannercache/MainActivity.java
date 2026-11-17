package com.aatkit.infeedbannerusingbannercache;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.intentsoftware.addapptr.AATKit;
import com.aatkit.infeedbannerusingbannercache.R;
import com.aatkit.infeedbannerusingbannercache.models.BannerCacheModel;
import com.aatkit.infeedbannerusingbannercache.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private InFeedApplication inFeedApplication;
    private SwipeRefreshLayout swipeContainer;
    final List<Object> list = new ArrayList<>();
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inFeedApplication = ((InFeedApplication) getApplication());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        swipeContainer = findViewById(R.id.swipe_refresh_layout);

        prepareContentData();
        listAdapter = new ListAdapter(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        swipeContainer.setOnRefreshListener(createOnRefreshListener());
        swipeContainer.setColorSchemeColors(
                getResources().getColor(R.color.gradientCenterColor),
                getResources().getColor(R.color.gradientEndColor),
                getResources().getColor(R.color.gradientStartColor));
        swipeContainer.setProgressViewOffset(true, 0, 60);
    }

    protected void onResume() {
        super.onResume();
        AATKit.onActivityResume(this);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        removeAd();
        AATKit.onActivityPause(this);
        super.onPause();
    }

    private void prepareContentData() {

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 10; i++)
                list.add(new NewsModel("Title " + (i + 1), "News " + (i + 1)));
            list.add(new BannerCacheModel(inFeedApplication.getBannerCache()));
        }
    }

    private SwipeRefreshLayout.OnRefreshListener createOnRefreshListener() {
        return () -> {

            removeAd();
            list.clear();
            prepareContentData();
            listAdapter.notifyDataSetChanged();
            swipeContainer.setRefreshing(false);
        };
    }

    private void removeAd() {
        for (Object object : list) {
            if (object instanceof BannerCacheModel) {
                ((BannerCacheModel) object).removeAd();
            }
        }
    }
}