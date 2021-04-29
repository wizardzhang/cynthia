package com.wizard.cynthia.exception;

import com.wizard.cynthia.api.CommonResponse;
import com.wizard.cynthia.api.ResultCode;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author ZZW
 * @description: 全局异常处理器
 * @date 2021/4/29 13:57
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    public static final String S_S = "%s:%s";

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResponse<Object> handleError(MissingServletRequestParameterException e) {
        log.warn("Missing Request Parameter", e);
        String message = String.format("Missing Request Parameter: %s", e.getParameterName());
        return CommonResponse
                .builder()
                .code(ResultCode.PARAM_MISS)
                .message(message)
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResponse<Object> handleError(MethodArgumentTypeMismatchException e) {
        log.warn("Method Argument Type Mismatch", e);
        String message = String.format("Method Argument Type Mismatch: %s", e.getName());
        return CommonResponse
                .builder()
                .code(ResultCode.PARAM_TYPE_ERROR)
                .message(message)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<Object> handleError(MethodArgumentNotValidException e) {
        log.warn("Method Argument Not Valid", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        assert error != null;
        String message = String.format(S_S, error.getField(), error.getDefaultMessage());
        return CommonResponse
                .builder()
                .code(ResultCode.PARAM_VALID_ERROR)
                .message(message)
                .build();
    }

    @ExceptionHandler(BindException.class)
    public CommonResponse<Object> handleError(BindException e) {
        log.warn("Bind Exception", e);
        FieldError error = e.getFieldError();
        assert error != null;
        String message = String.format(S_S, error.getField(), error.getDefaultMessage());
        return CommonResponse
                .builder()
                .code(ResultCode.PARAM_BIND_ERROR)
                .message(message)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResponse<Object> handleError(ConstraintViolationException e) {
        log.warn("Constraint Violation", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = String.format(S_S, path, violation.getMessage());
        return CommonResponse
                .builder()
                .code(ResultCode.PARAM_VALID_ERROR)
                .message(message)
                .build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResponse<Object> handleError(NoHandlerFoundException e) {
        log.error("404 Not Found", e);
        return CommonResponse
                .builder()
                .code(ResultCode.NOT_FOUND)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResponse<Object> handleError(HttpMessageNotReadableException e) {
        log.error("Message Not Readable", e);
        return CommonResponse
                .builder()
                .code(ResultCode.MSG_NOT_READABLE)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResponse<Object> handleError(HttpRequestMethodNotSupportedException e) {
        log.error("Request Method Not Supported", e);
        return CommonResponse
                .builder()
                .code(ResultCode.METHOD_NOT_SUPPORTED)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public CommonResponse<Object> handleError(HttpMediaTypeNotSupportedException e) {
        log.error("Media Type Not Supported", e);
        return CommonResponse
                .builder()
                .code(ResultCode.MEDIA_TYPE_NOT_SUPPORTED)
                .message(e.getMessage())
                .build();
    }

    public CommonResponse<Object> handleError(ServiceException e) {
        log.error("Service Exception", e);
        return CommonResponse
                .builder()
                .code(e.getResultCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(Throwable.class)
    public CommonResponse<Object> handleError(Throwable e) {
        log.error("Internal Server Error", e);
        return CommonResponse
                .builder()
                .code(ResultCode.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
    }
}
