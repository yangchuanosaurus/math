package app.module.pagination;

public interface PaginationTrackingListener {
    void onPagination(int page, boolean success, boolean retry);
}
