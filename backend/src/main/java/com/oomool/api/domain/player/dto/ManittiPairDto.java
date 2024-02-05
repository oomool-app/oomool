package com.oomool.api.domain.player.dto;

import lombok.Builder;

@Builder
public record ManittiPairDto(
    PlayerDto player,
    PlayerDto manitti
) {
}
