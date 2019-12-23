package module.pagination.app;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationAdapter;
import app.module.pagination.PaginationListFactory;

/**
 * This is a builder factory to create instances
 *
 * Created by Albert Zhao on 2019-12-23.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class PhotoListPaginationFactory extends PaginationListFactory<String> {

    public static final int PAGE_SIZE = 20;
    public static final int PAGE_START = PhotoPaginationLoader.MockPhotoDataSet.PAGE_START;

    public PhotoListPaginationFactory() {
        super(PAGE_SIZE, PAGE_START);
    }

    public static PhotoListPaginationFactory create() {
        return new PhotoListPaginationFactory();
    }

    @Override
    protected PhotoPaginationLoader createPaginationLoader() {
        String eventId = "";
        return new PhotoPaginationLoader(eventId);
    }

    @Override
    protected PaginationAdapter<String> createListAdapter(Pagination<String> pagination) {
        return new PhotoListAdapter(pagination);
    }

}
