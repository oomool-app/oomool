package com.oomool.api.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    /**
     * jsend 형식에 따라 공통 응답 포맷 Dto
     * https://github.com/omniti-labs/jsend
     * */

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String ERROR = "error";

    private String status;

    @JsonInclude(Include.NON_NULL)
    private T data;

    @JsonInclude(Include.NON_NULL)
    private String message;

    // SUCCESS & FAIL 시 응답
    private ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS, data, null);
    }

    // Hibernate Validator에 의해 유효하지 않는 데이터로 API 호출이 거부될 때 반환
    public static <T> ApiResponse<?> fail(T data) {
        return new ApiResponse<>(FAIL, data, null);
    }

    // 예외 발생으로 API 호출 실패 시 반환
    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>(ERROR, null, message);
    }
}
