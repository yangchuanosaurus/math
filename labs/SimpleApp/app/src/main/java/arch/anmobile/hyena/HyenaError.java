package arch.anmobile.hyena;

/**
 * Created by Albert Zhao on 2019-10-28.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
public class HyenaError extends Exception {

    public HyenaError() {
    }

    public HyenaError(String exceptionMessage) {
        super(exceptionMessage);
    }

    public HyenaError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
    }

    public HyenaError(Throwable cause) {
        super(cause);
    }

}
