package module.pagination.app;

import android.content.Context;

import androidx.annotation.NonNull;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationAdapter;
import app.module.pagination.PaginationGridFactory;
import app.module.pagination.PaginationLoader;

/**
 * Created by Albert Zhao on 2019-12-23.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class PhotoGridPaginationFactory extends PaginationGridFactory<String> {

    public static final int PAGE_SIZE = 20;
    public static final int PAGE_START = PhotoPaginationLoader.MockPhotoDataSet.PAGE_START;

    private static final int GRID_COLUMN_COUNT = 3;

    private PhotoGridPaginationFactory() {
        super(PAGE_SIZE, PAGE_START, GRID_COLUMN_COUNT);
    }

    public static PhotoGridPaginationFactory create() {
        return new PhotoGridPaginationFactory();
    }

    @Override
    protected PaginationAdapter<String> createGridAdapter(@NonNull Context context, int gridColumnCount, Pagination<String> pagination) {
        return new PhotoGridAdapter(context, gridColumnCount, pagination);
    }

    @Override
    protected PaginationLoader<String> createPaginationLoader() {
        String eventId = "";
        return new PhotoPaginationLoader(eventId);
    }
}
