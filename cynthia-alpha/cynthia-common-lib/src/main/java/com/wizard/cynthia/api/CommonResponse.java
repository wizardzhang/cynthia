package com.wizard.cynthia.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 通用返回结果
 * @author ZZW
 * @date 2021/4/29 11:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse<T> implements Serializable {

    public static final long serialVersionUID = 42L;

    /**
     * message
     */
    private String message;

    /**
     * data
     */
    private T data;

    @Builder.Default
    private ResultCode code = ResultCode.SUCCESS;

    public static <T> CommonResponse<T> error(CommonResponse<?> response) {
        return error(response.getCode(), response.getMessage());
    }

    public static <T> CommonResponse<T> error(ResultCode code, String message) {
        CommonResponse<T> response = new CommonResponse<>();
        response.code = code;
        response.message = message;
        return response;
    }

    public static <T> CommonResponse<T> success(T data) {
        CommonResponse<T> response = new CommonResponse<>();
        response.code = ResultCode.SUCCESS;
        response.data = data;
        response.message = "";
        return response;
    }
}
