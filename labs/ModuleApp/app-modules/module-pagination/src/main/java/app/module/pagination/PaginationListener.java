package app.module.pagination;

public interface PaginationListener {
    void onPagination(int page, boolean success, boolean retry);
}
