package arch.anmobile.draft;

public abstract class Lifecycle<S> {
    public abstract void addObserver(LifecycleObserver<S> observer);
    public abstract void removeObserver(LifecycleObserver<S> observer);
    public abstract void postState(S state);
}
