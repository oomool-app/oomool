package com.oomool.api.domain.room.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.repository.GameRoomRepository;
import com.oomool.api.domain.room.util.GameRoomMapper;
import com.oomool.api.domain.room.util.UniqueCodeGenerator;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;
    private final GameRoomMapper gameRoomMapper;
    private final TempRoomRedisService tempRoomRedisService;

    /**
     * 문답방 생성
     */
    public String createGameRoom(String inviteCode) {

        // 문답방 key 만들기 - inviteCode랑 다르게 생성해줘야한다.
        UniqueCodeGenerator uniqueCodeGenerator = new UniqueCodeGenerator();
        String roomUid = uniqueCodeGenerator.generateRandomString();

        // 방 정보 조회
        GameRoom gameRoom = gameRoomMapper.dtoToEntity(roomUid, tempRoomRedisService.getSettingOption(inviteCode));
        gameRoomRepository.save(gameRoom);
        return roomUid;
    }

    /**
     * 문답방 상세 정보 조회
     * */
    public Map<String, Object> getGameRoom(String roomUid) {

        // 문답방 setting 데이터 조회
        GameRoom gameRoom = gameRoomRepository.findByRoomUid(roomUid)
            .orElseThrow(() -> new EntityNotFoundException("room UID가 존재하지 않습니다."));

        SettingOptionDto settingOptionDto = gameRoomMapper.convertSettingOptionDto(gameRoom);

        return Map.of(
            "room_uid", roomUid,
            "create_at", gameRoom.getCreateAt(),
            "setting", settingOptionDto,
            "players", gameRoom.getPlayers());
    }

    /**
     * 문답방 설정값 조회
     * */
    public SettingOptionDto getSeeingOptionDto(String roomUid) {

        GameRoom gameRoom = gameRoomRepository.findByRoomUid(roomUid)
            .orElseThrow(() -> new EntityNotFoundException("room UID가 존재하지 않습니다."));

        return gameRoomMapper.convertSettingOptionDto(gameRoom);
    }

}
