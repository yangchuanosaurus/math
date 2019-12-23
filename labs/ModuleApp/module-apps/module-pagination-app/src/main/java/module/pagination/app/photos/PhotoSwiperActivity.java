package module.pagination.app.photos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationRecyclerView;
import app.module.pagination.PaginationTrackingListener;
import arch.lifecycle.model.ArchLifecycleModel;
import module.pagination.app.PhotoListPaginationFactory;
import module.pagination.app.R;

import static app.module.pagination.PaginationFactory.addPaginationIntent;

public class PhotoSwiperActivity extends AppCompatActivity implements PaginationTrackingListener {

    private Pagination<String> mPagination;
    private PaginationRecyclerView mPaginationRecyclerView;
    private View mViewLoadingPhotos;

    public static void startFromList(@NonNull Fragment fragment, int itemPosition, Pagination<String> pagination) {
        Intent intent = new Intent(fragment.getContext(), PhotoSwiperActivity.class);
        addPaginationIntent(intent, itemPosition, pagination);
        fragment.startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_swiper);

        mViewLoadingPhotos = findViewById(R.id.view_photo_loading);

        mPaginationRecyclerView = findViewById(R.id.recycler_view);
        mPaginationRecyclerView.setHasFixedSize(true);

        mPagination = PhotoListPaginationFactory.create().bindPagination(this,
                getIntent(), mPaginationRecyclerView);
        mPagination.addTrackingListener(this);

        showLoadingView(false);
    }

    @Override
    public void onBackPressed() {
        ArchLifecycleModel.save("PhotosPaginationLifecycle", mPagination);
        Intent intent = new Intent();
        addPaginationIntent(intent, 0, mPagination);
        setResult(RESULT_OK, intent);
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
