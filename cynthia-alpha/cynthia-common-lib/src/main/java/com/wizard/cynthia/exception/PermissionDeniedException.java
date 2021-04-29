package com.wizard.cynthia.exception;

import com.wizard.cynthia.api.ResultCode;
import lombok.Getter;

public class PermissionDeniedException extends RuntimeException {

    @Getter
    private final ResultCode resultCode;

    public PermissionDeniedException(String message) {
        super(message);
        this.resultCode = ResultCode.UN_AUTHORIZED;
    }

    public PermissionDeniedException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public PermissionDeniedException(ResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}