package module.pagination.app;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationAdapter;
import app.module.pagination.PaginationViewHolder;

public class PhotoListAdapter extends PaginationAdapter<String> {

    public PhotoListAdapter(Pagination<String> pagination) {
        super(pagination);
    }

    @NonNull
    @Override
    public PaginationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (LoadMoreViewHolder.VIEW_TYPE == viewType) {
            View view = createView(parent, R.layout.view_load_more);
            return new LoadMoreViewHolder(view);
        } else if (PhotoViewHolder.VIEW_TYPE == viewType) {
            View view = createView(parent, R.layout.view_photo);
            return new PhotoViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PaginationViewHolder holder, int position) {
        if (LoadMoreViewHolder.VIEW_TYPE == holder.getViewType()) {

        } else if (PhotoViewHolder.VIEW_TYPE == holder.getViewType()) {
            String photo = getItem(position);
            ((PhotoViewHolder) holder).updatePhoto(photo);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMoreEnabled()) {
            if (position == getItemCount() - 1) {
                return LoadMoreViewHolder.VIEW_TYPE;
            }
        }
        return PhotoViewHolder.VIEW_TYPE;
    }

    static class PhotoViewHolder extends PaginationViewHolder {
        private TextView mTvPhoto;
        static final int VIEW_TYPE = 1;

        PhotoViewHolder(@NonNull View itemView) {
            super(VIEW_TYPE, itemView);
            mTvPhoto = itemView.findViewById(R.id.tv_photo);
        }

        void updatePhoto(String photo) {
            mTvPhoto.setText(photo);
        }
    }

    static class LoadMoreViewHolder extends PaginationViewHolder {
        final static int VIEW_TYPE = Integer.MAX_VALUE;
        public LoadMoreViewHolder(@NonNull View itemView) {
            super(VIEW_TYPE, itemView);
        }
    }
}
