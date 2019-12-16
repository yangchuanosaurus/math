package module.pagination.app;

import app.module.pagination.PaginationLoader;

public class PhotoPaginationLoader implements PaginationLoader<String> {
    private String mEventId;

    public PhotoPaginationLoader(String eventId) {
        mEventId = eventId;
    }

    @Override
    public void loadPage(int page, PaginationLoaderListener<String> paginationLoaderListener) {

    }
}
