package module.pagination.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationRecyclerView;

public class PhotoListActivity extends AppCompatActivity {

    private PaginationRecyclerView mPaginationRecyclerView;
    private PhotoListAdapter mPhotoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        // Create a PaginationLoader for biz loader
        PhotoPaginationLoader photoLoader = createPaginationLoader();
        // shared the pagination between activities/fragments
        Pagination<String> photoListPagination = createPhotoListPagination(photoLoader);
        mPaginationRecyclerView.setPagination(photoListPagination);

        // created recycler view adapter for load page
        mPhotoListAdapter = createPhotoListAdapter(photoListPagination);

        // reload the first page
        mPhotoListAdapter.reload();

        // recycler view will handle the load more
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

}
