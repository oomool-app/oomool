package com.oomool.api.domain.player.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.player.entity.Avatar;
import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.player.repository.AvatarRepository;
import com.oomool.api.domain.player.repository.PlayerRepository;
import com.oomool.api.domain.player.util.PlayerMapper;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.repository.GameRoomRepository;
import com.oomool.api.domain.room.service.TempRoomRedisService;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final GameRoomRepository gameRoomRepository;
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final TempRoomRedisService tempRoomRedisService;
    private final PlayerMapper playerMapper;

    /**
     *  플레이어 생성
     * */
    public void savePlayerList(String inviteCode, String roomUid) throws Exception {

        // 필요한 정보 조회
        List<PlayerDto> playerDtoList = tempRoomRedisService.getTempRoomPlayerList(inviteCode);
        String masterUserId = tempRoomRedisService.getTempRoomSettingValue(inviteCode, "masterId");
        GameRoom gameRoom = gameRoomRepository.findByRoomUid(roomUid).orElse(null);

        List<Player> playerList = new ArrayList<>();
        // Dto To Entiry
        for (PlayerDto playerDto : playerDtoList) {
            User user = userRepository.findById(playerDto.getUserId())
                .orElseThrow(() -> new Exception("유저가 없습니다.")); // 추후 수정
            Avatar avatar = avatarRepository.findByUrl(playerDto.getPlayerAvatarUrl())
                .orElseThrow(() -> new EntityNotFoundException("아바타가 없습니다."));
            Player player = playerMapper.dtoToEntity(roomUid, playerDto, gameRoom, user,
                avatar, Integer.parseInt(masterUserId));
            playerList.add(player);
        }
        playerRepository.saveAll(playerList); // 저장
    }

    /**
     * 플레이어 조회
     * */
    public List<PlayerDto> getPlayerDtoList(String roomUid) {
        return playerRepository.findByRoomUid(roomUid)
            .stream()
            .map(playerMapper::convertPlayerDto)
            .collect(Collectors.toList());
    }
}
