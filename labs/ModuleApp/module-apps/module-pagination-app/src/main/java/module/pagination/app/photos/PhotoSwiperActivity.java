package module.pagination.app.photos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationLog;
import app.module.pagination.PaginationRecyclerView;
import app.module.pagination.PaginationTrackingListener;
import arch.lifecycle.model.ArchLifecycleModel;
import module.pagination.app.PhotoGridAdapter;
import module.pagination.app.PhotoPaginationLoader;
import module.pagination.app.R;

public class PhotoSwiperActivity extends AppCompatActivity implements PaginationTrackingListener {

    private static final String INTENT_ITEM_POSITION = "photos.photo.position";
    private static final String INTENT_PAGINATION = "photos.pagination";

    private Pagination<String> mPagination;
    private PaginationRecyclerView mPaginationRecyclerView;
    private View mViewLoadingPhotos;

    private static final int GRID_COLUMN_COUNT = 3;
    public static final int PAGE_SIZE = 25;

    public static void startFromList(@NonNull Context context, int itemPosition, Pagination<String> pagination) {
        Intent intent = new Intent(context, PhotoSwiperActivity.class);
        intent.putExtra(INTENT_ITEM_POSITION, itemPosition);
        intent.putExtra(INTENT_PAGINATION, pagination);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_swiper);

        mPagination = ArchLifecycleModel.get("PhotosPaginationLifecycle", null);

        if (null != getIntent()) {
            int itemPosition = getIntent().getExtras().getInt(INTENT_ITEM_POSITION);
            mPagination = getPagination(getIntent());
            mPagination.addTrackingListener(this);
            PaginationLog.d("Restore itemPosition=" + itemPosition + ", pagination=" + mPagination);
        }

        mViewLoadingPhotos = findViewById(R.id.view_photo_loading);

        mPaginationRecyclerView = findViewById(R.id.recycler_view);
        mPaginationRecyclerView.setHasFixedSize(true);

        initPaginationRecyclerView();
    }

    private void initPaginationRecyclerView() {
        // created recycler view adapter for load page
        PhotoGridAdapter photoGridAdapter = createPhotoGridAdapter(mPagination);

        GridLayoutManager layoutManager = new GridLayoutManager(this, GRID_COLUMN_COUNT, RecyclerView.VERTICAL, false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mPaginationRecyclerView.isLoadMorePosition(position)) {
                    return GRID_COLUMN_COUNT;
                } else {
                    return 1;
                }
            }
        });

        mPaginationRecyclerView.setLayoutManager(layoutManager);
        mPaginationRecyclerView.setLoadMoreListener(() -> mPaginationRecyclerView.loadNextPage());

        mPaginationRecyclerView.setAdapter(photoGridAdapter);
    }

    private PhotoGridAdapter createPhotoGridAdapter(@NonNull Pagination<String> pagination) {
        return new PhotoGridAdapter(this, GRID_COLUMN_COUNT, pagination);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private Pagination<String> getPagination(@NonNull Intent intent) {
        if (null != getIntent().getExtras()) {
            return (Pagination<String>) getIntent().getExtras().getSerializable(INTENT_PAGINATION);
        }
        return null;
    }

    private void backToPhotoList() {
        ArchLifecycleModel.save("PhotosPaginationLifecycle", mPagination);
        finish();
    }

    @Override
    public void onPaginationLoaded(int page, int count, boolean success, boolean retry) {
        showLoadingView(false);
    }

    private void showLoadingView(boolean show) {
        mViewLoadingPhotos.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
