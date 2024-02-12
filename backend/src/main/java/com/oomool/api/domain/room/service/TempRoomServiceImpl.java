package com.oomool.api.domain.room.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.constant.TempRoomPrefix;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.dto.TempRoomBanRequestDto;
import com.oomool.api.domain.room.dto.TempRoomDto;
import com.oomool.api.domain.room.util.TempRoomMapper;
import com.oomool.api.domain.room.util.UniqueCodeGenerator;
import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.domain.user.service.UserService;
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
    private final UserService userService;

    @Override
    public Map<String, Object> createTempRoom(SettingOptionDto settingOptionDto, int masterUserId) {

        //  초대코드를 생성
        String inviteCode = UniqueCodeGenerator.generateRandomString(15);  // 대기방 초대코드 생성기

        // Redis 에 넣을 추가정보 생성 (방설정값 + 초대코드 + 생성 시각 + 방장아이디)
        Map<String, Object> settingRoomMap = tempRoomMapper.settingRoomDtoToMap(settingOptionDto);
        settingRoomMap.put("inviteCode", inviteCode);
        settingRoomMap.put("createdAt", CustomDateUtil.convertDateTimeToString(LocalDateTime.now()));
        settingRoomMap.put("masterId", Integer.toString(masterUserId));

        // Redis에 저장
        redisService.saveAllHashOperation(TempRoomPrefix.SETTING_OPTION + inviteCode, settingRoomMap);

        // Redis에 저장한 결과 조회
        Map<String, Object> settingOptionMap = redisService.getHashOperationByString(
            TempRoomPrefix.SETTING_OPTION + inviteCode);
        // [임시 - StartCheck] - 대기방에서 시작 여부를 체킹
        redisService.saveValueOperation(TempRoomPrefix.START_CHECK + inviteCode, "false");

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
        if (!redisService.hasKey(TempRoomPrefix.SETTING_OPTION + inviteCode)) {
            throw new BaseException(StatusCode.INVALID_INVITE_CODE);
        }
        // 이미 참여하고 있는지 확인
        if (redisService.hasValueOperation(TempRoomPrefix.USER_INVITE_TEMPROOM + playerDto.getUserId(), inviteCode)) {
            throw new BaseException(StatusCode.DUPLICATION_INVITE_CODE);
        }
        // 강퇴 이력이 있는 사용자는 참여 불가
        if (redisService.hasKey(TempRoomPrefix.BAN_LIST + inviteCode, playerDto.getUserId())) {
            throw new BaseException(StatusCode.CANNOT_ACCESS_TEMP_ROOM);
        }

        // 1. User 기준으로 inviteCode 를 관리하는 Redis에 저장한다.
        redisService.saveSetOperation(TempRoomPrefix.USER_INVITE_TEMPROOM + playerDto.getUserId(), inviteCode);

        // 2. inviteRoom을 기준으로 Player를 저장한다.
        redisService.saveHashOperation(TempRoomPrefix.PLAYERS + inviteCode, playerDto.getUserId(),
            tempRoomMapper.playerDtoToString(playerDto));

        // 3. 현재까지 플레이어 결과 반환
        Map<Integer, Object> totalPlayerMap = redisService.getHashOperationByInteger(
            TempRoomPrefix.PLAYERS + inviteCode);
        List<PlayerDto> playerDtoList = tempRoomMapper.objectToPlayerDtoList(totalPlayerMap);

        return Map.ofEntries(Map.entry("players", playerDtoList));
    }

    @Override
    public TempRoomDto getTempRoomDetail(String inviteCode) {

        // 1. 대기방 설정정보 조회
        Map<String, Object> settingOptionMap = redisService.getHashOperationByString(
            TempRoomPrefix.SETTING_OPTION + inviteCode);
        if (settingOptionMap == null) {
            throw new BaseException(StatusCode.INVALID_INVITE_CODE);
        }

        // 2. 대기방 플레이어 정보 조회
        List<PlayerDto> playerDtoList = tempRoomMapper.objectToPlayerDtoList(
            redisService.getHashOperationByInteger(TempRoomPrefix.PLAYERS + inviteCode)
        );

        // 3. 강퇴 유저 정보 조회
        List<UserDto> banUserDtoList = tempRoomMapper.objectToUserDtoList(
            redisService.getHashOperationByInteger(TempRoomPrefix.BAN_LIST + inviteCode)
        );

        // 대기방 전체 결과 조회
        return tempRoomMapper.mapToTempRoomDto(settingOptionMap, playerDtoList, banUserDtoList);
    }

    @Override
    public String deleteTempRoom(String inviteCode, int userId) {

        // 방 존재 여부 확인
        if (!redisService.hasKey(TempRoomPrefix.SETTING_OPTION + inviteCode)) {
            throw new BaseException(StatusCode.INVALID_INVITE_CODE);
        }

        // 방장의 권한이 아닐경우 (플레이어일 경우)
        if (!validateTempRoomMaster(inviteCode, userId)) {
            throw new BaseException(StatusCode.NOT_MASTER_AUTH_TEMPROOM);
        }

        // redis에 저장한 User 기준 InviteCode를 관리하는 Set에서 key를 삭제한다.
        // 참여하고 있는 모든 플레이어의 유저 - 대기방 코드 삭제
        Map<Integer, Object> playerMap = redisService.getHashOperationByInteger(TempRoomPrefix.PLAYERS + inviteCode);
        playerMap.keySet().forEach(
            playerUserId ->
                redisService.deleteSetOperation(TempRoomPrefix.USER_INVITE_TEMPROOM + playerUserId, inviteCode));

        // invite Code에 대한 정보를 삭제한다.
        redisService.deleteKey(TempRoomPrefix.SETTING_OPTION + inviteCode);
        redisService.deleteKey(TempRoomPrefix.PLAYERS + inviteCode);
        redisService.deleteKey(TempRoomPrefix.BAN_LIST + inviteCode);
        return inviteCode + " 대기방 삭제를 완료했습니다.";
    }

    @Override
    public String exitTempRoom(String inviteCode, int userId) {

        // 유저가 초대코드의 방장 권한이 있다면 퇴장할 수 없다.
        if (validateTempRoomMaster(inviteCode, userId)) {
            throw new BaseException(StatusCode.FORBIDDEN, "플레이어는 방을 퇴장할 수 없습니다.");
        }

        // 1. 유저 기준 참여하고 있는 대기방 초대코드 삭제 (value가 초대코드)
        redisService.deleteSetOperation(TempRoomPrefix.USER_INVITE_TEMPROOM + userId, inviteCode);

        // 2. 방 기준 - 참여 플레이어 삭제
        redisService.deleteKeyOperation(TempRoomPrefix.PLAYERS + inviteCode, userId);

        return "퇴장이 완료되었습니다.";
    }

    @Override
    public PlayerDto modifyPlayerProfile(String inviteCode, PlayerDto requestUpdateProfileDto) {

        // Redis에서 "수정" = 덮어쓰기
        redisService.saveHashOperation(
            TempRoomPrefix.PLAYERS + inviteCode,
            requestUpdateProfileDto.getUserId(),
            tempRoomMapper.playerDtoToString(requestUpdateProfileDto)
        );

        Object updatePlayerJson = redisService.getHashOperation(TempRoomPrefix.PLAYERS + inviteCode,
            requestUpdateProfileDto.getUserId());
        return tempRoomMapper.objectToPlayerDto(updatePlayerJson);
    }

    @Override
    public SettingOptionDto modifyTempRoomSettingOption(String inviteCode,
        SettingOptionDto requestUpdateSettingOptionDto) {

        // TODO :: 방장 검증

        Map<String, Object> requestUpdateSettingOptionMap = tempRoomMapper.settingRoomDtoToMap(
            requestUpdateSettingOptionDto);
        requestUpdateSettingOptionMap.forEach((settingKey, settingValue) -> {
            if (settingValue != null) {
                redisService.saveHashOperation(TempRoomPrefix.SETTING_OPTION + inviteCode, settingKey, settingValue);
            }
        });

        Map<String, Object> updateSettingOptionMap = redisService.getHashOperationByString(
            TempRoomPrefix.SETTING_OPTION + inviteCode);
        return tempRoomMapper.mapToSettingOptionDto(updateSettingOptionMap);
    }

    @Override
    public String addBanList(String inviteCode, TempRoomBanRequestDto requestBanDto) {

        // 방장 검증 - 방장일경우만 Ban 추가할 권한 존재
        if (!validateTempRoomMaster(inviteCode, requestBanDto.userId())) {
            throw new BaseException(StatusCode.NOT_MASTER_AUTH_TEMPROOM, "방장이 아닙니다.");
        }
        // 강퇴인원을 추가한다.
        UserDto banUserDto = userService.searchByUserId(requestBanDto.banUserId());
        redisService.saveHashOperation(TempRoomPrefix.BAN_LIST + inviteCode, requestBanDto.banUserId(),
            tempRoomMapper.userDtoToString(banUserDto));
        // 플레이어 리스트에서 제외한다.
        redisService.deleteKeyOperation(TempRoomPrefix.PLAYERS + inviteCode, requestBanDto.banUserId());
        redisService.deleteSetOperation(TempRoomPrefix.USER_INVITE_TEMPROOM + requestBanDto.banUserId(), inviteCode);
        return banUserDto.getUsername() + "님이 강제 퇴장되었습니다.";
    }

    @Override
    public boolean validateTempRoomMaster(String inviteCode, int userId) {
        String masterId = redisService.getHashOperation(TempRoomPrefix.SETTING_OPTION + inviteCode, "masterId")
            .toString();
        if (Integer.toString(userId).equals(masterId)) {
            return true;
        }
        return false;
    }

    @Override
    public String startCheck(String inviteCode) {
        // 임시
        return redisService.getValueOperation(TempRoomPrefix.START_CHECK + inviteCode);
    }

}
