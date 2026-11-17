package com.aatkit.infeedbannersusingstaticapi.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aatkit.infeedbannersusingstaticapi.R;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    public final TextView titleView;
    public final TextView newsView;

    public NewsViewHolder(View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.title);
        newsView = itemView.findViewById(R.id.description);
    }
}
