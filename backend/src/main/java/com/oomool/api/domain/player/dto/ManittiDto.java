package com.oomool.api.domain.player.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ManittiDto {
    private String nickname; // 마니띠 닉네임
    private String avatarColor; // 아바타 배경색
    private String url; // 아바타 url

    @Builder
    public ManittiDto(String nickname, String avatarColor, String url) {
        this.nickname = nickname;
        this.avatarColor = avatarColor;
        this.url = url;
    }
}
