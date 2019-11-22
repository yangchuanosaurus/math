package arch.anmobile.mvp;

public abstract class Presenter<V extends View> {
    private V mView;

    void bindView(V view) {
        mView = view;
    }

    void unbindView() {
        mView = null;
    }

    public V getView() {
        return mView;
    }

    protected boolean isViewSafe() {
        return mView != null;
    }

    protected abstract void onCreated();
    protected abstract void onViewSafe();
    protected abstract void onDestroy();
}
