package com.oomool.api.domain.player.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.player.dto.ManittiDto;
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

        // 마니띠 매칭 로직
        Map<Integer, Integer> matchingPairByUserId = matchingPair(playerDtoList);

        List<Player> playerList = new ArrayList<>();
        // Dto To Entiry
        for (PlayerDto playerDto : playerDtoList) {
            User user = userRepository.findById(playerDto.getUserId())
                .orElseThrow(() -> new Exception("유저가 없습니다.")); // 추후 수정
            Avatar avatar = avatarRepository.findByUrl(playerDto.getPlayerAvatarUrl())
                .orElseThrow(() -> new EntityNotFoundException("아바타가 없습니다."));
            Player player = playerMapper.dtoToEntity(roomUid, playerDto, gameRoom, user,
                avatar, Integer.parseInt(masterUserId), matchingPairByUserId);
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

    /**
     * 마니띠의 정보 조회
     */
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
     * 마니띠 정보 조회(엔티티 반환)
     */
    public Player getPlayerInfo(int authorId) {
        Player player = playerRepository.findById(authorId);
        return player;
    }
     * 마니띠 매칭
     * - 마니띠 매칭은 USER id를 기준으로 한다.
     * */
    public Map<Integer, Integer> matchingPair(List<PlayerDto> playerDtoList) {

        // 매칭 닉네임 만 뽑기
        List<Integer> playerList = playerDtoList.stream()
            .map(PlayerDto::getUserId).collect(Collectors.toList());

        // 랜덤하게 셔플하기
        Collections.shuffle(playerList);

        Map<Integer, Integer> pair = new HashMap<>();
        List<Integer> availablePlayer = new ArrayList<>(playerList);

        for (int playerId : playerList) {
            availablePlayer.remove(
                Integer.valueOf(
                    playerId));                                 // 자기자신을 제거 -> index가 아닌 객체 값 USERID가 삭제 되어야 한다.
            if (availablePlayer.isEmpty()) {                                // 모든 플레이어 매칭되면 종료
                break;
            }
            int manittiId = availablePlayer.get(0);                         // 가능한 첫번째 마니띠 선택
            pair.put(playerId, manittiId);                                  // 매칭 결과에 넣기
            availablePlayer.add(playerId);                                  // 다음 반복 위해, 자기 자신 추가
            availablePlayer.remove(Integer.valueOf(manittiId));             // 내가 지목한 마니띠는 제외
        }
        return pair;
    }
}
