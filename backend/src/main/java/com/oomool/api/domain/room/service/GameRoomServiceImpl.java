package com.oomool.api.domain.room.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.player.entity.Avatar;
import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.player.service.AvatarServiceImpl;
import com.oomool.api.domain.player.util.PlayerMapper;
import com.oomool.api.domain.room.constant.TempRoomPrefix;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.dto.TempRoomDto;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.repository.GameRoomRepository;
import com.oomool.api.domain.room.util.GameRoomMapper;
import com.oomool.api.domain.room.util.MatchManittiUtil;
import com.oomool.api.domain.room.util.UniqueCodeGenerator;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.service.UserService;
import com.oomool.api.global.config.redis.RedisService;
import com.oomool.api.global.exception.BaseException;
import com.oomool.api.global.exception.StatusCode;
import com.oomool.api.global.util.CustomDateUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameRoomServiceImpl implements GameRoomService {

    private final GameRoomRepository gameRoomRepository;
    private final GameRoomMapper gameRoomMapper;

    // 연관관계 엔티티
    private final UserService userService;
    private final TempRoomServiceImpl tempRoomService;
    private final AvatarServiceImpl avatarService;
    private final PlayerMapper playerMapper;
    private final RedisService redisService;
    private final EmitterService emitterService;

    @Override
    @Transactional
    public Map<String, Object> createGameRoom(String inviteCode) throws Exception {

        String roomUid = UniqueCodeGenerator.generateRandomString(8);                               // 방 UUID 생성
        TempRoomDto tempRoomDto = tempRoomService.getTempRoomDetail(inviteCode);         // 초대코드로 임시방 정보 조회

        // 대기방으로부터 저장해야할 목록
        GameRoom gameRoom = gameRoomMapper.dtoToEntity(roomUid, tempRoomDto.setting());
        List<PlayerDto> playerDtoList = tempRoomDto.players();

        // 방이 생성되지 않는 조건
        if (playerDtoList.size() < 3) {
            throw new BaseException(StatusCode.CANNOT_START_OOMOOL_ROOM, "3인 미만의 인원은 방을 생성할 수 없습니다.");
        }
        if (playerDtoList.size() > tempRoomDto.setting().getMaxMember()) {
            throw new BaseException(StatusCode.CANNOT_START_OOMOOL_ROOM, "방의 최대 인원수를 초과했습니다.");
        }

        // 마니또 매칭 결과
        Map<Integer, Integer> matchMap = MatchManittiUtil.matchPair(playerDtoList);

        // [임시 - StartCheck] - 대기방에서 시작 여부를 체킹
        redisService.saveValueOperation(TempRoomPrefix.START_CHECK + inviteCode, roomUid);
        emitterService.notify(inviteCode, roomUid);

        // Player Entity 객체로 변환
        for (PlayerDto playerDto : playerDtoList) {
            User user = userService.getUserById(playerDto.getUserId());
            Avatar avatar = avatarService.getAvatarByPlayerAvatarUrl(playerDto.getPlayerAvatarUrl());
            Player player = playerMapper.dtoToEntity(roomUid, playerDto, gameRoom, user, avatar,
                tempRoomDto.masterId(), matchMap.get(playerDto.getUserId()));
            gameRoom.getPlayers().add(player); // 문답방에 플레이어를 넣는다.

            // 대기방 유저 목록에서 삭제
            redisService.deleteSetOperation(TempRoomPrefix.USER_INVITE_TEMPROOM + user.getId(), inviteCode);
        }
        gameRoomRepository.save(gameRoom);

        // invite Code에 대한 정보를 삭제한다. (대기방 삭제)
        redisService.deleteKey(TempRoomPrefix.SETTING_OPTION + inviteCode);
        redisService.deleteKey(TempRoomPrefix.PLAYERS + inviteCode);

        // 생성된 문답방 코드 return
        return Map.of(
            "roomUid", roomUid,
            "message", inviteCode + " 대기방이 삭제되었습니다."
        );
    }

    @Override
    public GameRoom getGameRoom(String roomUid) {
        return gameRoomRepository.findByRoomUid(roomUid)
            .orElseThrow(() -> new BaseException(StatusCode.INVILD_OOMOOL_ROOM_UUID));
    }

    @Override
    public Map<String, Object> getGameRoomDetail(String roomUid) {

        GameRoom gameRoom = gameRoomRepository.findByRoomUid(roomUid)
            .orElseThrow(() -> new BaseException(StatusCode.INVILD_OOMOOL_ROOM_UUID));
        SettingOptionDto settingOptionDto = gameRoomMapper.entityToSettingOptionDto(gameRoom);

        List<PlayerDto> playerDtoList = playerMapper.entityToPlayerDtoList(gameRoom.getPlayers());

        return Map.of(
            "room_uid", roomUid,
            "created_at", CustomDateUtil.convertDateTimeToString(gameRoom.getCreatedAt()),
            "setting", settingOptionDto,
            "players", playerDtoList
        );
    }
}
