package com.oomool.api.domain.room.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingRoomDto;
import com.oomool.api.domain.room.dto.TempRoomDto;
import com.oomool.api.domain.room.util.UniqueCodeGenerator;

@Service
public class TempRoomService {

    // Redis의 데이터 액세스를 담당하는 redisService 로직과 분리한다.
    private final TempRoomRedisService redisService;

    @Autowired
    public TempRoomService(TempRoomRedisService redisService) {
        this.redisService = redisService;
    }

    public TempRoomDto createTempRoom(SettingRoomDto roomSetting, PlayerDto masterPlayer) throws
        JsonProcessingException {

        // inviteCode 생성
        UniqueCodeGenerator generator = new UniqueCodeGenerator();
        String inviteCode = generator.generateRandomString();

        // 임시방에 데이터 저장
        redisService.saveTempRoomSetting(inviteCode, roomSetting, masterPlayer.getUserId());
        redisService.saveTempRoomPlayer(inviteCode, masterPlayer);

        // 비즈니스 검증로직 필요

        return getTempRoom(inviteCode);
    }

    /**
     *  대기방 입장
     * */
    public Map<String, Object> joinTempRoom(String inviteCode, PlayerDto player) throws JsonProcessingException {
        // 0. inviteCode 유효 여부 확인 (startDate 검증 포함)

        // 1. maxNumber 유효 여부 검증 로직

        // 2. banList에 해당하는 지 검증 로직

        // 3. 방장은 초대코드로 들어와도 참여할 수 없다.

        // 4. 이미 기존에 참여하고 있는 유저라면 새로 등록되지 않는다.
        // playerList에 추가된다.
        redisService.saveTempRoomPlayer(inviteCode, player);
        return Map.ofEntries(Map.entry("players", redisService.getTempRoomPlayer(inviteCode)));
    }

    /**
     * 대기방 조회
     * - 대기방에 대한 전체 정보를 조회한다.
     * */
    public TempRoomDto getTempRoom(String inviteCode) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // 1. settingRoomDto
        SettingRoomDto settingRoom = objectMapper.convertValue(redisService.getTempRoomSetting(inviteCode),
            SettingRoomDto.class);
        // 2. player List
        List<PlayerDto> playerList = redisService.getTempRoomPlayer(inviteCode);

        return TempRoomDto.builder()
            .inviteCode(inviteCode)
            .masterId(playerList.get(0).getUserId())
            .setting(settingRoom)
            .players(playerList)
            .build();
    }

}
