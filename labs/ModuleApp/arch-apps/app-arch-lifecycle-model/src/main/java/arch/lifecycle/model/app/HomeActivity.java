package arch.lifecycle.model.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import arch.lifecycle.model.ArchLifecycleModel;

public class HomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String lifecycle = "HomeLifecycle";
        // The model needs Context
//        PageModel pageModel = ArchLifecycleModel.of(this, lifecycle).create(PageModel::new);

        // The model doesn't needs Context
//        PageModel pageModel = ArchLifecycleModel.of(lifecycle).create(PageModel::new);
    }
}
