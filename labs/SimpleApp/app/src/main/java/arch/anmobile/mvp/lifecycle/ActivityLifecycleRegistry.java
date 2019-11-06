package arch.anmobile.mvp.lifecycle;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

import arch.anmobile.mvp.LifecycleRegistry;
import arch.anmobile.mvp.PresenterContainerProvider;

public class ActivityLifecycleRegistry extends LifecycleRegistry {

    private WeakReference<Activity> mActivityRef;
    private Application.ActivityLifecycleCallbacks mActivityCallbacks;

    private ActivityLifecycleRegistry(@NonNull Activity activity) {
        super(PresenterContainerProvider.getRegistryName(activity));
        mActivityRef = new WeakReference<>(activity);
    }

    public static LifecycleRegistry of(@NonNull Activity activity) {
        return new ActivityLifecycleRegistry(activity);
    }

    @Override
    public void registerLifecycleCallbacks(PresenterContainerProvider provider) {
        Activity activity = mActivityRef.get();
        if (null != activity) {
            mActivityCallbacks = new ActivityLifecycleCallbacks(provider);
            activity.getApplication().registerActivityLifecycleCallbacks(mActivityCallbacks);
        }
    }

    @Override
    public void unregisterLifecycleCallbacks() {
        Activity activity = mActivityRef.get();
        if (null != activity && null != mActivityCallbacks) {
            activity.getApplication().unregisterActivityLifecycleCallbacks(mActivityCallbacks);
        }
    }
}
