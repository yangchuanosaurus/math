package module.pagination.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_photo, null);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        String photo = getItem(position);
        holder.updatePhoto(photo);
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvPhoto;
        
        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvPhoto = itemView.findViewById(R.id.tv_photo);
        }

        void updatePhoto(String photo) {
            mTvPhoto.setText(photo);
        }
    }
}
