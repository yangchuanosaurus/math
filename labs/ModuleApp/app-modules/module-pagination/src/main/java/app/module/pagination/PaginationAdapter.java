package app.module.pagination;

import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Pagination<E> mPagination;

    public PaginationAdapter(Pagination<E> pagination) {
        mPagination = pagination;
    }

    public void reload() {
        mPagination.reload(createOnPaginationListener());
    }

    public void loadNextPage() {
        mPagination.loadNextPage(createOnPaginationListener());
    }

    private Pagination.PaginationListener createOnPaginationListener() {
        return new Pagination.PaginationListener() {
            @Override
            public void onPageLoad(int page, int start, int count) {
                onPaginationLoaded(page, start, count);
            }

            @Override
            public void onPageFailed(int page) {
                onPageLoadFailed(page);
            }
        };
    }

    private void onPaginationLoaded(int page, int start, int count) {
        notifyItemRangeInserted(start, count);
    }

    private void onPageLoadFailed(int page) {
        if (mPagination.isStartPage(page)) {
            // todo the first page load failed
        } else {
            // todo the other pages load failed
        }
    }

    @Override
    public int getItemCount() {
        return mPagination.getEntitiesCount();
    }

}
