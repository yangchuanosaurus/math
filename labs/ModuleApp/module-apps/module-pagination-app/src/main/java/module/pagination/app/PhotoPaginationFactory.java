package module.pagination.app;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationAdapter;
import app.module.pagination.PaginationRecyclerView;

/**
 * Created by Albert Zhao on 2019-12-23.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class PhotoPaginationFactory {

    public static final int PAGE_SIZE = 20;
    public static final int PAGE_START = PhotoPaginationLoader.MockPhotoDataSet.PAGE_START;

    private static final int GRID_COLUMN_COUNT = 3;

    private static final String INTENT_PAGINATION = "photos.pagination";
    private static final String INTENT_ITEM_POSITION = "photos.pagination.position";

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
    public static Pagination<String> getIntentPagination(Intent intent) {
        Pagination<String> pagination = null;
        if (null != intent && null != intent.getExtras()) {
            if (intent.hasExtra(INTENT_PAGINATION)) {
                pagination = (Pagination<String>) intent.getExtras().getSerializable(INTENT_PAGINATION);
            }
        }
        return pagination;
    }

    public static Pagination<String> bindPagination(@NonNull Context context,
                                                    @NonNull PaginationRecyclerView recyclerView,
                                                    @PhotoListStyle int style) {
        return bindIntentPagination(context, null, recyclerView, style);
    }

    @SuppressWarnings("unchecked")
    public static Pagination<String> bindIntentPagination(@NonNull Context context,
                                                    Intent intent,
                                                    @NonNull PaginationRecyclerView recyclerView,
                                                    @PhotoListStyle int style) {
        Pagination<String> pagination = null;
        int itemPosition = 0;
        if (null != intent && null != intent.getExtras()) {
            if (intent.hasExtra(INTENT_PAGINATION)) {
                pagination = (Pagination<String>) intent.getExtras().getSerializable(INTENT_PAGINATION);
            }
            if (intent.hasExtra(INTENT_ITEM_POSITION)) {
                itemPosition = intent.getExtras().getInt(INTENT_ITEM_POSITION);
            }
        }

        if (null == pagination) {
            PhotoPaginationLoader loader = createPaginationLoader();
            pagination = createPhotoPagination(loader);
        }

        return bind(context, recyclerView, style, pagination, itemPosition);
    }

    private static Pagination<String> bind(@NonNull Context context,
                                                    @NonNull PaginationRecyclerView recyclerView,
                                                    @PhotoListStyle int style,
                                                    Pagination<String> pagination,
                                                    int itemPosition) {

        PaginationAdapter<String> adapter = null;
        if (PhotoListStyle.LIST == style) {
            adapter = new PhotoListAdapter(pagination);
            bindLinearLayoutManager(context, recyclerView);
        } else if (PhotoListStyle.GRID == style) {
            adapter = new PhotoGridAdapter(context, GRID_COLUMN_COUNT, pagination);
            bindGridLayoutManager(context, recyclerView);
        } else {
            throw new RuntimeException("Unsupported PhotoListStyle of " + style);
        }

        recyclerView.setLoadMoreListener(recyclerView::loadNextPage);
        recyclerView.setAdapter(adapter);

        recyclerView.scrollToPosition(itemPosition);

        return pagination;
    }

    private static void bindLinearLayoutManager(@NonNull Context context,
                                                @NonNull PaginationRecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

    }

    private static void bindGridLayoutManager(@NonNull Context context,
                                              @NonNull PaginationRecyclerView recyclerView) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,
                GRID_COLUMN_COUNT, RecyclerView.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (recyclerView.isLoadMorePosition(position)) {
                    return GRID_COLUMN_COUNT;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private static Pagination<String> createPhotoPagination(PhotoPaginationLoader photoPaginationLoader) {
        return new Pagination<>(PAGE_SIZE, PAGE_START, photoPaginationLoader);
    }

    private static PhotoPaginationLoader createPaginationLoader() {
        String eventId = "";
        return new PhotoPaginationLoader(eventId);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            PhotoListStyle.LIST,
            PhotoListStyle.GRID
    })
    public @interface PhotoListStyle {
        int LIST = 0;
        int GRID = 1;
    }

}
