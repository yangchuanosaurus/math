package module.pagination.app;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationLog;
import app.module.pagination.PaginationRecyclerView;
import app.module.pagination.PaginationTrackingListener;
import app.module.pagination.ViewHolderFactory;
import leakcanary.AppWatcher;

/**
 * Created by Albert Zhao on 2019-12-19.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class PhotoGridActivity extends AppCompatActivity implements PaginationTrackingListener {

    private PaginationRecyclerView mPaginationRecyclerView;
    private Pagination<String> mPhotoGridPagination;

    private View mViewEmptyResults;
    private View mViewLoadingPhotos;
    private View mViewFailedResults;

    private static final int GRID_COLUMN_COUNT = 3;
    public static final int PAGE_SIZE = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        mViewEmptyResults = findViewById(R.id.view_empty_results);
        mViewLoadingPhotos = findViewById(R.id.view_photo_loading);
        mViewFailedResults = findViewById(R.id.view_failed_results);

        mPaginationRecyclerView = findViewById(R.id.recycler_view);
        mPaginationRecyclerView.setHasFixedSize(true);

        // Create a PaginationLoader for biz loader, apply biz part
        PhotoPaginationLoader photoLoader = createPaginationLoader();
        // shared the pagination between activities/fragments
        mPhotoGridPagination = createPhotoListPagination(photoLoader);

        // created recycler view adapter for load page
        PhotoGridAdapter photoGridAdapter = createPhotoGridAdapter(mPhotoGridPagination);

        GridLayoutManager layoutManager = new GridLayoutManager(this, GRID_COLUMN_COUNT, RecyclerView.VERTICAL, false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mPaginationRecyclerView.isLoadMorePosition(position)) {
                    return GRID_COLUMN_COUNT;
                } else {
                    return 1;
                }
            }
        });

        mPaginationRecyclerView.setLayoutManager(layoutManager);
        mPaginationRecyclerView.setLoadMoreListener(() -> mPaginationRecyclerView.loadNextPage());

        mPaginationRecyclerView.setAdapter(photoGridAdapter);

        // Activity will tracking the pagination listener
        mPhotoGridPagination.addTrackingListener(this);

        reloadPhotos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PaginationLog.d("PhotoGridActivity onDestroy");

        AppWatcher.INSTANCE.getObjectWatcher().watch(mPaginationRecyclerView);
        AppWatcher.INSTANCE.getObjectWatcher().watch(mPhotoGridPagination);
        AppWatcher.INSTANCE.getObjectWatcher().watch(this);
    }

    // reload the first page
    private void reloadPhotos() {
        showEmptyResultsView(false);
        showFailedResultsView(false);
        mPaginationRecyclerView.reload();
        showLoadingView(true);
    }

    private PhotoGridAdapter createPhotoGridAdapter(@NonNull Pagination<String> pagination) {
        return new PhotoGridAdapter(this, GRID_COLUMN_COUNT, pagination);
    }

    private Pagination<String> createPhotoListPagination(PhotoPaginationLoader photoPaginationLoader) {
        int pageStart = PhotoPaginationLoader.MockPhotoDataSet.PAGE_START;

        return new Pagination<>(PAGE_SIZE, pageStart, photoPaginationLoader);
    }

    private PhotoPaginationLoader createPaginationLoader() {
        String eventId = "";

        return new PhotoPaginationLoader(eventId);
    }

    private void showEmptyResultsView(boolean show) {
        mViewEmptyResults.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showFailedResultsView(boolean show) {
        mViewFailedResults.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showLoadingView(boolean show) {
        mViewLoadingPhotos.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPaginationLoaded(int page, int count, boolean success, boolean retry) {
        PaginationLog.d("PhotoListActivity onPaginationLoaded page="
                + page + ", success=" + success + ", count=" + count);

        showLoadingView(false);
        boolean isStartPage = mPhotoGridPagination.isStartPage(page);
        if (isStartPage) {
            if (success && count == 0) {
                // show empty results
                showEmptyResultsView(true);
            } else if (!success) {
                // show error message and retry button
                showFailedResultsView(true);
            }
        } else if (!success) {
            // PaginationRecyclerView has handled this error case
        }
    }

}
