package com.oomool.api.domain.player.service;

import java.util.List;
import java.util.Map;

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
     * 나의 플레이어 프로필을 조회한다.
     *
     * @param roomUid 문답방 코드
     * @param userId 유저 아이디
     * */
    PlayerDto getPlayerByUserId(String roomUid, int userId);

    /**
     * User ID를 기준으로 나의 마니띠의 Player 프로필을 가져온다.
     *
     * @param roomUid 문답방 코드
     * @param userId 유저 아이디
     * */
    Map<String, Object> getManittiPlayerProfile(String roomUid, int userId);

    /**
     * 나의 "마니또"를 조회한다. = 나를 "마니띠"로 갖는 플레이어를 조회한다.
     *
     * @param roomUid 문답방 코드
     * @param userId 유저 아이디
     * */
    PlayerDto getManittoPlayerProfile(String roomUid, int userId);

    /**
     * 마니또의 추측 결과를 저장한다.
     *
     * @param roomUid 문답방 코드
     * @param userId 유저 아이디
     * @param guessResult 추측 여부 결과
     * */
    void saveGuessResult(String roomUid, int userId, boolean guessResult);

    /**
     * 마니띠 정보 가져오기
     * */
    ManittiDto getManittiInfo(int authorId);

    /**
     * 작성자 정보 가져오기
     */
    Player getPlayerInfo(int authorId);
}
