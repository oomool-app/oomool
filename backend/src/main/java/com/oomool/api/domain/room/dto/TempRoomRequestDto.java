package com.oomool.api.domain.room.dto;

import java.util.List;

import com.oomool.api.domain.player.dto.PlayerDto;

import lombok.Getter;

@Getter
public class TempRoomRequestDto {
    private SettingRoomDto setting;
    private List<PlayerDto> players;
}
