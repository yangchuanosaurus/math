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
        // load more & load more retry have embed inside the ViewHolderFactory
        // register a customize entity view holder
        register(PhotoViewHolder.VIEW_TYPE, R.layout.view_photo, PhotoViewHolder::new);
    }

    @NonNull
    @Override
    public PaginationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // createViewHolder ViewHolderFactory to manage multiple view holders
        return createViewHolder(this, viewType, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PaginationViewHolder holder, int position) {
        if (PhotoViewHolder.VIEW_TYPE == holder.getViewType()) {
            String photo = getItem(position);
            ((PhotoViewHolder) holder).updatePhoto(photo);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = getItemLoadMoreViewType(position);
        if (isLoadMoreViewType(viewType)) {
            // return a load more style view type
            return viewType;
        }
        return PhotoViewHolder.VIEW_TYPE;
    }

    static class PhotoViewHolder extends PaginationViewHolder {
        private TextView mTvPhoto;
        static final int VIEW_TYPE = 1;

        PhotoViewHolder(@NonNull View itemView) {
            super(VIEW_TYPE, itemView);
            mTvPhoto = itemView.findViewById(R.id.tv_photo);

            itemView.setOnClickListener(v -> onItemActionClick(0));
        }

        void updatePhoto(String photo) {
            mTvPhoto.setText(photo);
        }
    }
}
