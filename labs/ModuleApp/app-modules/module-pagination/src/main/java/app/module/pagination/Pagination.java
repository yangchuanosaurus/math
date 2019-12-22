package app.module.pagination;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import arch.lifecycle.model.LifecycleModel;

public class Pagination<T> implements Serializable, LifecycleModel<Pagination<T>> {
    private final int mPageSize;
    private final int mPageStart;
    private int mPage;
    private List<T> mEntities;
    private boolean mHasMore;

    private PaginationLoader<T> mPaginationLoader;
    private boolean mLoading;

    private List<PaginationTrackingListener> mTrackingListeners;

    private Pagination(int pageSize, int pageStart) {
        mPageSize = pageSize;
        mPageStart = pageStart;
        mPage = pageStart - 1;
        mHasMore = true;
        mLoading = false;

        mEntities = new ArrayList<>();
        mTrackingListeners = new ArrayList<>();
    }

    public Pagination(int pageSize, int pageStart, @NonNull PaginationLoader<T> paginationLoader) {
        this(pageSize, pageStart);

        setPaginationLoader(paginationLoader);
    }

    private void setPaginationLoader(@NonNull PaginationLoader<T> paginationLoader) {
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

    @Override
    public Pagination<T> lifecycleClone() {
        Pagination<T> pagination = new Pagination<>(mPageSize, mPageStart);
        pagination.mHasMore = this.mHasMore;
        pagination.mPage = this.mPage;
        pagination.mEntities = new ArrayList<>(this.mEntities);
        pagination.mPaginationLoader = this.mPaginationLoader;

        return pagination;
    }

    interface PaginationListener {
        void onPageLoaded(int page, int start, int count);
        void onPageFailed(int page);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pagination<?> that = (Pagination<?>) o;

        if (mPageSize != that.mPageSize) return false;
        if (mPageStart != that.mPageStart) return false;
        if (mPage != that.mPage) return false;
        if (mHasMore != that.mHasMore) return false;
        if (mLoading != that.mLoading) return false;
        return mEntities != null ? mEntities.equals(that.mEntities) : that.mEntities == null;
    }

    @Override
    public int hashCode() {
        int result = mPageSize;
        result = 31 * result + mPageStart;
        result = 31 * result + mPage;
        result = 31 * result + (mEntities != null ? mEntities.hashCode() : 0);
        result = 31 * result + (mHasMore ? 1 : 0);
        result = 31 * result + (mLoading ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "mPageSize=" + mPageSize +
                ", mPageStart=" + mPageStart +
                ", mPage=" + mPage +
                ", mEntities=" + mEntities +
                ", mHasMore=" + mHasMore +
                ", mPaginationLoader=" + mPaginationLoader +
                ", mLoading=" + mLoading +
                ", mTrackingListeners=" + mTrackingListeners +
                '}';
    }
}
