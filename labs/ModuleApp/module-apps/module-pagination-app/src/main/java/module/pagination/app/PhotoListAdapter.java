package module.pagination.app;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationAdapter;

public class PhotoListAdapter extends PaginationAdapter<String, PhotoListAdapter.PhotoViewHolder> {

    public PhotoListAdapter(Pagination<String> pagination) {
        super(pagination);
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {

    }


    static class PhotoViewHolder extends RecyclerView.ViewHolder {

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
