package app.module.pagination;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationAdapter<E> extends RecyclerView.Adapter<PaginationViewHolder> {

    private Pagination<E> mPagination;
    private Pagination.PaginationListener mPaginationListener;

    private boolean mEnableLoadMore;

    public PaginationAdapter(Pagination<E> pagination) {
        mPagination = pagination;
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

    protected View createView(@NonNull ViewGroup parent, @LayoutRes int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    // show the 'load more' view holder
    void addLoadMore(@NonNull View view) {
        if (mPagination.hasMoreData()) {
            PaginationLog.d("show the 'load more' view holder");
            if (!mEnableLoadMore) {
                mEnableLoadMore = true;
                postRunnable(view, new Runnable() {
                    @Override
                    public void run() {
                        notifyItemInserted(mPagination.getEntitiesCount());
                    }
                });
            }
        }
    }

    // hide the 'load more' view holder
    void removeLoadMore(@NonNull View view) {
        PaginationLog.d("remove the 'load more' view holder");
        if (mEnableLoadMore) {
            postRunnable(view, new Runnable() {
                @Override
                public void run() {
                    notifyItemRemoved(mPagination.getEntitiesCount());
                }
            });
        }
        mEnableLoadMore = false;

    }

    protected void postRunnable(@NonNull View view, Runnable runnable) {
        view.post(runnable);
    }

    protected boolean isLoadMoreEnabled() {
        return mEnableLoadMore;
    }

}
