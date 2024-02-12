package com.oomool.api.domain.room.dto;

import com.oomool.api.domain.user.dto.UserDto;

import lombok.Builder;

@Builder
public record TempRoomBanRequestDto(
    UserDto user,
    UserDto banUser
) {
}
