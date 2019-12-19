package app.module.pagination;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import leakcanary.AppWatcher;

public abstract class PaginationAdapter<E> extends RecyclerView.Adapter<PaginationViewHolder> {

    private Pagination<E> mPagination;
    private Pagination.PaginationListener mPaginationListener;

    private boolean mEnableLoadMore;
    private boolean mLoadMoreFailed;

    private LoadMoreRetryListener mLoadMoreRetryListener;

    public PaginationAdapter(Pagination<E> pagination) {
        mPagination = pagination;
        AppWatcher.INSTANCE.getObjectWatcher().watch(mPagination);
    }

    void reload() {
        mPagination.reload(mPaginationListener);
    }

    void loadNextPage() {
        mPagination.loadNextPage(mPaginationListener);
    }

    void setOnPaginationListener(Pagination.PaginationListener listener) {
        mPaginationListener = listener;
    }

    Pagination<E> getPagination() {
        return mPagination;
    }

    protected E getItem(int position) {
        return mPagination.getEntityAtPosition(position);
    }

    @Override
    public int getItemCount() {
        int count = mPagination.getEntitiesCount();
        if (mEnableLoadMore) count += 1;
        return count;
    }

    public int getItemLoadMoreViewType(int position) {
        if (isLoadMoreEnabled()) {
            if (position == getItemCount() - 1) {
                if (isLoadMoreFailed()) {
                    return ViewHolderFactory.LoadMoreRetryViewHolder.VIEW_TYPE;
                } else {
                    return ViewHolderFactory.LoadMoreViewHolder.VIEW_TYPE;
                }
            }
        }
        return Integer.MIN_VALUE;
    }

    public boolean isLoadMoreViewType(int viewType) {
        return viewType != Integer.MIN_VALUE;
    }

    void setLoadMoreRetryListener(LoadMoreRetryListener listener) {
        mLoadMoreRetryListener = listener;
    }

    public LoadMoreRetryListener getLoadMoreRetryListener() {
        return mLoadMoreRetryListener;
    }

    // show the 'load more' view holder
    void addLoadMore(@NonNull View view) {
        if (mPagination.hasMoreData()) {
            PaginationLog.d("show the 'load more' view holder");
            if (!mEnableLoadMore) {
                mEnableLoadMore = true;
                postRunnable(view, () -> notifyItemInserted(mPagination.getEntitiesCount()));
            } else {
                // load more -> failed -> load more retry -> load more
                if (mLoadMoreFailed) {
                    // if load more replace the retry, we should notify changed
                    mLoadMoreFailed = false;
                    postRunnable(view, () -> notifyItemChanged(mPagination.getEntitiesCount()));
                }
            }
        }
    }

    // remove the 'load more' view holder
    void addLoadMoreRetry(@NonNull View view) {
        mLoadMoreFailed = true;
        if (mEnableLoadMore && mPagination.hasMoreData()) {
            PaginationLog.d("show the 'load more retry' view holder");
            postRunnable(view, () -> notifyItemChanged(mPagination.getEntitiesCount()));
        }
    }

    // hide the 'load more' view holder
    void removeLoadMore(@NonNull View view) {
        PaginationLog.d("remove the 'load more' view holder");
        mLoadMoreFailed = false;
        if (mEnableLoadMore) {
            mEnableLoadMore = false;
            postRunnable(view, () -> notifyItemRemoved(mPagination.getEntitiesCount()));
        }
    }

    private void postRunnable(@NonNull View view, Runnable runnable) {
        view.post(runnable);
    }

    protected boolean isLoadMoreEnabled() {
        return mEnableLoadMore;
    }

    protected boolean isLoadMoreFailed() {
        return mLoadMoreFailed;
    }

    void destroy() {

    }
}
