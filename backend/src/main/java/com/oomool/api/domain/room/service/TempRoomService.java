package com.oomool.api.domain.room.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.dto.TempRoomDto;
import com.oomool.api.domain.room.util.UniqueCodeGenerator;
import com.oomool.api.global.util.CustomDateUtil;

@Service
public class TempRoomService {

    // Redis의 데이터 액세스를 담당하는 redisService 로직과 분리한다.
    private final TempRoomRedisService tempRoomRedisService;

    @Autowired
    public TempRoomService(TempRoomRedisService redisService) {
        this.tempRoomRedisService = redisService;
    }

    /**
     * 대기방 생성
     * */
    public TempRoomDto createTempRoom(SettingOptionDto roomSetting, PlayerDto masterPlayer) throws
        JsonProcessingException {

        // inviteCode 생성
        UniqueCodeGenerator generator = new UniqueCodeGenerator();
        String inviteCode = generator.generateRandomString();

        // 임시방에 데이터 저장
        tempRoomRedisService.saveTempRoomSetting(inviteCode, roomSetting, masterPlayer.getUserId());
        tempRoomRedisService.saveTempRoomPlayer(inviteCode, masterPlayer);

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
        tempRoomRedisService.saveTempRoomPlayer(inviteCode, player);
        return Map.ofEntries(Map.entry("players", tempRoomRedisService.getTempRoomPlayerList(inviteCode)));
    }

    /**
     * 대기방 조회
     * - 대기방에 대한 전체 정보를 조회한다.
     * */
    public TempRoomDto getTempRoom(String inviteCode) throws JsonProcessingException {
        CustomDateUtil customDateUtil = new CustomDateUtil();
        String createAt = (String)tempRoomRedisService.getTempRoomSettingValue(inviteCode, "createAt");
        String masterId = (String)tempRoomRedisService.getTempRoomSettingValue(inviteCode, "masterId");
        return TempRoomDto.builder()
            .inviteCode(inviteCode)
            .createAt(customDateUtil.parseDateTime(createAt))
            .masterId(Integer.parseInt(masterId))
            .setting(tempRoomRedisService.getSettingOption(inviteCode))
            .players(tempRoomRedisService.getTempRoomPlayerList(inviteCode))
            .build();
    }

}
