package com.oomool.api.domain.room.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingRoomDto;

@Service
public class TempRoomRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TempRoomRedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveTempRoomInfo(String inviteCode, String value) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(inviteCode, value);
    }

    public void saveTempRoomSetting(String inviteCode, SettingRoomDto settingRoomDto, int masterId) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();

        // Dto 객체를 Map으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> settingRoomMap = objectMapper.convertValue(settingRoomDto,
            new TypeReference<>() {
            });

        // Redis Key (TEMPROOM PREFIX)
        hashOps.putAll("roomSetting:" + inviteCode,
            settingRoomMap); // String : hash Set [inviteCode : Hash (temp Room 정보)]
    }

    public void saveTempRoomPlayer(String inviteCode, PlayerDto player) throws JsonProcessingException {
        // player가 현재 참여하고 있는 대기방코드 추가
        savePlayerInviteCode(inviteCode, player.getUserId());
        // 대기방 코드에 player 정보 추가
        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        ObjectMapper objectMapper = new ObjectMapper();
        String playerJson = objectMapper.writeValueAsString(player);
        listOps.rightPush("roomPlayers:" + inviteCode,
            playerJson); // String : List [inviteCode : List(Player) (player는 String으로)]
    }

    /**
     * player 기준 참여하고 있는 대기방
     * - Set에 inviteCode 관리
     * */
    public void savePlayerInviteCode(String inviteCode, int userId) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.add("playerInvite:" + userId, inviteCode);
    }

    public Object getTempRoomInfo(String inviteCode) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        return valueOps.get(inviteCode);
    }

    /**
     * get Temp Room Information
     * */
    public Object getTempRoomSetting(String inviteCode) {
        // Setting에 해당하는 정보를 가져온다.
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries("roomSetting:" + inviteCode);
    }

    /**
     * get Player List Information
     * */
    public List<PlayerDto> getTempRoomPlayer(String inviteCode) throws JsonProcessingException {
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
     * 대기방에서 문답방 넘어갈 경우
     * player들이 참여하고 있는 현재 대기방을 삭제한다.
     */
    public void removePlayerInviteCode(String inviteCode, int userId) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.remove("playerInvite:" + userId, inviteCode);
    }

}
