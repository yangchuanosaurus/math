package arch.anmobile.draft;

public abstract class Presenter<V extends View> implements ActivityLifecycleObserver {
    public ActivityLifecycle.State lifecycleState;
    private V mView;

    @Override
    public void setState(ActivityLifecycle.State state) {
        lifecycleState = state;
        if (isDestroyed()) {
            unbindView();
        }
    }

    @Override
    public ActivityLifecycle.State getState() {
        return lifecycleState;
    }

    public void bindView(V view) {
        mView = view;
    }

    public void unbindView() {
        mView = null;
    }

    public V getView() {
        return mView;
    }

    protected boolean isSafeView() {
        return getState().isAtLeast(ActivityLifecycle.State.RESUMED);
    }

    protected boolean isSafeUpdateModel() {
        return getState().isAtLeast(ActivityLifecycle.State.CREATED);
    }

    protected boolean isDestroyed() {
        return getState().isAtLeast(ActivityLifecycle.State.DESTROYED);
    }
}
