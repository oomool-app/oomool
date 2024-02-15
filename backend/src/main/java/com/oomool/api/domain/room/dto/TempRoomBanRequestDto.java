package com.oomool.api.domain.room.dto;

import lombok.Builder;

@Builder
public record TempRoomBanRequestDto(
    int userId,
    int banUserId
) {
}
