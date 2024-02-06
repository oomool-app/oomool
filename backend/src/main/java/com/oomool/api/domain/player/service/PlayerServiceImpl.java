package com.oomool.api.domain.player.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.player.dto.ManittiDto;
import com.oomool.api.domain.player.dto.ManittiPairDto;
import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.player.repository.PlayerRepository;
import com.oomool.api.domain.player.util.PlayerMapper;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.service.GameRoomServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    // 연관관계 객체 가져오기 위한 주입
    private final GameRoomServiceImpl gameRoomService;

    @Override
    public List<PlayerDto> getPlayerDtoList(String roomUid) {
        GameRoom gameRoom = gameRoomService.getGameRoom(roomUid);
        return playerMapper.entityToPlayerDtoList(gameRoom.getPlayers());
    }

    @Override
    public PlayerDto getPlayerByUserId(String roomUid, int userId) {
        return playerMapper.entityToPlayerDto(playerRepository.findByRoom_RoomUidAndUser_Id(roomUid, userId));
    }

    @Override
    public ManittiPairDto getManittiPlayerProfile(String roomUid, int userId) {
        Player player = playerRepository.findByRoom_RoomUidAndUser_Id(roomUid, userId);                 // "나"의 프로필 조회
        Player manitti = playerRepository.findByRoom_RoomUidAndUser_Id(roomUid, player.getManittiId()); // "마니띠"의 프로필 조회
        return ManittiPairDto.builder()
            .player(playerMapper.entityToPlayerDto(player))
            .manitti(playerMapper.entityToPlayerDto(manitti))
            .build();
    }

    /**
     * 마니띠 정보 가져오기
     * */
    @Override
    public ManittiDto getManittiInfo(int authorId) {
        // authorId로 manittiId 가져오기
        Player playerByAuthorId = playerRepository.findById(authorId);

        int manittiId = playerByAuthorId.getManittiId();

        // manittiId로 manitti Info 가져오기
        List<Player> playerList = playerRepository.findAll();

        ManittiDto manittiDto = new ManittiDto();

        for (Player player : playerList) {
            int playerManittiCheck = player.getUser().getId();
            if (playerManittiCheck == manittiId) {
                manittiDto = ManittiDto
                    .builder()
                    .nickname(player.getNickname())
                    .avatarColor(player.getAvatarColor())
                    .url(player.getAvatar().getUrl())
                    .build();

                return manittiDto;
            }
        }
        return manittiDto;
    }

    /**
     * 작성자 정보 가져오기
     */
    @Override
    public Player getPlayerInfo(int authorId) {
        Player player = playerRepository.findByUser_id(authorId);
        return player;
    }
}
