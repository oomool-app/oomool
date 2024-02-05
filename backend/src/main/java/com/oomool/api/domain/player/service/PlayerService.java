package com.oomool.api.domain.player.service;

import java.util.List;

import com.oomool.api.domain.player.dto.ManittiDto;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.player.entity.Player;

public interface PlayerService {

    /**
     * 문답방의 플레이어 정보를 저장한다.
     *
     * @param roomUid 문답방 코드
     * */
    List<PlayerDto> getPlayerDtoList(String roomUid);

    /**
     * 마니띠 정보 가져오기
     * */
    ManittiDto getManittiInfo(int authorId);

    /**
     * 작성자 정보 가져오기
     */
    Player getPlayerInfo(int authorId);
}
