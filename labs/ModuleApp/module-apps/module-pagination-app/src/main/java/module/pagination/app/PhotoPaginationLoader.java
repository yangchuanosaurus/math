package module.pagination.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import app.module.pagination.PaginationLoader;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PhotoPaginationLoader implements PaginationLoader<String> {
    private String mEventId;

    private MockPhotoDataSet mPhotoDataSet;

    public PhotoPaginationLoader(String eventId) {
        mEventId = eventId;
        mPhotoDataSet = new MockPhotoDataSet();
    }

    @Override
    public void loadPage(final int page, final int pageSize,
                         final PaginationLoaderListener<String> paginationLoaderListener) {
        Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                Thread.sleep(2000);
                List<String> strings = mPhotoDataSet.getPage(page, pageSize);
                return strings == null ? new ArrayList<String>() : strings;
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        if (page == 0) {
                            paginationLoaderListener.onPageLoadFailed(page);
                        } else {
                            paginationLoaderListener.onPageLoaded(page, strings);
                        }
                    }
                });

//        paginationLoaderListener.onPageLoaded(page, mPhotoDataSet.getPage(page, pageSize));
    }

    public static class MockPhotoDataSet {

        public static final int PAGE_START = 1;
        public static final int PAGE_SIZE = PhotoGridActivity.PAGE_SIZE;
        private List<String> mPhotoList;

        private MockPhotoDataSet() {
            mPhotoList = new ArrayList<>();
            for (int i = 0; i < PAGE_SIZE * 20 + 1; i++) {
                mPhotoList.add("Photo - " + (i + 1));
            }
        }

        List<String> getPage(int page, int pageSize) {
            if (mPhotoList.isEmpty()) return null;

            int start = (page - PAGE_START) * pageSize;
            int count = pageSize;

            if (mPhotoList.size() < start) return null; // no photos there

            if (mPhotoList.size() < start + count) {
                count = mPhotoList.size() - start; // no enough photos in data set
            }

            List<String> pagePhoto = new ArrayList<>();
            for (int i = start; i < start + count; i++) {
                pagePhoto.add(mPhotoList.get(i));
            }

            return pagePhoto;
        }
    }
}
