package com.aatkit.infeedbannerusinginfeedapi.holders;

import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aatkit.infeedbannerusinginfeedapi.R;

public class AdViewHolder extends RecyclerView.ViewHolder {

    public final FrameLayout adFrame;

    public AdViewHolder(@NonNull View itemView) {
        super(itemView);
        adFrame = itemView.findViewById(R.id.empty_ad_view);
    }
}
