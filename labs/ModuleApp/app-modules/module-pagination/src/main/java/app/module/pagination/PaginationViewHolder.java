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
public abstract class PaginationViewHolder<T> extends RecyclerView.ViewHolder
        implements ViewHolderClickable<T> {
    private int mViewType;
    private Pagination<T> mPagination;
    private ItemClickListener<T> mItemClickListener;

    public PaginationViewHolder(int viewType, @NonNull View itemView) {
        super(itemView);
        mViewType = viewType;
    }

    public int getViewType() {
        return mViewType;
    }

    void setPagination(@NonNull Pagination<T> pagination) {
        mPagination = pagination;
    }

    void setItemClickListener(@NonNull ItemClickListener listener) {
        mItemClickListener = listener;
    }

    /**
     * check if the ItemClickListener is null after constructor finished
     * */
    public boolean isClickable() {
        return null != mItemClickListener;
    }

    protected void onItemActionClick(int action) {
        if (isClickable()) {
            int pos = getAdapterPosition();
            T entity = mPagination.getEntityAtPosition(pos);
            getItemClickListener().onItemActionClick(entity, action);
        }
    }

    public ItemClickListener<T> getItemClickListener() {
        return mItemClickListener;
    }

}
