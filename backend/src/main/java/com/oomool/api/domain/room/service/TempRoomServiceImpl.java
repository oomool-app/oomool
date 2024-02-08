package com.oomool.api.domain.room.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.dto.TempRoomDto;
import com.oomool.api.domain.room.util.TempRoomMapper;
import com.oomool.api.domain.room.util.UniqueCodeGenerator;
import com.oomool.api.global.config.redis.RedisService;
import com.oomool.api.global.exception.BaseException;
import com.oomool.api.global.exception.StatusCode;
import com.oomool.api.global.util.CustomDateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TempRoomServiceImpl implements TempRoomService {

    private final RedisService redisService;
    private final TempRoomMapper tempRoomMapper;

    // PREFIX
    private final String prefixSettingOption = "roomSetting:";
    private final String prefixPlayers = "roomPlayers:";
    private final String prefixUserTempRoom = "userInviteTemp:";

    @Override
    public Map<String, Object> createTempRoom(SettingOptionDto settingOptionDto, int masterUserId) {

        //  초대코드를 생성
        String inviteCode = UniqueCodeGenerator.generateRandomString(15);  // 대기방 초대코드 생성기

        // Redis 에 넣을 추가정보 생성
        Map<String, Object> settingRoomMap = tempRoomMapper.settingRoomDtoToMap(settingOptionDto);
        settingRoomMap.put("inviteCode", inviteCode);
        settingRoomMap.put("createdAt", CustomDateUtil.convertDateTimeToString(LocalDateTime.now()));
        settingRoomMap.put("masterId", Integer.toString(masterUserId));

        // Redis에 저장
        redisService.saveAllHashOperation(prefixSettingOption + inviteCode, settingRoomMap);

        // Redis에 저장한 결과 조회
        Map<String, Object> settingOptionMap = redisService.getHashOperationByString(prefixSettingOption + inviteCode);
        return Map.of(
            "invite_code", settingOptionMap.get("inviteCode"),
            "create_at", settingOptionMap.get("createdAt"),
            "master_id", settingOptionMap.get("masterId"),
            "setting", tempRoomMapper.mapToSettingOptionDto(settingOptionMap)
        );
    }

    @Override
    public Map<String, Object> joinTempRoom(String inviteCode, PlayerDto playerDto) {

        // 생성되지 않은 방
        if (!redisService.hasKey(prefixSettingOption + inviteCode)) {
            throw new BaseException(StatusCode.INVALID_INVITE_CODE);
        }
        // 이미 참여하고 있는지 확인
        if (!redisService.hasValueOperation(prefixUserTempRoom + playerDto.getUserId(), inviteCode)) {
            throw new BaseException(StatusCode.DUPLICATION_INVITE_CODE);
        }

        // 1. User 기준으로 inviteCode 를 관리하는 Redis에 저장한다.
        redisService.saveSetOperation(prefixUserTempRoom + playerDto.getUserId(), inviteCode);

        // 2. inivteRoom을 기준으로 Player를 저장한다.
        redisService.saveHashOperation(
            prefixPlayers,
            playerDto.getUserId(),
            tempRoomMapper.playerDtoToString(playerDto)
        );

        // 3. 현재까지 플레이어 결과 반환
        Map<Integer, Object> totalPlayerMap = redisService.getHashOperationByInteger(prefixPlayers + inviteCode);
        List<PlayerDto> playerDtoList = tempRoomMapper.objectToPlayerDtoList(totalPlayerMap);
        return Map.ofEntries(
            Map.entry("players", playerDtoList)
        );
    }

    @Override
    public TempRoomDto getTempRoomDetail(String inviteCode) {

        // 1. 대기방 설정정보 조회
        Map<String, Object> settingOptionMap = redisService.getHashOperationByString(prefixSettingOption + inviteCode);
        if (settingOptionMap == null) {
            throw new BaseException(StatusCode.INVALID_INVITE_CODE);
        }

        // 2. 대기방 플레이어 정보 조회
        List<PlayerDto> playerDtoList = tempRoomMapper.objectToPlayerDtoList(
            redisService.getHashOperationByInteger(prefixPlayers + inviteCode)
        );

        // 대기방 전체 결과 조회
        return tempRoomMapper.mapToTempRoomDto(settingOptionMap, playerDtoList);
    }

    @Override
    public String deleteTempRoom(String inviteCode, int userId) {

        // 방 존재 여부 확인
        if (!redisService.hasKey(prefixSettingOption + inviteCode)) {
            throw new BaseException(StatusCode.INVALID_INVITE_CODE);
        }

        // 방장의 권한이 아닐경우 (플레이어일 경우)
        // TODO

        // redis에 저장한 player InviteCode를 관리하는 Set에서 key를 삭제한다.
        Map<Integer, Object> playerMap = redisService.getHashOperationByInteger(prefixSettingOption + inviteCode);
        // if (Integer userId : playerMap.keySet()) {
        //     redisService.deleteSetOperation(prefixUserTempRoom + userId, inviteCode);
        // }
        // INVITECode에 대한 정보를 삭제한다.
        redisService.deleteKey(prefixSettingOption + inviteCode);
        redisService.deleteKey(prefixPlayers + inviteCode);
        return inviteCode + " 대기방 삭제를 완료했습니다.";
    }

    @Override
    public String exitTempRoom(String inviteCode, int userId) {

        // 유저가 초대코드의 방장 권한이 있다면 퇴장할 수 없다.

        // 1. 유저 기준 참여하고 있는 대기방 초대코드 삭제 (value가 초대코드)
        redisService.deleteSetOperation(prefixUserTempRoom + userId, inviteCode);

        // 2. 방 기준 - 참여 플레이어 삭제
        redisService.deleteKeyOperation(prefixPlayers + inviteCode, userId);

        return "퇴장이 완료되었습니다.";
    }

    @Override
    public PlayerDto modifyPlayerProfile(String inviteCode, PlayerDto requestUpdateProfileDto) {
        return null;
    }

    @Override
    public SettingOptionDto modifyTempRoomSettingOption(String inviteCode,
        SettingOptionDto requestUpdateSettingOptionDto) {
        return null;
    }
}
