package com.oomool.api.domain.room.service;

import java.util.Map;

import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.entity.GameRoom;

public interface GameRoomService {

    /**
     * 문답방을 생성한다.
     *
     * @param inviteCode 초대코드
     * */
    String createGameRoom(String inviteCode) throws Exception;

    /**
     * 문답방 Entity를 조회한다.
     *
     * @param roomUid 문답방 UUID
     * */
    GameRoom getGameRoom(String roomUid);

    /**
     * 문답방의 상세 정보를 조회한다.
     *
     * @param roomUid 문답방 UUID
     * */
    Map<String, Object> getGameRoomDetail(String roomUid);

    /**
     * 문답방의 설정 옵션을 조회한다.
     *
     * @param roomUid 문답방 UUID
     * */
    SettingOptionDto getSettingOptionDto(String roomUid);

    /**
     * 문답방의 설정 옵션을 조회한다.
     *
     * @param gameRoom 게임 룸엔티티
     * */
    SettingOptionDto getSettingOptionDtoByGameRoom(GameRoom gameRoom);
}