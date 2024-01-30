package com.oomool.api.domain.player.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private int userId;
    private String userEmail;
    private String playerNickname;
    private String playerBackgroundColor;
    private String playerAvatarUrl;
}
