package com.oomool.api.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SecurityUserDto {
    private int userId;
    private String email;
    private String role;
    private String nickname;

    @Builder
    public SecurityUserDto(int userId, String email, String role, String nickname) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.nickname = nickname;
    }
}
