package com.aatkit.infeedbannersusingstaticapi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aatkit.infeedbannersusingstaticapi.models.BannerModel;
import com.aatkit.infeedbannersusingstaticapi.models.NewsModel;
import com.intentsoftware.addapptr.AATKit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Object> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        prepareContentData();
        ListAdapter listAdapter = new ListAdapter(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    protected void onResume() {
        super.onResume();
        AATKit.onActivityResume(this);
        ((InfeedBannerUsingStaticApiApplication) getApplication()).getBannerPlacement().startAutoReload();
    }

    @Override
    protected void onPause() {
        ((InfeedBannerUsingStaticApiApplication) getApplication()).getBannerPlacement().stopAutoReload();

        AATKit.onActivityPause(this);
        super.onPause();
    }

    private void prepareContentData() {

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 10; i++)
                list.add(new NewsModel("Title " + (i + 1), "News " + (i + 1)));
            list.add(new BannerModel(((InfeedBannerUsingStaticApiApplication) getApplication()).getBannerPlacement()));
        }
    }
}