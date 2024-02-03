package com.oomool.api.domain.room.service;

import java.util.Map;

import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.entity.GameRoom;

public interface GameRoomService {
    /**
     * 문답방을 생성한다.
     *
     * @param roomUid 문답방 UUID
     * @param settingOptionDto 문답방 설정 옵션
     * */
    void createGameRoom(String roomUid, SettingOptionDto settingOptionDto);

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

}
