package arch.anmobile.mvp;

import java.util.HashMap;
import java.util.Map;

public class PresenterContainerProvider {

    private Map<String, PresenterContainer> presenterContainerMap;

    private PresenterContainerProvider() {
        presenterContainerMap = new HashMap<>();
    }

    private interface LazyLoader {
        PresenterContainerProvider SINGLETON = new PresenterContainerProvider();
    }

    public static PresenterContainer of(LifecycleRegistry lifecycleRegistry) {
        return LazyLoader.SINGLETON.addPresenterContainer(lifecycleRegistry);
    }

    private PresenterContainer getPresenterContainer(String registryName) {
        return presenterContainerMap.get(registryName);
    }

    public static String getRegistryName(Object object) {
        return object.getClass().getCanonicalName();
    }

    private PresenterContainer addPresenterContainer(LifecycleRegistry lifecycleRegistry) {
        PresenterContainer presenterContainer;
        if (!presenterContainerMap.containsKey(lifecycleRegistry.getRegistryName())) {
            presenterContainer = new PresenterContainer(this, lifecycleRegistry);
            presenterContainerMap.put(lifecycleRegistry.getRegistryName(), presenterContainer);
        } else {
            presenterContainer = presenterContainerMap.get(lifecycleRegistry.getRegistryName());
        }

        return presenterContainer;
    }

    public void onContainerCreated(String registryName) {
        PresenterContainer presenterContainer = getPresenterContainer(registryName);
        if (null != presenterContainer) {
            presenterContainer.presenter.onCreated();
        }
    }

    @SuppressWarnings("unchecked")
    public void onContainerBindView(String registryName, View view) {
        PresenterContainer presenterContainer = getPresenterContainer(registryName);
        if (null != presenterContainer) {
            presenterContainer.presenter.bindView(view);
        }
    }

    public void onContainerViewSafe(String registryName) {
        PresenterContainer presenterContainer = getPresenterContainer(registryName);
        if (null != presenterContainer) {
            presenterContainer.presenter.onViewSafe();
        }
    }

    public void onContainerUnbindView(String registryName) {
        PresenterContainer presenterContainer = getPresenterContainer(registryName);
        if (null != presenterContainer) {
            presenterContainer.presenter.unbindView();
        }
    }

    public void onContainerDestroyed(String registryName) {
        PresenterContainer presenterContainer = getPresenterContainer(registryName);
        if (null != presenterContainer) {
            presenterContainer.presenter.onDestroy();
            presenterContainer.lifecycleRegistry.unregisterLifecycleCallbacks();

            presenterContainerMap.remove(registryName);
        }
    }
}
