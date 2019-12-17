package module.pagination.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationLog;
import app.module.pagination.PaginationRecyclerView;
import app.module.pagination.PaginationTrackingListener;

public class PhotoListActivity extends AppCompatActivity implements PaginationTrackingListener {

    private PaginationRecyclerView mPaginationRecyclerView;
    private PhotoListAdapter mPhotoListAdapter;
    private Pagination<String> mPhotoListPagination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        mPaginationRecyclerView = findViewById(R.id.recycler_view);

        // Create a PaginationLoader for biz loader
        PhotoPaginationLoader photoLoader = createPaginationLoader();
        // shared the pagination between activities/fragments
        mPhotoListPagination = createPhotoListPagination(photoLoader);
        mPhotoListPagination.addTrackingListener(mPaginationRecyclerView);

        // Activity will tracking the pagination listener
        mPhotoListPagination.addTrackingListener(this);
        // todo does the RecyclerView needs know the Pagination?
        mPaginationRecyclerView.setPagination(mPhotoListPagination);

        // created recycler view adapter for load page
        mPhotoListAdapter = createPhotoListAdapter(mPhotoListPagination);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mPaginationRecyclerView.setLayoutManager(layoutManager);

        mPaginationRecyclerView.setAdapter(mPhotoListAdapter);

        // reload the first page
        mPhotoListAdapter.reload();

        // scroll listener of recycler view will handle the load more behavior
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPhotoListPagination.removeTrackingListener(mPaginationRecyclerView);
        mPhotoListPagination.removeTrackingListener(this);
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

    @Override
    public void onPagination(int page, int count, boolean success, boolean retry) {
        PaginationLog.d("PhotoListActivity onPagination page="
                + page + ", success=" + success + ", count=" + count);

        boolean isStartPage = mPhotoListPagination.isStartPage(page);
        if (isStartPage) {
            if (success && count == 0) {
                // todo show empty results
            } else if (!success) {
                // todo show error message and retry button
            }
        } else if (!success) {
            // todo adapter show load more failed and retry button inside
        }
    }
}
