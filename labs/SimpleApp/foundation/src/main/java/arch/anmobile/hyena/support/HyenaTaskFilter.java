package arch.anmobile.hyena.support;

import arch.anmobile.hyena.HyenaTask;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public interface HyenaTaskFilter {
    boolean apply(HyenaTask task);
}
