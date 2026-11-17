package com.aatkit.infeedbannerusingbannercache.holders;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aatkit.infeedbannerusingbannercache.ActivityWithBanner;
import com.aatkit.infeedbannerusingbannercache.R;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    public final TextView titleView;
    public final TextView newsView;
    public final View itemView;

    public NewsViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        titleView = itemView.findViewById(R.id.title);
        newsView = itemView.findViewById(R.id.description);

        itemView.setOnClickListener(createOnClickListener());
    }

    private View.OnClickListener createOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), ActivityWithBanner.class);
                itemView.getContext().startActivity(intent);
            }
        };
    }
}
