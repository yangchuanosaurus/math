package arch.anmobile.mvp.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import arch.anmobile.mvp.PresenterContainerProvider;

public class FragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {

    private final PresenterContainerProvider mProvider;

    FragmentLifecycleCallbacks(@NonNull PresenterContainerProvider provider) {
        this.mProvider = provider;
    }

    @Override
    public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f,
                                      @NonNull Context context) {
//        Log.d("Albert", "onFragmentPreAttached " + f);
    }

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f,
                                   @NonNull Context context) {
//        Log.d("Albert", "onFragmentAttached " + f);

    }

    @Override
    public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                     @Nullable Bundle savedInstanceState) {
//        Log.d("Albert", "onFragmentPreCreated " + f);
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                  @Nullable Bundle savedInstanceState) {
//        Log.d("Albert", "onFragmentCreated " + f);
        if (null != f.getContext()) {
            mProvider.onContainerCreated(FragmentRegistry.getRegistryName(f.getContext(), f));
        }
    }

    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                          @Nullable Bundle savedInstanceState) {
//        Log.d("Albert", "onFragmentActivityCreated " + f);
    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                      @NonNull View v, @Nullable Bundle savedInstanceState) {
//        Log.d("Albert", "onFragmentViewCreated " + f);
    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
        Context context = f.getContext();
        if (null != context && f instanceof arch.anmobile.mvp.View) {
            String registryName = FragmentRegistry.getRegistryName(context, f);
            mProvider.onContainerBindView(registryName, (arch.anmobile.mvp.View) f);
            mProvider.onContainerViewSafe(registryName);
        }
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {

    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        Context context = f.getContext();
        if (null != context) {
            String registryName = FragmentRegistry.getRegistryName(context, f);
            mProvider.onContainerUnbindView(registryName);
        }
    }

    @Override
    public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {

    }

    @Override
    public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f,
                                            @NonNull Bundle outState) {

    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {

    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
//        Log.d("Albert", "onFragmentDestroyed " + f);
        Context context = f.getContext();
        if (null != context) {
            String registryName = FragmentRegistry.getRegistryName(context, f);
            mProvider.onContainerDestroyed(registryName);
        }
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {

    }
}
