package com.oomool.api.domain.room.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.dto.TempRoomDto;
import com.oomool.api.domain.room.exception.NotTempRoomMasterException;
import com.oomool.api.domain.room.util.TempRoomMapper;
import com.oomool.api.domain.room.util.UniqueCodeGenerator;
import com.oomool.api.global.util.CustomDateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TempRoomRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final TempRoomMapper tempRoomMapper;

    // ================    Service Layer   ==================

    /**
     * 방장은 대기방을 Redis에 생성한다.
     *
     * @param settingOptionDto 대기방 설정 정보
     * @param masterUserId 방장 게임 프로필
     * */
    public Map<String, Object> createTempRoom(SettingOptionDto settingOptionDto, int masterUserId) throws
        JsonProcessingException {
        String inviteCode = UniqueCodeGenerator.generateRandomString(15);  // 대기방 초대코드 생성기

        saveTempRoomSetting(inviteCode, settingOptionDto, masterUserId); // USER DTO 는 master 권한을 갖는다.

        Map<String, Object> settingOption = getTempRoomSetting(inviteCode);
        return Map.of(
            "invite_code", inviteCode,
            "created_at", settingOption.get("createdAt"),
            "master_id", settingOption.get("masterId"),
            "setting", tempRoomMapper.mapToSettingOptionDto(settingOption)
        );
    }

    /**
     * 플레이어는 대기방에 입장한다.
     *
     * @param inviteCode 대기방 초대 코드
     * @param playerDto 참여자 게임 프로필
     * */
    public Map<String, Object> joinTempRoom(String inviteCode, PlayerDto playerDto) throws JsonProcessingException {
        // 입장 여부 검증 로직 필요
        saveTempRoomPlayer(inviteCode, playerDto);
        return Map.ofEntries(Map.entry("players", getTempRoomPlayerList(inviteCode)));
    }

    /**
     * 초대코드로 대기방의 정보를 조회한다.
     *
     * @param inviteCode 대기방 초대코드
     * */
    public TempRoomDto getTempRoom(String inviteCode) throws JsonProcessingException {

        Map<String, Object> map = getTempRoomSetting(inviteCode);

        return TempRoomDto.builder()
            .inviteCode(inviteCode)
            .createdAt(CustomDateUtil.parseDateTime((String)map.get("createdAt")))
            .masterId(Integer.parseInt((String)map.get("masterId")))
            .setting(tempRoomMapper.mapToSettingOptionDto(map))
            .players(getTempRoomPlayerList(inviteCode))
            .build();
    }

    /**
     * 방장은 대기방을 삭제한다.
     *
     * @param inviteCode 대기방 초대코드
     * @param masterUserId 방장의 유저아이디
     * */
    public String deleteTempRoom(String inviteCode, int masterUserId) {

        // 방장의 권한이 아닐경우 (플레이어일 경우)
        if (!isMasterInTempRoom(inviteCode, masterUserId)) {
            throw new NotTempRoomMasterException("플레이어는 대기방을 삭제할 수 없습니다.");
        }
        // 방장 일 경우 - 삭제 권한 존재
        deleteTempRoomByMaster(inviteCode);
        return inviteCode + " 대기방 삭제를 완료했습니다";
    }

    /**
     * 플레이어는 대기방을 퇴장한다.
     *
     *
     * @param inviteCode 초대코드
     * @param userId 유저 ID
     * */
    public String exitTempRoom(String inviteCode, int userId) {

        // 유저가 초대코드의 방장 권한이 있다면 퇴장할 수 없다.
        if (isMasterInTempRoom(inviteCode, userId)) {
            throw new NotTempRoomMasterException("방장은 대기방에서 퇴장할 수 없습니다.");
        }
        // 유저가 방장이 아닌 경우 방을 나간다.
        deleteTempRoomByPlayer(inviteCode, userId);
        return "퇴장이 완료되었습니다.";
    }

    /**
     * 대기방의 플레이어 프로필을 수정한다.
     *
     * @param inviteCode 초대코드
     * @param requestUpdateProfileDto 수정할 대상의 프로필 정보
     * */
    public PlayerDto modifyPlayerProfile(String inviteCode, PlayerDto requestUpdateProfileDto) throws
        JsonProcessingException {
        int userId = requestUpdateProfileDto.getUserId();
        // Redis에서 수정은 "덮어쓰기"
        HashOperations<String, Integer, Object> hashOps = redisTemplate.opsForHash();
        hashOps.put("roomPlayers:" + inviteCode, userId, tempRoomMapper.playerDtoToString(requestUpdateProfileDto));
        return tempRoomMapper.objectToPlayerDto(hashOps.get("roomPlayers:" + inviteCode, userId));
    }

    // ================    비즈니스 Layer   ==================

    /**
     * 방장인지 플레이어인지 검증한다.
     *
     * @param inviteCode 초대코드
     * @param playerUserId 플레이어의 유저 아이디
     * */
    public boolean isMasterInTempRoom(String inviteCode, int playerUserId) {
        // 초대코드에 대한 방장 권한 조회
        int masterId = Integer.parseInt(getTempRoomSettingValue(inviteCode, "masterId")); // hash 조회
        if (masterId == playerUserId) {
            return true;
        }
        return false;
    }

    // ================      Redis CRUD     ==================

    /**
     * Redis에 대기방 설정 정보를 저장 한다. (일시적 저장)
     *
     * @param inviteCode 대기방 초대코드
     * @param settingRoomDto 방장이 설정한 방 정보
     * @param masterId 방장의 userId
     * */
    public void saveTempRoomSetting(String inviteCode, SettingOptionDto settingRoomDto, int masterId) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();

        Map<String, Object> settingRoomMap = tempRoomMapper.settingRoomDtoToMap(settingRoomDto);

        settingRoomMap.put("inviteCode", inviteCode);
        settingRoomMap.put("createdAt", CustomDateUtil.convertDateTimeToString(LocalDateTime.now()));
        settingRoomMap.put("masterId", Integer.toString(masterId));

        hashOps.putAll("roomSetting:" + inviteCode, settingRoomMap);
    }

    /**
     * Redis에 대기방 플레이어를 저장한다.
     *
     * @param inviteCode 초대코드
     * @param playerDto 플레이어 프로필 정보
     * */
    public void saveTempRoomPlayer(String inviteCode, PlayerDto playerDto) {
        // 유저 기준 참여하고 있는 대기방 현황이 필요
        saveUserTempRoom(inviteCode, playerDto.getUserId());
        HashOperations<String, Integer, Object> hashOps = redisTemplate.opsForHash();
        hashOps.put("roomPlayers:" + inviteCode, playerDto.getUserId(), tempRoomMapper.playerDtoToString(playerDto));
    }

    /**
     * Redis에 유저가 참여하고 있는 대기방을 저장한다. (유저는 여러개의 대기방에 참여한다.)
     *
     * @param inviteCode 초대코드
     * @param userId 유저 아이디
     * */
    public void saveUserTempRoom(String inviteCode, int userId) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.add("userInviteTemp:" + userId, inviteCode);
    }

    /**
     * Redis에 대기방의 설정 정보를 조회한다.
     * - createdAt과 inviteCode 등 설정 관련 전체 정보 조회
     *
     * @param inviteCode 초대코드
     * */
    public Map<String, Object> getTempRoomSetting(String inviteCode) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries("roomSetting:" + inviteCode);
    }

    /**
     * Redis에 대기방에 참여하고 있는 전체 플레이어 목록을 조회한다.
     *
     * @param inviteCode 초대코드
     * */
    public Map<Integer, Object> getTempRoomPlayerMap(String inviteCode) {
        HashOperations<String, Integer, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries("roomPlayers:" + inviteCode);
    }

    /**
     * Redis의 설정 값들의 한 개의 key값에 대한 조회를 한다.
     *
     * @param inviteCode 초대코드
     * @param key 키 [ title, startDate, endDate, questionType, maxMember, inviteCode, createdAt, masterId ]
     * */
    public String getTempRoomSettingValue(String inviteCode, String key) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        Object settingValue = hashOps.get("roomSetting:" + inviteCode, key);

        if (settingValue == null) {
            throw new NoSuchElementException("해당 키에 대한 설정값이 존재하지 않습니다.");
        }

        return settingValue.toString();
    }

    /**
     * Redis에 유저 기준 참여하고 있는 대기방 목록을 조회한다. (유저별 대기방코드 조회)
     *
     * @param userId 유저 ID
     * */
    public List<String> getUserInviteTempRoomList(int userId) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        Set<Object> inviteCodeSet = setOps.members("userInviteTemp:" + userId);

        if (inviteCodeSet == null || inviteCodeSet.isEmpty()) {
            throw new NoSuchElementException(userId + "에 해당하는 대기방이 존재하지 않습니다."); // 추후 수정해야함
        }

        return inviteCodeSet.stream()
            .filter(object -> object instanceof String)
            .map(object -> (String)object)
            .collect(Collectors.toList());
    }

    /**
     * Redis에 invitecode를 삭제한다. - 대기방 전체 삭제
     *
     * @param inviteCode inviteCode
     * */
    public void deleteTempRoomByMaster(String inviteCode) {

        // 방 존재 여부 확인
        boolean exists = Boolean.TRUE.equals(redisTemplate.hasKey("roomSetting:" + inviteCode));
        if (!exists) {
            throw new IllegalArgumentException("유효하지 않은 초대코드 입니다.");
        }

        // redis에 저장한 player InviteCode 관리하는 set
        for (Integer userId : getTempRoomPlayerMap(inviteCode).keySet()) {
            deleteUserTempRoom(inviteCode, userId); // userInviteTemp에서 관리하는 inviteKey 삭제하기
        }
        redisTemplate.delete("roomSetting:" + inviteCode);
        redisTemplate.delete("roomPlayers:" + inviteCode);
    }

    /**
     * Redis에 inviteCode에 해당하는 Player가 나간다. - 대기방 퇴장
     *
     * @param inviteCode 초대코드
     * @param userId 플레이어에 참여하고 있는 UserId
     * */
    public void deleteTempRoomByPlayer(String inviteCode, int userId) {

        // 1. 유저 기준 - 참여 대기방 삭제
        deleteUserTempRoom(inviteCode, userId);
        // 2. 방 기준 - 참여 플레이어 삭제
        HashOperations<String, Integer, Object> hashOps = redisTemplate.opsForHash();
        hashOps.delete("roomPlayers:" + inviteCode, userId);
    }

    /**
     * 유저 기준으로 참여하고 있는 InviteCode를 관리하는 Set을 삭제한다.
     * saveUserTempRoom
     * */
    public void deleteUserTempRoom(String inviteCode, int userId) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.remove("userInviteTemp:" + userId, inviteCode);
    }

    // ================    조회 한 값 변환 (Convert)   ==================

    /**
     * 초대코드로 설정 정보를 조회하여 SettingOptionDto로 반환한다. (주요 설정 정보)
     *
     * @param inviteCode 초대코드
     * */
    public SettingOptionDto getSettingOptionDtoByInviteCode(String inviteCode) {
        return tempRoomMapper.mapToSettingOptionDto(getTempRoomSetting(inviteCode));
    }

    /**
     * 조회한 HashMap을 List PlayerDto 로 변환한다.
     *
     * @param inviteCode 초대코드
     * */
    public List<PlayerDto> getTempRoomPlayerList(String inviteCode) throws JsonProcessingException {
        List<PlayerDto> playerDtoList = new ArrayList<>();
        for (Map.Entry<Integer, Object> playerJson : getTempRoomPlayerMap(inviteCode).entrySet()) {
            playerDtoList.add(tempRoomMapper.objectToPlayerDto(playerJson.getValue()));
        }
        return playerDtoList;
    }

}
