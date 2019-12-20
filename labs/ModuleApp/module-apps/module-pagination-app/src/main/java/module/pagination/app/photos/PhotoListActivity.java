package module.pagination.app.photos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import module.pagination.app.R;

/**
 * show photos in pagination list
 * when user click on one photo, which should opened in a full-screen mode,
 *      also, the pagination status could be shared between
 *          the photos pagination list and the full-screen mode
 * when user back to the pagination list from full-screen mode
 *      the pagination status should updated by full-screen mode
 *
 * 1. how to share the pagination status between Activities?
 * */
public class PhotoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_photos);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_photos, new PhotoListFragment())
                .commit();
    }

}
