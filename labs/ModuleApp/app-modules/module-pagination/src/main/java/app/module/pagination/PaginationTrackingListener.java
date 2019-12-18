package app.module.pagination;

public interface PaginationTrackingListener {
    void onPaginationLoaded(int page, int count, boolean success, boolean retry);
}
