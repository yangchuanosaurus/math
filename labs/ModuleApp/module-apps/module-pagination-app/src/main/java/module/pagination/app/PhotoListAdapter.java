package module.pagination.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import java.util.HashMap;
import java.util.Map;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationAdapter;
import app.module.pagination.PaginationViewHolder;
import app.module.pagination.ViewHolderBuilder;

public class PhotoListAdapter extends PaginationAdapter<String> {

    public PhotoListAdapter(Pagination<String> pagination) {
        super(pagination);
    }

    @NonNull
    @Override
    public PaginationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create ViewHolderFactory to manage multiple view holders
        return ViewHolderFactory.create(viewType, parent);
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
                if (isLoadMoreFailed()) {
                    return LoadMoreRetryViewHolder.VIEW_TYPE;
                } else {
                    return LoadMoreViewHolder.VIEW_TYPE;
                }
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
        final static int VIEW_TYPE = 2;
        public LoadMoreViewHolder(@NonNull View itemView) {
            super(VIEW_TYPE, itemView);
        }
    }

    static class LoadMoreRetryViewHolder extends PaginationViewHolder {
        static final int VIEW_TYPE = 3;

        public LoadMoreRetryViewHolder(@NonNull View itemView) {
            super(VIEW_TYPE, itemView);
        }
    }

    static class ViewHolderFactory {
        static Map<Integer, ViewHolderBuilder<View, PaginationViewHolder>> mRegistry;
        static Map<Integer, Integer> mRegistryLayout;

        static {
            mRegistry = new ArrayMap<>();
            mRegistryLayout = new ArrayMap<>();

            mRegistry.put(PhotoViewHolder.VIEW_TYPE, PhotoViewHolder::new);
            mRegistryLayout.put(PhotoViewHolder.VIEW_TYPE, R.layout.view_photo);

            mRegistry.put(LoadMoreViewHolder.VIEW_TYPE, LoadMoreViewHolder::new);
            mRegistryLayout.put(LoadMoreViewHolder.VIEW_TYPE, R.layout.view_load_more);

            mRegistry.put(LoadMoreRetryViewHolder.VIEW_TYPE, LoadMoreRetryViewHolder::new);
            mRegistryLayout.put(LoadMoreRetryViewHolder.VIEW_TYPE, R.layout.view_load_more_retry);
        }

        @NonNull
        static PaginationViewHolder create(int viewType, @NonNull ViewGroup parent) {
            int layout = mRegistryLayout.get(viewType);
            View view = createView(parent, layout);
            return mRegistry.get(viewType).create(view);
        }

        private static View createView(@NonNull ViewGroup parent, @LayoutRes int layout) {
            return LayoutInflater.from(parent.getContext())
                    .inflate(layout, parent, false);
        }
    }
}
