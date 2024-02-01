package com.oomool.api.domain.room.util;

import java.time.LocalDateTime;

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
            .createAt(LocalDateTime.now())
            .title(settingOptionDto.getTitle())
            .startDate(settingOptionDto.getStartDate())
            .endDate(settingOptionDto.getEndDate())
            .questionType(settingOptionDto.getQuestionType())
            .build();
    }

    /**
     * GameRoom Entity -> SettingOptionDto
     * */
    public SettingOptionDto convertSettingOptionDto(GameRoom gameRoom) {
        return SettingOptionDto.builder()
            .title(gameRoom.getTitle())
            .startDate(gameRoom.getStartDate())
            .endDate(gameRoom.getEndDate())
            .questionType(gameRoom.getQuestionType())
            .maxMember(gameRoom.getPlayers().size())
            .build();
    }

}
