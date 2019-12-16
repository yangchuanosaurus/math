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

    private List<PaginationTrackingListener> mTrackingListeners;

    public Pagination(int pageSize, int pageStart, @NonNull PaginationLoader<T> paginationLoader) {
        mPageSize = pageSize;
        mPageStart = pageStart;
        mPage = pageStart - 1;
        mHasMore = true;
        mLoading = false;

        mEntities = new ArrayList<>();
        mTrackingListeners = new ArrayList<>();

        mPaginationLoader = paginationLoader;
    }

    public void addTrackingListener(@NonNull PaginationTrackingListener listener) {
        mTrackingListeners.add(listener);
    }

    public void removeTrackingListener(@NonNull PaginationTrackingListener listener) {
        if (mTrackingListeners.contains(listener)) {
            mTrackingListeners.remove(listener);
        }
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
    private void loadPage(int page, final PaginationListener listener) {
        final PaginationListener onPaginationListener = listener;

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
    void reload(PaginationListener listener) {
        mPage = mPageStart - 1;
        mHasMore = true;
        mEntities = new ArrayList<>();
        loadPage(mPageStart, listener);
    }

    /**
     * load entities of next page
     * @param listener of the next page loaded
     * */
    void loadNextPage(PaginationListener listener) {
        int nextPage = mPage + 1;
        loadPage(nextPage, listener);
    }

    private void addPage(int page, List<T> entities, PaginationListener listener) {
        int insertEntitiesAtStart = mEntities.size();
        int insertEntitiesCount = entities.size();
        mLoading = false;
        mEntities.addAll(entities);
        mPage = page;
        mHasMore = entities.size() < mPageSize;
        if (null != listener) {
            listener.onPageLoad(page, insertEntitiesAtStart, insertEntitiesCount);
        }
        for (PaginationTrackingListener trackingListener : mTrackingListeners) {
            trackingListener.onPagination(page, true, false);
        }
    }

    private void addPageFailed(int page, PaginationListener listener) {
        mLoading = false;
        mHasMore = true;
        if (null != listener) {
            listener.onPageFailed(page);
        }
        for (PaginationTrackingListener trackingListener : mTrackingListeners) {
            trackingListener.onPagination(page, false, true);
        }
    }

    interface PaginationListener {
        void onPageLoad(int page, int start, int count);
        void onPageFailed(int page);
    }
}
