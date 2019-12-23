package app.module.pagination;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Albert Zhao on 2019-12-23.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public abstract class PaginationGridFactory<T> extends PaginationFactory<T> {

    private int mGridColumnCount;

    protected PaginationGridFactory(int pageSize, int pageStart, int gridColumnCount) {
        super(pageSize, pageStart);
        mGridColumnCount = gridColumnCount;
    }

    @Override
    public Pagination<T> bindPagination(@NonNull Context context, Intent intent,
                                        @NonNull PaginationRecyclerView recyclerView) {
        return getIntentPagination(context, intent, recyclerView);
    }

    @SuppressWarnings("unchecked")
    private Pagination<T> getIntentPagination(@NonNull Context context,
                                              Intent intent,
                                              @NonNull PaginationRecyclerView recyclerView) {
        Pagination<T> pagination = getIntentPagination(intent);
        int itemPosition = 0;
        if (null != intent && null != intent.getExtras()) {
            if (intent.hasExtra(INTENT_ITEM_POSITION)) {
                itemPosition = intent.getExtras().getInt(INTENT_ITEM_POSITION);
            }
        }

        if (null == pagination) {
            // create pagination if no pagination in Intent
            PaginationLoader<T> loader = createPaginationLoader();
            pagination = createPagination(mPageSize, mStartPage, loader);
        }

        bindGridStyleAndAdapter(context, recyclerView, pagination);

        recyclerView.scrollToPosition(itemPosition);

        return pagination;
    }

    private void bindGridStyleAndAdapter(@NonNull Context context,
                                         @NonNull PaginationRecyclerView recyclerView,
                                         Pagination<T> pagination) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,
                mGridColumnCount, RecyclerView.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (recyclerView.isLoadMorePosition(position)) {
                    return mGridColumnCount;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

        PaginationAdapter<T> adapter = createGridAdapter(context, mGridColumnCount, pagination);
        recyclerView.setLoadMoreListener(recyclerView::loadNextPage);
        recyclerView.setAdapter(adapter);
    }

    protected abstract PaginationAdapter<T> createGridAdapter(@NonNull Context context,
                                                              int gridColumnCount,
                                                              Pagination<T> pagination);
}
