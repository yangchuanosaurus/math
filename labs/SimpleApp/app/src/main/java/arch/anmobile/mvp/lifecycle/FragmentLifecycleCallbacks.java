package arch.anmobile.mvp.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f,
                                      @NonNull Context context) {

    }

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f,
                                   @NonNull Context context) {

    }

    @Override
    public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                     @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                  @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                          @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                      @NonNull View v, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {

    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {

    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {

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

    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {

    }
}
