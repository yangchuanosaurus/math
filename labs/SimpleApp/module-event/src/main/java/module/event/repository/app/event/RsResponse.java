package module.event.repository.app.event;

/**
 * Created by Albert Zhao on 2019-11-07.
 * Copyright (c) 2019 Android Mobile Yangchuanosaurus. All rights reserved.
 */
public abstract class RsResponse {
    private boolean success;
    private String errorMessage;
    private Integer errorCode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
