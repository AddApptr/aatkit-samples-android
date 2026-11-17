package com.addapptr.nativeadssample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.intentsoftware.addapptr.AATKit;
import com.intentsoftware.addapptr.NativeAdPlacement;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ITEMS_PER_AD = 10;
    private boolean firstAdRequested;
    private RecyclerViewAdapter adapter;

    private NativeAdPlacement nativeAdPlacement = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Object> itemsList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nativeAdPlacement = ((SampleApplication) getApplication()).getNativeAdPlacement();
        adapter = new RecyclerViewAdapter(itemsList, nativeAdPlacement, this);
        recyclerView.setAdapter(adapter);

        addSampleData(itemsList);
        addNativeAds(itemsList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AATKit.onActivityResume(this);
        if (!firstAdRequested) { //prepare one ad at the very beginning
            firstAdRequested = true;
            nativeAdPlacement.reload();
        }
    }

    @Override
    protected void onPause() {
        AATKit.onActivityPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        adapter.onDestroy();
        super.onDestroy();
    }

    private void addSampleData(List<Object> itemsList) {
        for (int i = 0; i < 50; i++) {
            itemsList.add(new ContentModel(i));
        }
    }

    private void addNativeAds(List<Object> itemsList) {
        for (int i = ITEMS_PER_AD; i <= itemsList.size(); i += ITEMS_PER_AD) {
            itemsList.add(i, new AdModel(nativeAdPlacement));
        }
    }
}