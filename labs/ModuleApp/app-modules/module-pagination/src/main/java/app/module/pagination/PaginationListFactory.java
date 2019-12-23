package app.module.pagination;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by Albert Zhao on 2019-12-23.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public abstract class PaginationListFactory<T> extends PaginationFactory<T> {

    protected PaginationListFactory(int pageSize, int startPage) {
        super(pageSize, startPage);
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

        bindListStyleAndAdapter(context, recyclerView, pagination);

        recyclerView.scrollToPosition(itemPosition);

        return pagination;
    }

    private void bindListStyleAndAdapter(@NonNull Context context,
                                         @NonNull PaginationRecyclerView recyclerView,
                                         Pagination<T> pagination) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        PaginationAdapter<T> adapter = createListAdapter(pagination);
        recyclerView.setLoadMoreListener(recyclerView::loadNextPage);
        recyclerView.setAdapter(adapter);
    }

    protected abstract PaginationAdapter<T> createListAdapter(Pagination<T> pagination);
}
