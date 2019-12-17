package app.module.pagination;

public interface PaginationTrackingListener {
    void onPagination(int page, int count, boolean success, boolean retry);
}
