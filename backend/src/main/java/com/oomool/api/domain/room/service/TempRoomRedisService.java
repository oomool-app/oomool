package com.oomool.api.domain.room.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.dto.TempRoomDto;
import com.oomool.api.domain.room.util.UniqueCodeGenerator;
import com.oomool.api.global.util.CustomDateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TempRoomRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 방장은 대기방을 Redis에 생성한다.
     *
     * @param settingOptionDto 대기방 설정 정보
     * @param masterPlayerDto 방장 게임 프로필
     * */
    public TempRoomDto createTempRoom(SettingOptionDto settingOptionDto, PlayerDto masterPlayerDto) throws
        JsonProcessingException {

        String inviteCode = UniqueCodeGenerator.generateRandomString(15);  // 대기방 초대코드 생성기

        saveTempRoomSetting(inviteCode, settingOptionDto, masterPlayerDto.getUserId());
        saveTempRoomPlayer(inviteCode, masterPlayerDto);

        return getTempRoom(inviteCode);
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
            .setting(getSettingOptionDtoByMap(map))
            .players(getTempRoomPlayerList(inviteCode))
            .build();
    }

    /**
     * Redis에 대기방 설정 정보를 저장 한다. (일시적 저장)
     *
     * @param inviteCode 대기방 초대코드
     * @param settingRoomDto 방장이 설정한 방 정보
     * @param masterId 방장의 userId
     * */
    public void saveTempRoomSetting(String inviteCode, SettingOptionDto settingRoomDto, int masterId) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();

        Map<String, Object> settingRoomMap = objectMapper.convertValue(settingRoomDto,
            new TypeReference<>() {
            });

        settingRoomMap.put("inviteCode", inviteCode);
        settingRoomMap.put("createAt", CustomDateUtil.convertDateTimeToString(LocalDateTime.now()));
        settingRoomMap.put("masterId", Integer.toString(masterId));

        hashOps.putAll("roomSetting:" + inviteCode, settingRoomMap);
    }

    /**
     * Redis에 대기방 플레이어를 저장한다.
     *
     * @param inviteCode 초대코드
     * @param player 플레이어 프로필 정보
     * */
    public void saveTempRoomPlayer(String inviteCode, PlayerDto player) {
        // == player가 현재 참여하고 있는 대기방코드 추가 ==
        // 유저 기준 참여하고 있는 대기방 현황이 필요
        saveUserTempRoom(inviteCode, player.getUserId());

        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        String playerJson;
        try {
            playerJson = objectMapper.writeValueAsString(player);  // 플레이어 정보 추가
        } catch (JsonProcessingException e) {
            // TODO :: Global Exception 처리하기
            e.printStackTrace();
            playerJson = "{}";
        }
        listOps.rightPush("roomPlayers:" + inviteCode, playerJson);
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
    public List<PlayerDto> getTempRoomPlayerList(String inviteCode) throws JsonProcessingException {
        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        List<Object> playerJsonList = listOps.range("roomPlayers:" + inviteCode, 0, -1);
        List<PlayerDto> playerList = new ArrayList<>();
        if (playerJsonList != null) {
            for (Object playerJson : playerJsonList) {
                PlayerDto player = objectMapper.readValue((String)playerJson, PlayerDto.class);
                playerList.add(player);
            }
        }
        return playerList;
    }

    /**
     * Redis의 설정 값들의 한 개의 key값에 대한 조회를 한다.
     *
     * @param inviteCode 초대코드
     * @key 키 [ title, startDate, endDate, questionType, maxMember, inviteCode, createdAt, masterId ]
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

        assert inviteCodeSet != null;
        if (inviteCodeSet.isEmpty()) {
            throw new NoSuchElementException(userId + "에 해당하는 대기방이 존재하지 않습니다."); // 추후 수정해야함
        }

        return inviteCodeSet.stream()
            .filter(object -> object instanceof String)
            .map(object -> (String)object)
            .collect(Collectors.toList());
    }

    // ================    조회 한 값 변환 (Convert)   ==================

    /**
     * Redis의 대기방 설정정보를 SettingOptionDto로 반환한다. (주요 설정 정보)
     * - Redis에 있는 roomSetting 전체 key-value를 가져온다.
     * - SettingOptionDto로 필요한 정보만 반환한다.
     *
     * @param tempRoomSetting 전체 방 설정 정보
     * */
    public SettingOptionDto getSettingOptionDtoByMap(Map<String, Object> tempRoomSetting) {

        // settingOptionDto에 해당하지 않는 정보 제외
        tempRoomSetting.remove("inviteCode");
        tempRoomSetting.remove("createAt");
        tempRoomSetting.remove("masterId");

        // mapper 반환
        return objectMapper.convertValue(tempRoomSetting, SettingOptionDto.class);
    }

    /**
     * 초대코드로 설정 정보를 조회하여 SettingOptionDto로 반환한다. (주요 설정 정보)
     *
     * @param inviteCode 초대코드
     * */
    public SettingOptionDto getSettingOptionDtoByInviteCode(String inviteCode) {
        return getSettingOptionDtoByMap(getTempRoomSetting(inviteCode));
    }

}
