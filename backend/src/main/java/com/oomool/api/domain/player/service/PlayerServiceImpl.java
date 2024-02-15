package com.oomool.api.domain.player.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.player.dto.ManittiDto;
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
    public Map<String, Object> getPlayerDtoList(String roomUid) {
        GameRoom gameRoom = gameRoomService.getGameRoom(roomUid);
        List<PlayerDto> playerDtoList = playerMapper.entityToPlayerDtoList(gameRoom.getPlayers());
        return Map.of(
            "players", playerDtoList
        );
    }

    @Override
    public Map<String, Object> getManittiPlayerProfile(String roomUid, int userId) {
        Player player = playerRepository.findByRoomRoomUidAndUserId(roomUid, userId);                 // "나"의 프로필 조회
        // PlayerDto 는 manittiId를 가지지 않기 때문에 Service에서 조회한다.
        Player manitti = playerRepository.findByRoomRoomUidAndUserId(roomUid, player.getManittiId()); // "마니띠"의 프로필 조회
        return Map.of(
            "player", playerMapper.entityToPlayerDto(player),
            "manitti", playerMapper.entityToPlayerDto(manitti)
        );
    }

    @Override
    public Map<String, Object> getManittoPlayerProfile(String roomUid, int userId) {
        Player myManitto = playerRepository.findByRoomRoomUidAndManittiId(roomUid, userId);
        Boolean guess = playerRepository.findByRoomRoomUidAndUserId(roomUid, userId).getGuess();
        return Map.of(
            "guess", guess != null ? guess.toString() : "null",
            "manitto", playerMapper.entityToPlayerDto(myManitto)
        );
    }

    @Override
    @Transactional
    public void saveGuessResult(String roomUid, int userId, boolean guessResult) {
        Player player = playerRepository.findByRoomRoomUidAndUserId(roomUid, userId);
        player.updateGuess(guessResult);
    }

    /**
     * 마니띠 정보 가져오기
     * */
    @Override
    public ManittiDto getManittiInfo(String roomUid, int authorId) {
        // authorId로 manittiId 가져오기
        Player playerByAuthorId = playerRepository.findByRoomRoomUidAndUserId(roomUid, authorId);

        int manittiId = playerByAuthorId.getManittiId();

        // userId와 manittiId 유저 정보를 가져온다.
        Player player = playerRepository.findByRoomRoomUidAndUserId(roomUid, manittiId);

        ManittiDto manittiDto = ManittiDto
            .builder()
            .nickname(player.getNickname())
            .avatarColor(player.getAvatarColor())
            .url(player.getAvatar().getUrl())
            .build();

        return manittiDto;
    }

    /**
     * 작성자 정보 가져오기
     */
    @Override
    public Player getPlayerInfo(String roomUid, int authorId) {
        Player player = playerRepository.findByRoomRoomUidAndUserId(roomUid, authorId);
        return player;
    }
}
