package arch.anmobile.draft;

import java.util.ArrayList;
import java.util.List;

public class ActivityLifecycle extends Lifecycle<ActivityLifecycle.State> {
    List<LifecycleObserver<ActivityLifecycle.State>> lifecycleObserverList;

    public ActivityLifecycle() {
        lifecycleObserverList = new ArrayList<>();
    }

    @Override
    public void addObserver(LifecycleObserver<ActivityLifecycle.State> observer) {
        lifecycleObserverList.add(observer);
    }

    @Override
    public void removeObserver(LifecycleObserver<ActivityLifecycle.State> observer) {
        lifecycleObserverList.remove(observer);
    }

    @Override
    public void postState(ActivityLifecycle.State state) {
        for (LifecycleObserver<ActivityLifecycle.State> observer : lifecycleObserverList) {
            observer.setState(state);
        }
    }

    public enum State {
        INITIALIZED,
        CREATED,
        STARTED,
        RESUMED, // resumed, not paused
        PAUSED, // paused, not resumed
        STOPPED,
        DESTROYED;

        public boolean isAtLeast(State state) {
            if ((this == PAUSED && state == RESUMED) ||
                    (this == STOPPED && (state == STARTED || state == RESUMED)) ||
                    (this == DESTROYED)) {
                return compareTo(state) <= 0;
            }
            return compareTo(state) >= 0;
        }
    }
}
