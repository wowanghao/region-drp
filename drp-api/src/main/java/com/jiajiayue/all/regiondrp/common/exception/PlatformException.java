package com.jiajiayue.all.regiondrp.common.exception;

import lombok.Getter;

public class PlatformException extends RuntimeException {

    private static final long serialVersionUID = -2609810710632713363L;

    @Getter
    private PlatformErrorEnum PlatformErrorEnum;

    public PlatformException(PlatformErrorEnum PlatformErrorEnum) {
        super(PlatformErrorEnum.getName());
        this.PlatformErrorEnum = PlatformErrorEnum;
    }

    @Override
    public String getMessage() {
        return PlatformErrorEnum.getName();
    }

    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformException(Throwable cause) {
        super(cause);
    }
}
