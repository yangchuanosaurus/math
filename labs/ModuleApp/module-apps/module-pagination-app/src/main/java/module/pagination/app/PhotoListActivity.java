package module.pagination.app;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import app.module.pagination.ItemClickListener;
import app.module.pagination.Pagination;
import app.module.pagination.PaginationLog;
import app.module.pagination.PaginationRecyclerView;
import app.module.pagination.PaginationTrackingListener;
import app.module.pagination.ViewHolderFactory;
import leakcanary.AppWatcher;
import leakcanary.ObjectWatcher;

public class PhotoListActivity extends AppCompatActivity implements PaginationTrackingListener {

    private PaginationRecyclerView mPaginationRecyclerView;
    private Pagination<String> mPhotoListPagination;

    private View mViewEmptyResults;
    private View mViewLoadingPhotos;
    private View mViewFailedResults;

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
        mPhotoListPagination = createPhotoListPagination(photoLoader);

        // created recycler view adapter for load page
        PhotoListAdapter photoListAdapter = createPhotoListAdapter(mPhotoListPagination);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPaginationRecyclerView.setLayoutManager(layoutManager);
        mPaginationRecyclerView.setLoadMoreListener(() -> mPaginationRecyclerView.loadNextPage());
        mPaginationRecyclerView.setOnItemClickListener(new ItemClickListener<String>() {
            @Override
            public void onItemActionClick(int itemPosition, String item, int actionId) {
                PaginationLog.d("onItemActionClick " + item);
            }
        });

        mPaginationRecyclerView.setAdapter(photoListAdapter);

        // Activity will tracking the pagination listener
        mPhotoListPagination.addTrackingListener(this);

        reloadPhotos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PaginationLog.d("PhotoListActivity onDestroy");

        AppWatcher.INSTANCE.getObjectWatcher().watch(mPaginationRecyclerView);
        AppWatcher.INSTANCE.getObjectWatcher().watch(mPhotoListPagination);
        AppWatcher.INSTANCE.getObjectWatcher().watch(this);
    }

    // reload the first page
    private void reloadPhotos() {
        showEmptyResultsView(false);
        showFailedResultsView(false);
        mPaginationRecyclerView.reload();
        showLoadingView(true);
    }

    private PhotoListAdapter createPhotoListAdapter(@NonNull Pagination<String> pagination) {
        return new PhotoListAdapter(pagination);
    }

    private Pagination<String> createPhotoListPagination(PhotoPaginationLoader photoPaginationLoader) {
        int pageSize = 20;
        int pageStart = PhotoPaginationLoader.MockPhotoDataSet.PAGE_START;

        return new Pagination<>(pageSize, pageStart, photoPaginationLoader);
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
        boolean isStartPage = mPhotoListPagination.isStartPage(page);
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
