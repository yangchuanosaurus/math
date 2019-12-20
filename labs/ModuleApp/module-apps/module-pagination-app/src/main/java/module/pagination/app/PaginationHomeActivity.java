package module.pagination.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Albert Zhao on 2019-12-19.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class PaginationHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination_home);
    }

    public void onShowList(View view) {
        showDemo(PaginationHomeActivity.class);
    }

    public void onShowPaginationListDemo(View view) {
        showDemo(PhotoListActivity.class);
    }

    public void onShowPaginationGridDemo(View view) {
        showDemo(PhotoGridActivity.class);
    }

    public void onShowSharedPaginationDemo(View view) {
        showDemo(module.pagination.app.photos.PhotoListActivity.class);
    }

    private void showDemo(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
