package com.oomool.api.domain.room.dto;

import com.oomool.api.domain.user.dto.UserDto;

import lombok.Getter;

@Getter
public class TempRoomRequestDto {
    private SettingOptionDto setting;
    private UserDto userDto;
}
