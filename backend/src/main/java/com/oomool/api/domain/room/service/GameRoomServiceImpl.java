package com.oomool.api.domain.room.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.repository.GameRoomRepository;
import com.oomool.api.domain.room.util.GameRoomMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameRoomServiceImpl implements GameRoomService {

    private final GameRoomRepository gameRoomRepository;
    private final GameRoomMapper gameRoomMapper;

    @Override
    public void createGameRoom(String roomUid, SettingOptionDto settingOptionDto) {
        GameRoom gameRoom = gameRoomMapper.dtoToEntity(roomUid, settingOptionDto);
        gameRoomRepository.save(gameRoom);
    }

    @Override
    public GameRoom getGameRoom(String roomUid) {
        return gameRoomRepository.findByRoomUid(roomUid)
            .orElseThrow(() -> new EntityNotFoundException("room UID가 존재하지 않습니다."));
    }

    @Override
    public Map<String, Object> getGameRoomDetail(String roomUid) {
        GameRoom gameRoom = getGameRoom(roomUid);
        SettingOptionDto settingOptionDto = gameRoomMapper.entityToSettingOptionDto(gameRoom);
        return Map.of(
            "room_uid", roomUid,
            "create_at", gameRoom.getCreateAt(),
            "setting", settingOptionDto,
            "players", gameRoom.getPlayers()
        );
    }

    @Override
    public SettingOptionDto getSettingOptionDto(String roomUid) {
        return gameRoomMapper.entityToSettingOptionDto(getGameRoom(roomUid));
    }
}
