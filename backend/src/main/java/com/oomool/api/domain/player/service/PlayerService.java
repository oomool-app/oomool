package com.oomool.api.domain.player.service;

import java.util.List;

import com.oomool.api.domain.player.dto.PlayerDto;

public interface PlayerService {

    /**
     * 문답방의 플레이어 정보를 저장한다.
     *
     * @param roomUid 문답방 코드
     * */
    List<PlayerDto> getPlayerDtoList(String roomUid);

}
