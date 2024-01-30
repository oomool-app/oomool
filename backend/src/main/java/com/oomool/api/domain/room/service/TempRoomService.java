package com.oomool.api.domain.room.service;

import java.util.List;

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
    private final RedisService redisService;

    @Autowired
    public TempRoomService(RedisService redisService) {
        this.redisService = redisService;
    }

    public String createTempRoom(SettingRoomDto roomSetting, PlayerDto masterPlayer) throws JsonProcessingException {

        // inviteCode 생성
        UniqueCodeGenerator generator = new UniqueCodeGenerator();
        String inviteCode = generator.generateRandomString();

        // 임시방에 데이터 저장
        redisService.saveTempRoomSetting(inviteCode, roomSetting, masterPlayer.getUserId());
        redisService.saveTempRoomPlayer(inviteCode, masterPlayer);

        // 비즈니스 검증로직 필요

        return inviteCode;
    }

    /**
     *  대기방 입장
     * */
    public String joinTempRoom(String inviteCode, PlayerDto player) throws JsonProcessingException {
        // 0. inviteCode 유효 여부 확인 (startDate 검증 포함)

        // 1. maxNumber 유효 여부 검증 로직

        // 2. banList에 해당하는 지 검증 로직

        // 3. 방장은 초대코드로 들어와도 참여할 수 없다.

        // 4. 이미 기존에 참여하고 있는 유저라면 새로 등록되지 않는다.
        // playerList에 추가된다.
        redisService.saveTempRoomPlayer(inviteCode, player);
        return "ok";
    }

    /**
     * 대기방 조회
     * - 대기방에 대한 전체 정보를 조회한다.
     * */
    public TempRoomDto getTempRoom(String inviteCode) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // 1. settingRoomDto 생성해줘야한다.
        SettingRoomDto settingRoom = objectMapper.convertValue(redisService.getTempRoomSetting(inviteCode),
            SettingRoomDto.class);

        // 2. player List 가져와야 한다.
        List<PlayerDto> playerList = redisService.getTempRoomPlayer(inviteCode);
        // Map 형태에 넣어줘야 한다.
        TempRoomDto tempRoomDto = new TempRoomDto();
        tempRoomDto.setSetting(settingRoom);
        tempRoomDto.setPlayers(playerList);
        System.out.println(playerList);
        System.out.println(tempRoomDto);
        return tempRoomDto;
    }

}
