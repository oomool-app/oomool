package com.oomool.api.domain.room.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.user.dto.UserDto;

import lombok.Builder;

@Builder
public record TempRoomDto(
    String inviteCode,
    int masterId,
    SettingOptionDto setting,
    List<PlayerDto> players,
    List<UserDto> banList,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt
) {
}
