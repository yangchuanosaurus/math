package arch.anmobile.mvp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import arch.anmobile.mvp.lifecycle.FragmentLifecycleRegistry;

import static arch.anmobile.mvp.lifecycle.FragmentRegistry.FRAGMENT_ALIAS;

public class FragmentMvp {

    public static PresenterContainer of(@NonNull Fragment fragment) {
        if (null == fragment.getActivity()) throw new UnsupportedClassVersionError("Fragment need attached Activity.");

        LifecycleRegistry lifecycleRegistry = FragmentLifecycleRegistry.of(fragment.getActivity(), fragment);
        return PresenterContainerProvider.of(lifecycleRegistry);
    }

    public static <T extends Fragment> T createInstance(@NonNull FragmentActivity fragmentActivity,
                                                        Class<T> fragmentClass) {
        return createInstance(fragmentActivity, fragmentClass, null, null);
    }

    public static <T extends Fragment> T createInstance(@NonNull FragmentActivity fragmentActivity,
                                                        Class<T> fragmentClass,
                                                        @Nullable Bundle args) {
        return createInstance(fragmentActivity, fragmentClass, args, null);
    }

    public static <T extends Fragment> T createInstance(@NonNull FragmentActivity fragmentActivity,
                                                        Class<T> fragmentClass,
                                                        String presentFragmentAlias) {
        return createInstance(fragmentActivity.getSupportFragmentManager(),
                fragmentActivity.getClassLoader(), fragmentClass, null, presentFragmentAlias);
    }

    public static <T extends Fragment> T createInstance(@NonNull FragmentActivity fragmentActivity,
                                                        Class<T> fragmentClass,
                                                        @Nullable Bundle args,
                                                        String presentFragmentAlias) {
        return createInstance(fragmentActivity.getSupportFragmentManager(),
                fragmentActivity.getClassLoader(), fragmentClass, args, presentFragmentAlias);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Fragment> T createInstance(@NonNull FragmentManager fragmentManager,
                                                         ClassLoader classLoader,
                                                         Class<T> fragmentClass,
                                                         @Nullable Bundle args,
                                                         @Nullable String presentFragmentAlias) {
        T fragment = (T) fragmentManager.getFragmentFactory().instantiate(classLoader, fragmentClass.getName());

        Bundle presentFragmentArgs = null;
        if (null != presentFragmentAlias) {
            presentFragmentArgs = new Bundle();
            presentFragmentArgs.putString(FRAGMENT_ALIAS, presentFragmentAlias);

        }

        Bundle fragmentArgs = mergeBundle(presentFragmentArgs, args);

        if (null != fragmentArgs) {
            fragment.setArguments(fragmentArgs);
        }

        return fragment;
    }

    private static @Nullable Bundle mergeBundle(@Nullable Bundle arg1, @Nullable Bundle arg2) {
        if (null == arg1 && null == arg2) return null;

        Bundle bundle = new Bundle();
        if (null != arg1) {
            bundle.putAll(arg1);
        }
        if (null != arg2) {
            bundle.putAll(arg2);
        }
        return bundle;
    }
}
