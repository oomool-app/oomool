package com.oomool.api.domain.room.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingOptionDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TempRoomMapper {

    private final ObjectMapper objectMapper;

    /**
     * Redis에 있는 hashOps를 SettingOptionDto로 변환한다.
     * - Redis에 있는 roomSetting 전체 key-value를 가져온다.
     * - SettingOptionDto로 필요한 정보만 반환한다.
     *
     * @param tempRoomSetting 방 정보 관련 hashOps
     * */
    public SettingOptionDto mapToSettingOptionDto(Map<String, Object> tempRoomSetting) {
        // settingOptionDto에 해당하지 않는 정보 제외
        tempRoomSetting.remove("inviteCode");
        tempRoomSetting.remove("createdAt");
        tempRoomSetting.remove("masterId");

        // mapper 반환
        return objectMapper.convertValue(tempRoomSetting, SettingOptionDto.class);
    }

    /**
     * Redis 대기방에 있는 전체 플레이어 목록을 조회한다.
     *
     * @param playerJsonList redis에 serialization 된 json 객체
     * */
    public List<PlayerDto> objectToPlayerDtoList(List<Object> playerJsonList) throws JsonProcessingException {
        List<PlayerDto> playerDtoList = new ArrayList<>();
        if (playerJsonList != null) {
            for (Object playerJson : playerJsonList) {
                PlayerDto playerDto = objectMapper.readValue((String)playerJson, PlayerDto.class);
                playerDtoList.add(playerDto);
            }
        }
        return playerDtoList;
    }

    /**
     * Redis에 SettingRoomDto를 Map<String, Object> 형식으로 저장하기 위한 mapper
     *
     * @param settingOptionDto 방 설정 Dto
     * */
    public Map<String, Object> settingRoomDtoToMap(SettingOptionDto settingOptionDto) {

        return objectMapper.convertValue(settingOptionDto, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * Redis에 Player를 Serializer 하여 저장하기 위한 mapper
     *
     * @param playerDto 플레이어 Dto
     * */
    public String playerDtoToString(PlayerDto playerDto) {
        String playerJson;
        try {
            playerJson = objectMapper.writeValueAsString(playerDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            playerJson = "{}";
        }
        return playerJson;
    }

}
