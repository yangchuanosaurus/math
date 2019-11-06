package arch.anmobile.mvp;

import android.app.Activity;

import androidx.annotation.NonNull;

import arch.anmobile.mvp.lifecycle.ActivityLifecycleRegistry;

public class ActivityMvp {

    public static PresenterContainer of(@NonNull Activity activity) {
        LifecycleRegistry lifecycleRegistry = ActivityLifecycleRegistry.of(activity);
        return PresenterContainerProvider.of(lifecycleRegistry);
    }

}
