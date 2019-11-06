package arch.anmobile.hyena;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class Hyena {

    private static final int POOL_SIZE_LIMITS = 4;
    private static final int POOL_SIZE_DEFAULT = 2;
    public static final String TAG = "HYENA";

    public static HyenaQueue create() {
        return create(POOL_SIZE_DEFAULT);
    }

    public static HyenaQueue create(int poolSize) {
        return new HyenaQueue(poolSize);
    }

    public static HyenaQueue create(HyenaResultsDelivery delivery) {
        return create(POOL_SIZE_DEFAULT, delivery);
    }

    public static HyenaQueue create(int poolSize, HyenaResultsDelivery delivery) {
        return new HyenaQueue(poolSize, delivery);
    }

    public static HyenaQueue createAppQueue(int poolSize) {
        return create(poolSize);
    }

    public static HyenaQueue createPageQueue(int poolSize) {
        return create(poolSize);
    }

    private static boolean checkPoolSize(int poolSize) {
        return poolSize > POOL_SIZE_LIMITS;
    }
}
