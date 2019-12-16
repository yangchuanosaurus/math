package app.module.pagination;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> {
    private final int mPageSize;
    private final int mPageStart;
    private int mPage;
    private List<T> mEntities;
    private boolean mHasMore;

    private final PaginationLoader<T> mPaginationLoader;
    private boolean mLoading;

    public Pagination(int pageSize, int pageStart, @NonNull PaginationLoader<T> paginationLoader) {
        mPageSize = pageSize;
        mPageStart = pageStart;
        mPage = pageStart - 1;
        mHasMore = true;
        mLoading = false;

        mEntities = new ArrayList<>();

        mPaginationLoader = paginationLoader;
    }

    public int getCurrentPage() {
        return mPage;
    }

    public boolean isLoading() {
        return mLoading;
    }

    boolean isStartPage(int page) {
        return page == mPageStart;
    }

    int getEntitiesCount() {
        return mEntities.size();
    }

    /**
     * load entities of page
     * @param page number
     * @param listener of page loaded
     * */
    private void loadPage(int page, final OnPaginationListener listener) {
        final OnPaginationListener onPaginationListener = listener;

        mLoading = true;
        mPaginationLoader.loadPage(page, new PaginationLoader.PaginationLoaderListener<T>() {
            @Override
            public void onPageLoaded(int pageLoaded, List<T> entities) {
                addPage(pageLoaded, entities, onPaginationListener);
            }

            @Override
            public void onPageLoadFailed(int pageLoaded) {
                addPageFailed(pageLoaded, onPaginationListener);
            }
        });
    }

    /**
     * reload entities of page start (first page)
     * @param listener of the first page loaded
     * */
    void reload(OnPaginationListener listener) {
        mPage = mPageStart - 1;
        mHasMore = true;
        mEntities = new ArrayList<>();
        loadPage(mPageStart, listener);
    }

    /**
     * load entities of next page
     * @param listener of the next page loaded
     * */
    void loadNextPage(OnPaginationListener listener) {
        int nextPage = mPage + 1;
        loadPage(nextPage, listener);
    }

    private void addPage(int page, List<T> entities, OnPaginationListener listener) {
        int insertEntitiesAtStart = mEntities.size();
        int insertEntitiesCount = entities.size();
        mLoading = false;
        mEntities.addAll(entities);
        mPage = page;
        mHasMore = entities.size() < mPageSize;
        if (null != listener) {
            listener.onPageLoad(page, insertEntitiesAtStart, insertEntitiesCount);
        }
    }

    private void addPageFailed(int page, OnPaginationListener listener) {
        mLoading = false;
        mHasMore = true;
        if (null != listener) {
            listener.onPageFailed(page);
        }
    }

    interface OnPaginationListener {
        void onPageLoad(int page, int start, int count);
        void onPageFailed(int page);
    }
}
