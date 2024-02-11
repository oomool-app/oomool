package com.oomool.api.domain.player.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManittoDto {
    private String nickname; // 마니띠 닉네임
    private String avatarColor; // 아바타 배경색
    private String url; // 아바타 url
}
