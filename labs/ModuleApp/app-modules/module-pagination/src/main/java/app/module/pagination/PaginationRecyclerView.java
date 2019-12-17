package app.module.pagination;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class PaginationRecyclerView extends RecyclerView implements PaginationTrackingListener {

    private Pagination mPagination;

    public PaginationRecyclerView(@NonNull Context context) {
        super(context);
    }

    public PaginationRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PaginationRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPagination(@NonNull Pagination pagination) {
        mPagination = pagination;
    }

    @Override
    public void onPagination(int page, int count, boolean success, boolean retry) {
        PaginationLog.d("PaginationRecyclerView onPagination page="
                + page + ", success=" + success + ", count=" + count);

        boolean isStartPage = mPagination.isStartPage(page);
        if (isStartPage) {
            if (success && count == 0) {
                // todo show empty results
            } else if (!success) {
                // todo show error message and retry button
            }
        } else if (!success) {
            // todo adapter show load more failed and retry button inside
        }
    }
}
