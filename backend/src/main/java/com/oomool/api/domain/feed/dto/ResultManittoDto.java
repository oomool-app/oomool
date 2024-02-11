package com.oomool.api.domain.feed.dto;

import java.util.List;

import com.oomool.api.domain.player.dto.ManittoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultManittoDto {
    private ManittoDto manittoDto;
    private List<RoomManittoDto> resultManittoList;
}
