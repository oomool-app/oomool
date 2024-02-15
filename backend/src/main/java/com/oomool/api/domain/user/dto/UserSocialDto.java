package com.oomool.api.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserSocialDto {

    private String providerId;
    private String provider; // 카카오 or 구글...
    private String email;
    private String nickname;

    @Builder
    public UserSocialDto(String providerId, String provider, String email, String nickname) {
        this.providerId = providerId;
        this.provider = provider;
        this.email = email;
        this.nickname = nickname;
    }
}
