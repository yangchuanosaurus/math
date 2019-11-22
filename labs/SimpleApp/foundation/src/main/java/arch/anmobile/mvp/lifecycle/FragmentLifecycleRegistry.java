package arch.anmobile.mvp.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import arch.anmobile.mvp.LifecycleRegistry;
import arch.anmobile.mvp.PresenterContainerProvider;

public class FragmentLifecycleRegistry extends LifecycleRegistry {
    private WeakReference<Fragment> mFragmentRef;

    private FragmentManager.FragmentLifecycleCallbacks mFragmentCallbacks;

    private FragmentLifecycleRegistry(@NonNull Context context, @NonNull Fragment fragment) {
        super(FragmentRegistry.getRegistryName(context, fragment));
        mFragmentRef = new WeakReference<>(fragment);
    }

    public static LifecycleRegistry of(@NonNull Context context, @NonNull Fragment fragment) {
        return new FragmentLifecycleRegistry(context, fragment);
    }

    @Override
    public void registerLifecycleCallbacks(PresenterContainerProvider provider) {
        Fragment fragment = mFragmentRef.get();

        if (null != fragment) {
            if (null == mFragmentCallbacks) {
                mFragmentCallbacks = new FragmentLifecycleCallbacks(provider);
            }

            if (null != fragment.getActivity()) {
                RegistryManager.sharedInstance().register(fragment.getActivity(),
                        mFragmentCallbacks);
            }
        }

    }

    @Override
    public void unregisterLifecycleCallbacks() {
        Fragment fragment = mFragmentRef.get();
        if (null != fragment && null != mFragmentCallbacks) {
            if (null != fragment.getActivity()) {
                RegistryManager.sharedInstance().unregister(fragment.getActivity(),
                        mFragmentCallbacks);
            }
        }
    }

    private static class RegistryManager {
        private int mFragmentCount = 0; // In Activity Instance, we could add multiple fragments

        interface LazyLoader {
            RegistryManager SINGLETON = new RegistryManager();
        }

        static RegistryManager sharedInstance() {
            return LazyLoader.SINGLETON;
        }

        void register(@NonNull FragmentActivity activity,
                      FragmentManager.FragmentLifecycleCallbacks callbacks) {
            mFragmentCount++;
            if (mFragmentCount == 1) { // register when first fragment added
                activity.getSupportFragmentManager()
                        .registerFragmentLifecycleCallbacks(callbacks, false);
            }
        }

        void unregister(@NonNull FragmentActivity activity,
                        FragmentManager.FragmentLifecycleCallbacks callbacks) {
            mFragmentCount--;
            if (mFragmentCount == 0) {
                activity.getSupportFragmentManager()
                        .unregisterFragmentLifecycleCallbacks(callbacks);
            }
        }
    }
}
