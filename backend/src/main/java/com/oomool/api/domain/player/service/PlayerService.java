package com.oomool.api.domain.player.service;

import java.util.List;

import com.oomool.api.domain.player.dto.PlayerDto;

public interface PlayerService {

    /**
     * 문답방에 (대기방에 있던) 플레이어 정보를 저장한다.
     *
     * @param inviteCode 초대코드
     * @param roomUid 문답방 코드
     * */
    void savePlayerList(String inviteCode, String roomUid) throws Exception;

    /**
     * 문답방의 플레이어 정보를 저장한다.
     *
     * @param roomUid 문답방 코드
     * */
    List<PlayerDto> getPlayerDtoList(String roomUid);

}
