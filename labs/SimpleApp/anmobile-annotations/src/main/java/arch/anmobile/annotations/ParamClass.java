package arch.anmobile.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Albert Zhao on 2019-11-07.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ParamClass {
    String name();
    Class type();
}
