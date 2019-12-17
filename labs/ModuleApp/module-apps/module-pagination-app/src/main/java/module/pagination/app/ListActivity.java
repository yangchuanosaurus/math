package module.pagination.app;

import androidx.annotation.NonNull;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationLoader;

public class ListActivity {

    public void onCreate() {
        // shared the pagination between activities/fragments
        Pagination<String> photoListPagination = createPhotoListPagination();
        // created recycler view adapter for load page
        PhotoListAdapter adapter = createPhotoListAdapter(photoListPagination);
        // reload the first page
        adapter.reload();
        // load the next page
        adapter.loadNextPage();
    }

    private PhotoListAdapter createPhotoListAdapter(@NonNull Pagination<String> pagination) {
        return new PhotoListAdapter(pagination);
    }

    private Pagination<String> createPhotoListPagination() {
        int pageSize = 20;
        int pageStart = 0;
        PaginationLoader<String> photoPaginationLoader = new PhotoPaginationLoader("");

        return new Pagination<>(pageSize, pageStart, photoPaginationLoader);
    }

}
