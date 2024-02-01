package com.oomool.api.domain.room.dto;

import com.oomool.api.domain.player.dto.PlayerDto;

import lombok.Getter;

@Getter
public class TempRoomRequestDto {
    private SettingOptionDto setting;
    private PlayerDto master;
}
