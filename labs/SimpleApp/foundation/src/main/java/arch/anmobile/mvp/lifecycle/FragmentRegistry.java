package arch.anmobile.mvp.lifecycle;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import arch.anmobile.mvp.PresenterContainerProvider;

/**
 * Created by Albert Zhao on 2019-11-14.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public class FragmentRegistry {

    public static final String FRAGMENT_ALIAS = "fragment.alias";

    static String getRegistryName(@NonNull Context context, @NonNull Fragment fragment) {
        String registryName = PresenterContainerProvider.getRegistryName(context, fragment);
        Bundle args = fragment.getArguments();
        if (null != args && args.containsKey(FRAGMENT_ALIAS)) {
            String alias = args.getString(FRAGMENT_ALIAS);
            registryName = registryName + "-" + alias;
        }

        return registryName;
    }

}
