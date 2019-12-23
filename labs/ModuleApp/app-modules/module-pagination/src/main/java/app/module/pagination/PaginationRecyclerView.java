package app.module.pagination;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * PaginationRecyclerView contains the
 * injected {@link Pagination} and the biz {@link PaginationAdapter}
 * */
public class PaginationRecyclerView extends RecyclerView implements PaginationTrackingListener {

    private LoadMoreListener mLoadMoreListener;
    private LoadMoreRetryListener mLoadMoreRetryListener; // Embed load more retry click behavior

    private PaginationAdapter mPaginationAdapter;
    private EndlessRecyclerViewScrollListener mEndlessScrollListener;

    private ItemClickListener mItemClickListener;

    private int mVerticalScrollOffset;

    public PaginationRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public PaginationRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaginationRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLoadMoreRetryListener = () -> onActionLoadMoreRetry();
    }

    public void reload() {
        if (null != mPaginationAdapter) {
            mEndlessScrollListener.resetState();
            post(() -> mPaginationAdapter.reload());
        }
    }

    public void loadNextPage() {
        if (null != mPaginationAdapter) {
            post(() -> mPaginationAdapter.loadNextPage());
        }
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(layout);

        if (null != layout) {
            // scroll listener of recycler view will handle the load more behavior
            mEndlessScrollListener = createScrollListener(layout);
            if (null != mEndlessScrollListener) {
                addOnScrollListener(mEndlessScrollListener);
            }
            addOnScrollListener(new OnScrollListener() {
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    mVerticalScrollOffset += dy;
                }
            });
        }
    }

    public int getVerticalOffset() {
        return computeVerticalScrollOffset();
    }

    @NonNull
    public Adapter getAdapter() {
        return mPaginationAdapter;
    }

    public void setAdapter(@NonNull PaginationAdapter adapter) {
        super.setAdapter(adapter);
        mPaginationAdapter = adapter;
        Pagination pagination = adapter.getPagination();
        pagination.addTrackingListener(this);
        adapter.setOnPaginationListener(createOnPaginationListener());

        mPaginationAdapter.setLoadMoreRetryListener(mLoadMoreRetryListener);
        bindAdapterWithItemListener();
    }

    public void setLoadMoreListener(LoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    public <T> void setOnItemClickListener(ItemClickListener<T> listener) {
        mItemClickListener = listener;
        bindAdapterWithItemListener();
    }

    private void bindAdapterWithItemListener() {
        if (null != mPaginationAdapter && null != mItemClickListener) {
            mPaginationAdapter.setItemClickListener(mItemClickListener);
        }
    }

    private EndlessRecyclerViewScrollListener createScrollListener(@NonNull LayoutManager layout) {
        if (layout instanceof GridLayoutManager) {
            return new EndlessRecyclerViewScrollListener((GridLayoutManager) layout) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    onReachEndless(page, totalItemsCount);
                }
            };
        } else if (layout instanceof StaggeredGridLayoutManager) {
            return new EndlessRecyclerViewScrollListener((StaggeredGridLayoutManager) layout) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    onReachEndless(page, totalItemsCount);
                }
            };
        } else if (layout instanceof LinearLayoutManager) {
            return new EndlessRecyclerViewScrollListener((LinearLayoutManager) layout) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    onReachEndless(page, totalItemsCount);
                }
            };
        }
        return null;
    }

    private void onReachEndless(int page, int totalItemsCount) {
        PaginationLog.d("onLoadMoreStart page=" + page + ", totalItemsCount=" + totalItemsCount);
        onActionLoadMoreRetry();
    }

    private void onActionLoadMoreRetry() {
        if (null != mLoadMoreListener) {
            // show the 'load more' view holder
            mPaginationAdapter.addLoadMore(this);
            mLoadMoreListener.onLoadMoreStart();
        }
    }

    @Override
    public void onPaginationLoaded(int page, int count, boolean success, boolean retry) {
        PaginationLog.d("PaginationRecyclerView onPaginationLoaded page="
                + page + ", success=" + success + ", count=" + count);
    }

    private Pagination.PaginationListener createOnPaginationListener() {
        return new Pagination.PaginationListener() {
            @Override
            public void onPageLoaded(int page, int start, int count) {
                // corresponding, remove the 'load more' view holder when load page finished
                mPaginationAdapter.removeLoadMore(PaginationRecyclerView.this);
                onPaginationLoaded(start, count);
            }

            @Override
            public void onPageFailed(int page) {
                // corresponding, replace the 'load more' view holder with the 'load more retry' view holder
                mPaginationAdapter.addLoadMoreRetry(PaginationRecyclerView.this);
            }
        };
    }

    private void onPaginationLoaded(final int start, final int count) {
        PaginationLog.d("onPaginationLoaded start=" + start + ", count=" + count);
        post(() -> mPaginationAdapter.notifyItemRangeInserted(start, count));
    }

    public boolean isLoadMorePosition(int position) {
        return mPaginationAdapter.isLoadMoreEnabled()
                && position == mPaginationAdapter.getItemCount() - 1;
    }

    public interface LoadMoreListener {
        void onLoadMoreStart();
    }
}
