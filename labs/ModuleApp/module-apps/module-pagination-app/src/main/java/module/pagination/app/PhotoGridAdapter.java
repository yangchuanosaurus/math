package module.pagination.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationAdapter;
import app.module.pagination.PaginationViewHolder;
import app.module.pagination.ViewHolderFactory;

/**
 * Created by Albert Zhao on 2019-12-19.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class PhotoGridAdapter extends PaginationAdapter<String> {

    private final int mGridSize;

    public PhotoGridAdapter(Context context, int columnCount, Pagination<String> pagination) {
        super(pagination);
        // load more & load more retry have embed inside the ViewHolderFactory
        // register a customize entity view holder
        ViewHolderFactory.getDefault()
                .register(PhotoGridViewHolder.VIEW_TYPE, R.layout.view_photo, PhotoGridViewHolder::new);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mGridSize = wm.getDefaultDisplay().getWidth() / columnCount;
    }

    @NonNull
    @Override
    public PaginationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create ViewHolderFactory to manage multiple view holders
        if (PhotoGridViewHolder.VIEW_TYPE == viewType) {
            return ViewHolderFactory.getDefault().createGrid(viewType, mGridSize, parent);
        } else {
            return ViewHolderFactory.getDefault().create(this, viewType, parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PaginationViewHolder holder, int position) {
        if (PhotoListAdapter.PhotoViewHolder.VIEW_TYPE == holder.getViewType()) {
            String photo = getItem(position);
            ((PhotoGridViewHolder) holder).updatePhoto(photo);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = getItemLoadMoreViewType(position);
        if (isLoadMoreViewType(viewType)) {
            // return a load more style view type
            return viewType;
        }
        return PhotoListAdapter.PhotoViewHolder.VIEW_TYPE;
    }

    static class PhotoGridViewHolder extends PaginationViewHolder {
        private TextView mTvPhoto;
        static final int VIEW_TYPE = 1;

        PhotoGridViewHolder(@NonNull View itemView) {
            super(VIEW_TYPE, itemView);
            mTvPhoto = itemView.findViewById(R.id.tv_photo);
        }

        void updatePhoto(String photo) {
            mTvPhoto.setText(photo);
        }
    }

}
