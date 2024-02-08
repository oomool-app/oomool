package com.oomool.api.domain.room.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.oomool.api.domain.player.dto.PlayerDto;

public class MatchManittiUtil {

    /**
     * 마니띠를 매칭하는 유틸
     * - 마니띠를 매칭한다.
     * - 마니띠 매칭은 userId를 기준으로 한다.
     *
     * @param playerDtoList 플레이어 DTO 리스트
     *  */
    public static Map<Integer, Integer> matchPair(List<PlayerDto> playerDtoList) {

        // 매칭 닉네임 만 뽑기
        List<Integer> playerList = playerDtoList.stream()
            .map(PlayerDto::getUserId).collect(Collectors.toList());

        // 랜덤하게 셔플하기
        Collections.shuffle(playerList);

        Map<Integer, Integer> pair = new HashMap<>();
        List<Integer> availablePlayer = new ArrayList<>(playerList);

        for (int playerUserId : playerList) {
            // index가 아닌 Integer Object가 삭제 되어야 한다.
            availablePlayer.remove(Integer.valueOf(playerUserId));              // 플레이어의 유저아이디 자기자신 제거
            if (availablePlayer.isEmpty()) {
                break;
            }                                                                   // 모든 플레이어 매칭되면 종료
            int manittiId = availablePlayer.get(0);                             // 가능한 첫번째 마니띠 선택
            pair.put(playerUserId, manittiId);                                  // 매칭 결과에 넣기
            availablePlayer.add(playerUserId);                                  // 다음 반복 위해, 자기 자신 추가
            availablePlayer.remove(Integer.valueOf(manittiId));                 // 내가 지목한 마니띠는 제외
        }
        return pair;
    }

}
