package arch.anmobile.hyena;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class HyenaResults<T> {
    private final T mResults;
    final HyenaError mError;

    private HyenaResults(T results) {
        mResults = results;
        mError = null;
    }

    private HyenaResults(HyenaError error) {
        mError = error;
        mResults = null;
    }

    T getResults() {
        return mResults;
    }

    public static <T> HyenaResults<T> success(T results) {
        return new HyenaResults<>(results);
    }

    static <T> HyenaResults<T> error(HyenaError error) {
        return new HyenaResults<>(error);
    }

    public interface Listener<T> {
        void onResults(T results);
        void onErrorResults(HyenaError error);
    }
}
