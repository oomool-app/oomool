package com.oomool.api.domain.player.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.player.entity.Avatar;
import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.player.repository.PlayerRepository;
import com.oomool.api.domain.player.util.MatchPlayer;
import com.oomool.api.domain.player.util.PlayerMapper;
import com.oomool.api.domain.room.dto.TempRoomDto;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.service.GameRoomServiceImpl;
import com.oomool.api.domain.room.service.TempRoomRedisService;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    // 연관관계 객체 가져오기 위한 주입
    private final TempRoomRedisService tempRoomRedisService;
    private final GameRoomServiceImpl gameRoomService;
    private final UserService userService;
    private final AvatarServiceImpl avatarService;

    @Override
    public void savePlayerList(String inviteCode, String roomUid) throws Exception {

        TempRoomDto tempRoomDto = tempRoomRedisService.getTempRoom(inviteCode);
        GameRoom gameRoom = gameRoomService.getGameRoom(roomUid);

        List<PlayerDto> playerDtoList = tempRoomDto.getPlayers();
        int masterId = tempRoomDto.getMasterId();

        // 마니또 매칭 결과
        Map<Integer, Integer> matchMap = MatchPlayer.matchPair(playerDtoList);

        List<Player> playerList = new ArrayList<>();

        // Dto To Entity
        for (PlayerDto playerDto : playerDtoList) {
            User user = userService.getUserById(playerDto.getUserId());
            Avatar avatar = avatarService.getAvatarByPlayerAvatarUrl(playerDto.getPlayerAvatarUrl());
            Player player = playerMapper.dtoToEntity(roomUid, playerDto, gameRoom, user, avatar, masterId,
                matchMap.get(playerDto.getUserId()));
            playerList.add(player); // player 저장
        }
        playerRepository.saveAll(playerList); // 저장
    }

    @Override
    public List<PlayerDto> getPlayerDtoList(String roomUid) {
        return gameRoomService.getGameRoom(roomUid).getPlayers().stream().map(playerMapper::entityToPlayerDto).collect(
            Collectors.toList());
    }
}
