package com.oomool.api.domain.player.util;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.player.entity.Avatar;
import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.player.entity.PlayerType;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.user.entity.User;

@Component
public class PlayerMapper {

    /**
     * PlayerDto와 결합되는 Parameter를 결합하여 Player Entity 변환
     * - Player Entity 가 4개의 테이블과 연관관계를 맺고 있어 매핑할 것이 많다.
     * */
    public Player dtoToEntity(String roomUid,
        PlayerDto playerDto,
        GameRoom gameRoom,
        User user,
        Avatar avatar,
        int roomMasterId,
        int manittiUserId) {

        // PLAYER / MASTER 로 참여했는지 저장
        PlayerType playerType = playerDto.getUserId() == roomMasterId ? PlayerType.MASTER : PlayerType.PLAYER;

        return Player.builder()
            .room(gameRoom)
            .user(user)
            .nickname(playerDto.getPlayerNickname())
            .playerType(playerType)
            .manittiId(manittiUserId)
            .avatar(avatar)
            .avatarColor(playerDto.getPlayerBackgroundColor())
            .build();

    }

    /**
     * Player Entity -> PlayerDto
     * */
    public PlayerDto entityToPlayerDto(Player player) {
        return PlayerDto.builder()
            .userId(player.getUser().getId())
            .userEmail(player.getUser().getEmail())
            .playerNickname(player.getNickname())
            .playerBackgroundColor(player.getAvatarColor())
            .playerAvatarUrl(player.getAvatar().getUrl())
            .build();
    }

    /**
     * List<Player> -> List</PlayerDto>
     * */
    public List<PlayerDto> entityToPlayerDtoList(List<Player> playerList) {
        return playerList.stream()
            .map(this::entityToPlayerDto)
            .collect(Collectors.toList());
    }

}
