package app.module.pagination;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Albert Zhao on 2019-12-18.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public abstract class PaginationViewHolder extends RecyclerView.ViewHolder {
    private int mViewType;

    public PaginationViewHolder(int viewType, @NonNull View itemView) {
        super(itemView);
        mViewType = viewType;
    }

    public int getViewType() {
        return mViewType;
    }
}
