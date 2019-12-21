package app.module.pagination;

import java.io.Serializable;
import java.util.List;

public interface PaginationLoader<T> extends Serializable {
    void loadPage(int page, int pageSize, PaginationLoaderListener<T> paginationLoaderListener);

    interface PaginationLoaderListener<E> {
        void onPageLoaded(int page, List<E> entities);
        void onPageLoadFailed(int page);
    }
}
