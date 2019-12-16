package module.pagination.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import app.module.pagination.Pagination;
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

        // Create a PaginationLoader for biz loader
        PhotoPaginationLoader photoLoader = createPaginationLoader();
        // shared the pagination between activities/fragments
        mPhotoListPagination = createPhotoListPagination(photoLoader);

        // Activity will tracking the pagination listener
        mPhotoListPagination.addTrackingListener(this);
        // todo does the RecyclerView needs know the Pagination?
        mPaginationRecyclerView.setPagination(mPhotoListPagination);

        // created recycler view adapter for load page
        mPhotoListAdapter = createPhotoListAdapter(mPhotoListPagination);

        // reload the first page
        mPhotoListAdapter.reload();

        // scroll listener of recycler view will handle the load more behavior
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPhotoListPagination.removeTrackingListener(this);
    }

    private PhotoListAdapter createPhotoListAdapter(@NonNull Pagination<String> pagination) {
        return new PhotoListAdapter(pagination);
    }

    private Pagination<String> createPhotoListPagination(PhotoPaginationLoader photoPaginationLoader) {
        int pageSize = 20;
        int pageStart = 0;

        return new Pagination<>(pageSize, pageStart, photoPaginationLoader);
    }

    private PhotoPaginationLoader createPaginationLoader() {
        String eventId = "";

        return new PhotoPaginationLoader(eventId);
    }

    @Override
    public void onPagination(int page, boolean success, boolean retry) {
        if (page == 0 && !success) {
            // todo show error message and retry button
        } else if (page > 0 && !success) {
            // todo adapter show load more failed and retry button inside
        }
    }
}
