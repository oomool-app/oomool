package com.oomool.api.domain.room.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.oomool.api.domain.room.util.DateUtil;

@Service
public class TempRoomRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TempRoomRedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 대기방 설정 정보 저장
     * */
    public void saveTempRoomSetting(String inviteCode, SettingOptionDto settingRoomDto, int masterId) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();

        ObjectMapper objectMapper = new ObjectMapper();
        DateUtil dateUtil = new DateUtil();
        Map<String, Object> settingRoomMap = objectMapper.convertValue(settingRoomDto,
            new TypeReference<>() {
            });

        settingRoomMap.put("inviteCode", inviteCode);
        settingRoomMap.put("createAt", dateUtil.convertDateTimeToString(LocalDateTime.now()));
        settingRoomMap.put("masterId", Integer.toString(masterId));

        hashOps.putAll("roomSetting:" + inviteCode,
            settingRoomMap); // String : hash Set [inviteCode : Hash (temp Room 정보)]
    }

    /**
     * 대기방 플레이어 저장
     * */
    public void saveTempRoomPlayer(String inviteCode, PlayerDto player) throws JsonProcessingException {
        // player가 현재 참여하고 있는 대기방코드 추가
        saveUserTempRoom(inviteCode, player.getUserId());
        // 대기방 코드에 player 정보 추가
        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        ObjectMapper objectMapper = new ObjectMapper();
        String playerJson = objectMapper.writeValueAsString(player);
        listOps.rightPush("roomPlayers:" + inviteCode,
            playerJson); // String : List [inviteCode : List(Player) (player는 String으로)]
    }

    /**
     * player 기준 참여하고 있는 대기방 저장
     * */
    public void saveUserTempRoom(String inviteCode, int userId) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.add("userInviteTemp:" + userId, inviteCode);
    }

    /**
     * 대기방 설정 정보 저장
     * */
    public Map<String, Object> getTempRoomSetting(String inviteCode) {
        // Setting에 해당하는 정보를 가져온다.
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries("roomSetting:" + inviteCode);
    }

    /**
     * get Player List Information
     * saveUserTempRoom
     * */
    public List<PlayerDto> getTempRoomPlayerList(String inviteCode) throws JsonProcessingException {
        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        ObjectMapper objectMapper = new ObjectMapper();
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
     * 대기방 설정 정보 조회
     * */
    public SettingOptionDto getSettingOption(String inviteCode) {
        Map<String, Object> tempRoomSetting = getTempRoomSetting(inviteCode); // redis 정보 호출

        // settingOptionDto에 해당하지 않는 정보 제외
        tempRoomSetting.remove("inviteCode");
        tempRoomSetting.remove("createAt");
        tempRoomSetting.remove("masterId");

        // mapper 반환
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(tempRoomSetting, SettingOptionDto.class);
    }

    /**
     * 대기방 설정의 일부 정보 조회
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
     * 유저 기준 참여하고 있는 대기방 목록 조회
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

    /**
     * 대기방에서 문답방 넘어갈 경우
     * player들이 참여하고 있는 현재 대기방을 삭제한다.
     */
    public void removeUserTempRoom(String inviteCode, int userId) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.remove("userInviteTemp:" + userId, inviteCode);
    }

}
