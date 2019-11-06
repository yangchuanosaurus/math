package arch.anmobile.draft;

public interface LifecycleObserver<S> {
    void setState(S state);
    S getState();
}
