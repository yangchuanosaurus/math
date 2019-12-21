package module.pagination.app.photos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import app.module.pagination.Pagination;
import app.module.pagination.PaginationLog;
import module.pagination.app.R;

public class PhotoSwiperActivity extends AppCompatActivity {

    private static final String INTENT_ITEM_POSITION = "photos.photo.position";
    private static final String INTENT_PAGINATION = "photos.pagination";

    private Pagination<String> mPagination;

    public static void startFromList(@NonNull Context context, Pagination<String> pagination, int itemPosition) {
        Intent intent = new Intent(context, PhotoSwiperActivity.class);
        intent.putExtra(INTENT_ITEM_POSITION, itemPosition);
        intent.putExtra(INTENT_PAGINATION, pagination);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_swiper);

        if (null != getIntent()) {
            int itemPosition = getIntent().getExtras().getInt(INTENT_ITEM_POSITION);
            mPagination = (Pagination<String>) getIntent().getExtras().getSerializable(INTENT_PAGINATION);

            PaginationLog.d("Restore itemPosition=" + itemPosition + ", pagination=" + mPagination);
        }
    }

}
