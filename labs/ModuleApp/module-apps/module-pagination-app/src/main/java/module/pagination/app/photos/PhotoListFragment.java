package module.pagination.app.photos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import app.module.pagination.ItemClickListener;
import app.module.pagination.Pagination;
import app.module.pagination.PaginationFactory;
import app.module.pagination.PaginationLog;
import app.module.pagination.PaginationRecyclerView;
import app.module.pagination.PaginationTrackingListener;
import arch.lifecycle.model.ArchLifecycleModel;
import module.pagination.app.PhotoGridPaginationFactory;
import module.pagination.app.R;

import static android.app.Activity.RESULT_OK;

public class PhotoListFragment extends Fragment implements PaginationTrackingListener {

    private PaginationRecyclerView mPaginationRecyclerView;
    private Pagination<String> mPhotoGridPagination;
    private View mViewLoadingPhotos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaginationLog.d("PhotoListFragment onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PaginationLog.d("PhotoListFragment onCreateView");
        return inflater.inflate(R.layout.fragment_photo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mViewLoadingPhotos = view.findViewById(R.id.view_photo_loading);

        mPaginationRecyclerView = view.findViewById(R.id.recycler_view);
        mPaginationRecyclerView.setHasFixedSize(true);
        bindRecycleView(null);

        PaginationLog.d("PhotoListFragment onViewCreated");

        reloadPhotos();
    }

    private void bindRecycleView(Intent intent) {
        // if activity destory-ed and restart, the pagination intent will be lose
        if (null == mPhotoGridPagination) {
            mPhotoGridPagination = PhotoGridPaginationFactory.create()
                    .bindPagination(getContext(), intent, mPaginationRecyclerView);

            mPaginationRecyclerView.setOnItemClickListener(new ItemClickListener<String>() {
                @Override
                public void onItemActionClick(int itemPosition, String item, int actionId) {
                    PaginationLog.d("On item clicked: " + item);
                    gotoPhotoSwiper(itemPosition);
                }
            });

            mPhotoGridPagination.addTrackingListener(this);
        } else {
            if (null != intent) {
                Pagination<String> intentPagination = PaginationFactory.getIntentPagination(intent);
                if (null != intentPagination) {
                    // merge intent pagination
                    mPhotoGridPagination.merge(intentPagination);
                    mPaginationRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    private void gotoPhotoSwiper(int itemPosition) {
        PaginationLog.d("gotoPhotoSwiper: " + mPhotoGridPagination);
        PhotoSwiperActivity.startFromList(this, itemPosition, mPhotoGridPagination);
    }

    @Override
    public void onStart() {
        super.onStart();
        // todo we should reload photos when view created instead of onStart for restore state when View not destroyed

        PaginationLog.d("PhotoListFragment onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PaginationLog.d("PhotoListFragment onDestroy");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (0 == requestCode && RESULT_OK == resultCode) {
            // update pagination
            bindRecycleView(intent);
        }
    }

    // reload the first page
    private void reloadPhotos() {
        mPaginationRecyclerView.reload();
        showLoadingView(true);
    }

    private void showLoadingView(boolean show) {
        mViewLoadingPhotos.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPaginationLoaded(int page, int count, boolean success, boolean retry) {
        showLoadingView(false);
    }
}
