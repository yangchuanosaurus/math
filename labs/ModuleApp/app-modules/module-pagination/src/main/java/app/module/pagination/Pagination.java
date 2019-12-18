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
        mTrackingListeners.remove(listener);
    }

    public boolean isStartPage(int page) {
        return page == mPageStart;
    }

    int getEntitiesCount() {
        return mEntities.size();
    }

    T getEntityAtPosition(int pos) {
        return mEntities.get(pos);
    }

    /**
     * load entities of page
     * @param page number
     * @param listener of page loaded
     * */
    private void loadPage(int page, PaginationListener listener) {
        // Prompt call multi times via mLoading
        if (mLoading) return;

        PaginationLog.d("Pagination loadPage " + page);
        final PaginationListener onPaginationListener = listener;

        mLoading = true;
        mPaginationLoader.loadPage(page, mPageSize, new PaginationLoader.PaginationLoaderListener<T>() {
            @Override
            public void onPageLoaded(int pageLoaded, List<T> entities) {
                PaginationLog.d("Pagination onPageLoaded " + pageLoaded);
                addPage(pageLoaded, entities, onPaginationListener);
            }

            @Override
            public void onPageLoadFailed(int pageLoaded) {
                PaginationLog.d("Pagination onPageLoadFailed " + pageLoaded);
                addPageFailed(pageLoaded, onPaginationListener);
            }
        });
    }

    /**
     * reload entities of page start (first page)
     * @param listener of the first page loaded
     * */
    void reload(PaginationListener listener) {
        PaginationLog.d("Pagination reload");
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
        if (mHasMore) {
            PaginationLog.d("Pagination loadNextPage");
            int nextPage = mPage + 1;
            loadPage(nextPage, listener);
        }
    }

    private void addPage(int page, List<T> entities, PaginationListener listener) {
        int insertEntitiesAtStart = 0;
        int insertEntitiesCount = 0;

        if (null == entities || entities.isEmpty()) {
            mHasMore = false;
        } else {
            insertEntitiesAtStart = mEntities.size();
            insertEntitiesCount = entities.size();

            mEntities.addAll(entities);
            mPage = page;

            mHasMore = entities.size() == mPageSize;
        }

        mLoading = false;

        PaginationLog.d("Pagination addPage hasMore=" + mHasMore + ", at page=" + page);

        if (null != listener) {
            listener.onPageLoaded(page, insertEntitiesAtStart, insertEntitiesCount);
        }

        notifyTrackingListeners(page, insertEntitiesCount, true);
    }

    private void notifyTrackingListeners(int page, final int insertEntitiesCount, boolean success) {
        for (PaginationTrackingListener trackingListener : mTrackingListeners) {
            trackingListener.onPaginationLoaded(page, insertEntitiesCount, success, false);
        }
    }

    private void addPageFailed(int page, PaginationListener listener) {
        mLoading = false;
        mHasMore = true;

        PaginationLog.d("Pagination load " + page + " failed from page=" + mPage);

        if (null != listener) {
            listener.onPageFailed(page);
        }

        notifyTrackingListeners(page, 0, false);
    }

    boolean hasMoreData() {
        return mHasMore;
    }

    interface PaginationListener {
        void onPageLoaded(int page, int start, int count);
        void onPageFailed(int page);
    }
}
