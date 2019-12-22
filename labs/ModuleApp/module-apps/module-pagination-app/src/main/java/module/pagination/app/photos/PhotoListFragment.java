package module.pagination.app.photos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.module.pagination.ItemClickListener;
import app.module.pagination.Pagination;
import app.module.pagination.PaginationLog;
import app.module.pagination.PaginationRecyclerView;
import app.module.pagination.PaginationTrackingListener;
import arch.lifecycle.model.ArchLifecycleModel;
import module.pagination.app.PhotoGridAdapter;
import module.pagination.app.PhotoPaginationLoader;
import module.pagination.app.R;

public class PhotoListFragment extends Fragment implements PaginationTrackingListener {

    private PaginationRecyclerView mPaginationRecyclerView;
    private Pagination<String> mPhotoGridPagination;
    private View mViewLoadingPhotos;

    private static final int GRID_COLUMN_COUNT = 3;
    public static final int PAGE_SIZE = 25;

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

        initPaginationRecyclerView();
        mPaginationRecyclerView.setOnItemClickListener(new ItemClickListener<String>() {
            @Override
            public void onItemActionClick(int itemPosition, String item, int actionId) {
                PaginationLog.d("On item clicked: " + item);
                gotoPhotoSwiper(itemPosition, item);
            }
        });

        PaginationLog.d("PhotoListFragment onViewCreated");
    }

    private void gotoPhotoSwiper(int itemPosition, String item) {
        ArchLifecycleModel.save("PhotosPaginationLifecycle", mPhotoGridPagination);
        PaginationLog.d("gotoPhotoSwiper: " + mPhotoGridPagination);
        PhotoSwiperActivity.startFromList(getContext(), itemPosition, mPhotoGridPagination.lifecycleClone());
    }

    @Override
    public void onStart() {
        super.onStart();
        // todo we should reload photos when view created instead of onStart for restore state when View not destroyed
        reloadPhotos();
        PaginationLog.d("PhotoListFragment onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PaginationLog.d("PhotoListFragment onDestroy");
    }

    // reload the first page
    private void reloadPhotos() {
        mPaginationRecyclerView.reload();
        showLoadingView(true);
    }

    private void initPaginationRecyclerView() {
        // Create a PaginationLoader for biz loader, apply biz part
        PhotoPaginationLoader photoLoader = createPaginationLoader();
        // shared the pagination between activities/fragments
        mPhotoGridPagination = createPhotoListPagination(photoLoader);
        mPhotoGridPagination.addTrackingListener(this);

        // created recycler view adapter for load page
        PhotoGridAdapter photoGridAdapter = createPhotoGridAdapter(mPhotoGridPagination);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), GRID_COLUMN_COUNT, RecyclerView.VERTICAL, false);
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
        return new PhotoGridAdapter(getContext(), GRID_COLUMN_COUNT, pagination);
    }

    private Pagination<String> createPhotoListPagination(PhotoPaginationLoader photoPaginationLoader) {
        int pageStart = PhotoPaginationLoader.MockPhotoDataSet.PAGE_START;

        return new Pagination<>(PAGE_SIZE, pageStart, photoPaginationLoader);
    }

    private PhotoPaginationLoader createPaginationLoader() {
        String eventId = "";

        return new PhotoPaginationLoader(eventId);
    }

    private void showLoadingView(boolean show) {
        mViewLoadingPhotos.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPaginationLoaded(int page, int count, boolean success, boolean retry) {
        showLoadingView(false);
    }
}
