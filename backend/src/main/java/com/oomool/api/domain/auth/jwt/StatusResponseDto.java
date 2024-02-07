package com.oomool.api.domain.auth.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * filter에서 JWT 토큰 예외 발생 시 클라이언트에 401 응답 코드를 전달
 */
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // DTO 를 JSON으로 변환 시 null값인 field 제외
public class StatusResponseDto {
    private Integer status;
    private Object data;

    public StatusResponseDto(Integer status) {
        this.status = status;
    }

    public static StatusResponseDto addStatus(Integer status) {
        return new StatusResponseDto(status);
    }

    public static StatusResponseDto success() {
        return new StatusResponseDto(200);
    }

    public static StatusResponseDto success(Object data) {
        return new StatusResponseDto(200, data);
    }
}
