package arch.anmobile.mvp.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import arch.anmobile.mvp.PresenterContainerProvider;
import arch.anmobile.mvp.View;

public class ActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    private final PresenterContainerProvider mProvider;

    ActivityLifecycleCallbacks(@NonNull PresenterContainerProvider provider) {
        this.mProvider = provider;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mProvider.onContainerCreated(PresenterContainerProvider.getRegistryName(activity));
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof View) {
            String registryName = PresenterContainerProvider.getRegistryName(activity);
            mProvider.onContainerBindView(registryName, (View) activity);
            mProvider.onContainerViewSafe(registryName);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        mProvider.onContainerUnbindView(PresenterContainerProvider.getRegistryName(activity));
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mProvider.onContainerDestroyed(PresenterContainerProvider.getRegistryName(activity));
    }
}
