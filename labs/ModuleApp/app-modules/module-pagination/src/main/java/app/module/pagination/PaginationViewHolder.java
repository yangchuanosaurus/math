package app.module.pagination;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * All sub classes of {@link PaginationViewHolder} exists in {@link ViewHolderFactory}
 *
 * - {@link ViewHolderFactory.LoadMoreViewHolder}
 * - {@link ViewHolderFactory.LoadMoreRetryViewHolder}
 *
 * Created by Albert Zhao on 2019-12-18.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public abstract class PaginationViewHolder extends RecyclerView.ViewHolder {
    private int mViewType;
    private ItemClickListener mItemClickListener;

    public PaginationViewHolder(int viewType, @NonNull View itemView) {
        super(itemView);
        mViewType = viewType;
    }

    public int getViewType() {
        return mViewType;
    }

    void setItemClickListener(@NonNull ItemClickListener listener) {
        mItemClickListener = listener;
    }

    protected <T> ItemClickListener<T> getItemClickListener() {
        return mItemClickListener;
    }
}
