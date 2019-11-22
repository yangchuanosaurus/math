package arch.anmobile.mvp;

public class PresenterContainer {

    private final PresenterContainerProvider provider;

    Presenter presenter;
    LifecycleRegistry lifecycleRegistry;

    PresenterContainer(PresenterContainerProvider provider, LifecycleRegistry lifecycleRegistry) {
        this.provider = provider;
        this.lifecycleRegistry = lifecycleRegistry;
    }

    @SuppressWarnings("unchecked")
    public <T extends Presenter<V>, V extends View> T create(Class<T> presenterClass) {
        try {
            presenter = presenterClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to create instance of " + presenterClass);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to create instance of " + presenterClass);
        }

        // Register lifecycle when presenter setup
        lifecycleRegistry.registerProvider(provider);

        return (T) presenter;
    }

}
