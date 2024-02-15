package com.oomool.api.domain.room.util;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.entity.GameRoom;

@Component
public class GameRoomMapper {

    /**
     * SettingOptionDto -> GameRoom Entity
     * */
    public GameRoom dtoToEntity(String roomUid, SettingOptionDto settingOptionDto) {
        return GameRoom.builder()
            .roomUid(roomUid)
            .title(settingOptionDto.getTitle())
            .startDate(settingOptionDto.getStartDate())
            .endDate(settingOptionDto.getEndDate())
            .questionType(settingOptionDto.getQuestionType())
            .players(new ArrayList<>())
            .build();
    }

    /**
     * GameRoom Entity -> SettingOptionDto
     * */
    public SettingOptionDto entityToSettingOptionDto(GameRoom gameRoom) {
        return SettingOptionDto.builder()
            .title(gameRoom.getTitle())
            .startDate(gameRoom.getStartDate())
            .endDate(gameRoom.getEndDate())
            .questionType(gameRoom.getQuestionType())
            .maxMember(gameRoom.getPlayers().size())
            .build();
    }

}
