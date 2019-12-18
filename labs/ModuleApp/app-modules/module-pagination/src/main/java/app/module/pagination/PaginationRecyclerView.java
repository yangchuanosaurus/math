package app.module.pagination;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class PaginationRecyclerView extends RecyclerView implements PaginationTrackingListener {

    private Pagination mPagination;

    private LoadMoreListener mLoadMoreListener;

    private PaginationAdapter mPaginationAdapter;

    public PaginationRecyclerView(@NonNull Context context) {
        super(context);
    }

    public PaginationRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaginationRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void reload() {
        if (null != mPaginationAdapter) {
            post(new Runnable() {
                @Override
                public void run() {
                    mPaginationAdapter.reload();
                }
            });
        }
    }

    public void loadNextPage() {
        if (null != mPaginationAdapter) {
            post(new Runnable() {
                @Override
                public void run() {
                    mPaginationAdapter.loadNextPage();
                }
            });
        }
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(layout);

        if (null != layout) {
            // scroll listener of recycler view will handle the load more behavior
            EndlessRecyclerViewScrollListener scrollListener = createScrollListener(layout);
            if (null != scrollListener) {
                addOnScrollListener(scrollListener);
            }
        }
    }

    public void setAdapter(@NonNull PaginationAdapter adapter) {
        super.setAdapter(adapter);
        mPaginationAdapter = adapter;
        mPagination = adapter.getPagination();
        adapter.setOnPaginationListener(createOnPaginationListener());
    }

    public void setLoadMoreListener(LoadMoreListener listener) {
        mLoadMoreListener = listener;
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
        PaginationLog.d("onLoadMore page=" + page + ", totalItemsCount=" + totalItemsCount);
        if (null != mLoadMoreListener) {
            // show the 'load more' view holder
            mPaginationAdapter.addLoadMore(this);
            mLoadMoreListener.onLoadMore();
        }
    }

    @Override
    public void onPagination(int page, int count, boolean success, boolean retry) {
        PaginationLog.d("PaginationRecyclerView onPagination page="
                + page + ", success=" + success + ", count=" + count);

        boolean isStartPage = mPagination.isStartPage(page);
        if (!isStartPage) {
            if (!success) {
                // todo adapter show load more failed and retry button inside
            }
        }
    }

    private Pagination.PaginationListener createOnPaginationListener() {
        return new Pagination.PaginationListener() {
            @Override
            public void onPageLoaded(int page, int start, int count) {
                // corresponding, remove the 'load more' view holder when load page finished
                mPaginationAdapter.removeLoadMore(PaginationRecyclerView.this);
                onPaginationLoaded(page, start, count);
            }

            @Override
            public void onPageFailed(int page) {
                // corresponding, remove the 'load more' view holder when load page finished
                mPaginationAdapter.removeLoadMore(PaginationRecyclerView.this);
                onPageLoadFailed(page);
            }
        };
    }

    private void onPaginationLoaded(int page, final int start, final int count) {
        PaginationLog.d("onPaginationLoaded start=" + start + ", count=" + count);
        post(new Runnable() {
            @Override
            public void run() {
                mPaginationAdapter.notifyItemRangeInserted(start, count);
            }
        });
    }

    private void onPageLoadFailed(int page) {
        if (mPagination.isStartPage(page)) {
            // todo the first page load failed
        } else {
            // todo the other pages load failed
        }
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}
