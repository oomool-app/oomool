package com.oomool.api.domain.user.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.question.dto.DailyQuestionDto;
import com.oomool.api.domain.question.util.RoomQuestionMapper;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.service.TempRoomRedisService;
import com.oomool.api.domain.room.util.GameRoomMapper;
import com.oomool.api.domain.room.util.TempRoomMapper;
import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 연관관계 참조
    private final TempRoomRedisService tempRoomRedisService;
    private final TempRoomMapper tempRoomMapper;
    private final GameRoomMapper gameRoomMapper;
    private final RoomQuestionMapper roomQuestionMapper;

    public int regist(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        return user.getId();
    }

    public UserDto searchUserEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다"));

        UserDto userDto = UserDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .username(user.getUsername())
            .build();

        return userDto;
    }

    /**
     * 유저 Email로 조회 후 id를 리턴
     * */
    public int getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다"));
        return user.getId();
    }

    /**
     * 유저 Id 의 존재 여부 확인
     * */
    public User getUserById(int userId) throws Exception {
        return userRepository.findById(userId).orElseThrow(() -> new Exception("유저가 존재하지 않습니다."));
    }

    /**
     * 유저가 참여하고 있는 대기방 정보를 조회한다.
     * */
    public List<Map<String, Object>> getTempRoomList(int userId) {

        // 참여하고 있는 inviteCode 정보 조회
        List<String> tempRoomList = tempRoomRedisService.getUserInviteTempRoomList(userId);
        List<Map<String, Object>> tempRoomSettingList = new ArrayList<>();
        for (String inviteCode : tempRoomList) {
            Map<String, Object> tempRoomSetting = tempRoomRedisService.getTempRoomSetting(inviteCode);
            System.out.println(tempRoomSetting.toString());
            String createdAt = (String)tempRoomSetting.get("createdAt");
            int masterId = Integer.parseInt((String)tempRoomSetting.get("masterId"));
            SettingOptionDto settingOptionDto = tempRoomMapper.mapToSettingOptionDto(tempRoomSetting);
            tempRoomSettingList.add(
                Map.of(
                    "invite_code", inviteCode,
                    "master_id", masterId,
                    "created_at", createdAt,
                    "setting", settingOptionDto
                )
            );
        }
        return tempRoomSettingList;
    }

    /**
     * 유저가 참여하고 있는 문답방 정보를 조회한다.
     *
     * @param userId 유저 아이디
     * */
    public List<Map<String, Object>> getGameRoomList(int userId) {
        List<Map<String, Object>> gameRoomList = new ArrayList<>();
        // 참여하고 있는 roomUid 정보 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        for (Player player : user.getPlayerList()) {
            GameRoom gameRoom = player.getRoom(); // GameRoom
            SettingOptionDto settingOptionDto = gameRoomMapper.entityToSettingOptionDto(gameRoom);
            DailyQuestionDto dailyQuestionDto = gameRoom.getRoomQuestionList().stream()
                .filter(roomQuestion -> roomQuestion.getDate().equals(LocalDate.now()))
                .findFirst()
                .map(roomQuestionMapper::entityToDailyQuestionDto).orElse(null);

            // 필요한 컬럼 넣기
            Map<String, Object> gameRoomMap = new HashMap<>();
            gameRoomMap.put("room_uid", gameRoom.getRoomUid());
            gameRoomMap.put("daily_question", dailyQuestionDto);
            gameRoomMap.put("created_at", gameRoom.getCreatedAt());
            gameRoomMap.put("setting", settingOptionDto);
            // 최종 결과 만들기
            gameRoomList.add(gameRoomMap);
        }
        return gameRoomList;
    }
}
