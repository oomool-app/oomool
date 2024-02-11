package com.oomool.api.domain.room.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.dto.TempRoomDto;
import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.global.util.CustomDateUtil;

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
     *
     * */
    public TempRoomDto mapToTempRoomDto(Map<String, Object> tempRoomSetting, List<PlayerDto> playerDtoList,
        List<UserDto> banUserDtoList) {
        return TempRoomDto.builder()
            .inviteCode((String)tempRoomSetting.get("inviteCode"))
            .createdAt(CustomDateUtil.parseDateTime((String)tempRoomSetting.get("createdAt")))
            .masterId(Integer.parseInt((String)tempRoomSetting.get("masterId")))
            .setting(mapToSettingOptionDto(tempRoomSetting))
            .players(playerDtoList)
            .banList(banUserDtoList)
            .build();
    }

    /**
     * Redis 대기방에 있는 전체 플레이어 목록을 조회한다.
     *
     * @param playerJsonList redis에 serialization 된 json 객체
     * */
    public List<PlayerDto> objectToPlayerDtoList(List<Object> playerJsonList) {
        return playerJsonList.stream().map(this::objectToPlayerDto).collect(Collectors.toList());
    }

    public List<PlayerDto> objectToPlayerDtoList(Map<Integer, Object> totalPlayerMap) {
        return totalPlayerMap.values()
            .stream().map(this::objectToPlayerDto).toList();
    }

    /**
     * Redis 대기방에 있는 플레이어를 조회한다.
     *
     * @param playerJson Json에 Serlialize 된 Json 객체
     * */
    public PlayerDto objectToPlayerDto(Object playerJson) {
        try {
            return objectMapper.readValue((String)playerJson, PlayerDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
            return playerJson;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String userDtoToString(UserDto userDto) {
        String userJson;
        try {
            userJson = objectMapper.writeValueAsString(userDto);
            return userJson;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDto objectToUserDto(Object userJson) {
        try {
            return objectMapper.readValue((String)userJson, UserDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserDto> objectToUserDtoList(Set<Object> userJsonList) {
        return userJsonList.stream().map(this::objectToUserDto).collect(Collectors.toList());
    }

}
