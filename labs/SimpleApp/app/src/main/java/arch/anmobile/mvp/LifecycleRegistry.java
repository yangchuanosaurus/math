package arch.anmobile.mvp;

public abstract class LifecycleRegistry {

    private final String registryName;

    protected LifecycleRegistry(String registryName) {
        this.registryName = registryName;
    }

    public String getRegistryName() {
        return registryName;
    }

    /**
     * registerProvider, and registerLifecycleCallbacks
     * @param provider, singleton presenter containers
     * */
    void registerProvider(PresenterContainerProvider provider) {
        registerLifecycleCallbacks(provider);
    }

    public abstract void registerLifecycleCallbacks(PresenterContainerProvider provider);
    public abstract void unregisterLifecycleCallbacks();
}
