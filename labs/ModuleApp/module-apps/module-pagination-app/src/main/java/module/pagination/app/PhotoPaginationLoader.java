package module.pagination.app;

import java.util.ArrayList;
import java.util.List;

import app.module.pagination.PaginationLoader;

public class PhotoPaginationLoader implements PaginationLoader<String> {
    private String mEventId;

    private MockPhotoDataSet mPhotoDataSet;

    public PhotoPaginationLoader(String eventId) {
        mEventId = eventId;
        mPhotoDataSet = new MockPhotoDataSet();
    }

    @Override
    public void loadPage(int page, int pageSize, PaginationLoaderListener<String> paginationLoaderListener) {
        List<String> list = mPhotoDataSet.getPage(page, pageSize);
        paginationLoaderListener.onPageLoaded(page, list);
    }

    public static class MockPhotoDataSet {

        public static final int PAGE_START = 1;
        private List<String> mPhotoList;

        private MockPhotoDataSet() {
            mPhotoList = new ArrayList<>();
//            for (int i = 0; i < 28; i++) {
//                mPhotoList.add("Photo - " + (i + 1));
//            }
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
