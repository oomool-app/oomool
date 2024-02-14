package com.oomool.api.domain.room.dto;

import com.oomool.api.domain.player.dto.PlayerDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlayerEvent {

    private String eventType;
    private PlayerDto player;
    private String dateTime;

}
