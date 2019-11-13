package arch.anmobile.mvp.lifecycle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

import arch.anmobile.mvp.LifecycleRegistry;
import arch.anmobile.mvp.PresenterContainerProvider;

public class FragmentLifecycleRegistry extends LifecycleRegistry {
    private WeakReference<Fragment> mFragmentRef;

    private FragmentLifecycleRegistry(@NonNull Fragment fragment) {
        super(PresenterContainerProvider.getRegistryName(fragment));
        mFragmentRef = new WeakReference<>(fragment);
    }

    public static LifecycleRegistry of(@NonNull Fragment fragment) {
        return new FragmentLifecycleRegistry(fragment);
    }

    @Override
    public void registerLifecycleCallbacks(PresenterContainerProvider provider) {
        Fragment fragment = mFragmentRef.get();

    }

    @Override
    public void unregisterLifecycleCallbacks() {

    }
}
