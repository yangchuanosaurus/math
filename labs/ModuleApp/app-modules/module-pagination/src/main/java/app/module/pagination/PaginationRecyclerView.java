package app.module.pagination;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class PaginationRecyclerView extends RecyclerView {

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

    public void setPagination(Pagination pagination) {
        mPagination = pagination;
    }
}
