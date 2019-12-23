package app.module.pagination;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

/**
 * Created by Albert Zhao on 2019-12-23.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public abstract class PaginationFactory<T> {

    protected static final String INTENT_PAGINATION = "photos.pagination";
    protected static final String INTENT_ITEM_POSITION = "photos.pagination.position";

    int mPageSize;
    int mStartPage;

    PaginationFactory(int pageSize, int startPage) {
        mPageSize = pageSize;
        mStartPage = startPage;
    }

    /**
     * unique method of add itemPosition and Pagination to Intent
     *
     * @param intent which intent we should adding
     * @param itemPosition the position of item
     * @param pagination the Shared {@link Pagination} instance
     * */
    public static void addPaginationIntent(@NonNull Intent intent, int itemPosition, @NonNull Pagination<String> pagination) {
        intent.putExtra(INTENT_ITEM_POSITION, itemPosition);
        intent.putExtra(INTENT_PAGINATION, pagination.lifecycleClone());
    }

    @SuppressWarnings("unchecked")
    public static <T> Pagination<T> getIntentPagination(Intent intent) {
        Pagination<T> pagination = null;
        if (null != intent && null != intent.getExtras()) {
            if (intent.hasExtra(INTENT_PAGINATION)) {
                pagination = (Pagination<T>) intent.getExtras().getSerializable(INTENT_PAGINATION);
            }
        }
        return pagination;
    }

    protected Pagination<T> bindPagination(@NonNull Context context, Intent intent,
                                        @NonNull PaginationRecyclerView recyclerView) {
        return null;
    }

    Pagination<T> createPagination(int pageSize, int pageStart, PaginationLoader<T> paginationLoader) {
        return new Pagination<>(pageSize, pageStart, paginationLoader);
    }

    protected abstract PaginationLoader<T> createPaginationLoader();
}
