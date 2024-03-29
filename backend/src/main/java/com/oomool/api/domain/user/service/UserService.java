package com.oomool.api.domain.user.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.auth.entity.SocialLogin;
import com.oomool.api.domain.auth.repository.OAuthRepository;
import com.oomool.api.domain.player.entity.Player;
import com.oomool.api.domain.question.dto.DailyQuestionDto;
import com.oomool.api.domain.question.util.RoomQuestionMapper;
import com.oomool.api.domain.room.constant.TempRoomPrefix;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.entity.GameRoom;
import com.oomool.api.domain.room.util.GameRoomMapper;
import com.oomool.api.domain.room.util.TempRoomMapper;
import com.oomool.api.domain.user.dto.MypageDto;
import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;
import com.oomool.api.global.config.redis.RedisService;
import com.oomool.api.global.exception.BaseException;
import com.oomool.api.global.exception.StatusCode;
import com.oomool.api.global.util.CustomDateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OAuthRepository oAuthRepository;

    private final TempRoomMapper tempRoomMapper;
    private final GameRoomMapper gameRoomMapper;
    private final RoomQuestionMapper roomQuestionMapper;

    private final RedisService redisService;

    public int regist(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        return user.getId();
    }

    public UserDto searchUserEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new BaseException(StatusCode.NOT_FOUNT_USER));

        return UserDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .username(user.getUsername())
            .build();
    }

    /**
     * User Id로 조회 후 UserDto로 반환해주는 메서드
     * */
    public UserDto searchByUserId(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(StatusCode.NOT_FOUNT_USER));
        return UserDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .username(user.getUsername())
            .build();
    }

    /**
     * userId로 유저 정보 반환
     */
    public MypageDto searchUserInfo(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(StatusCode.NOT_FOUNT_USER));
        return MypageDto
            .builder()
            .email(user.getEmail())
            .username(user.getUsername())
            .build();
    }

    /**
     * 유저 정보 수정(닉네임)
     */
    public void modifyUsername(int userId, String username) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(StatusCode.NOT_FOUNT_USER));
        user.setUsername(username);
        userRepository.save(user);
    }

    /**
     * 회원 탈퇴
     * 유저 정보, 소셜 정보
     */
    public void deleteUser(int userId) {
        // 해당 유저의 소셜 정보도 DB에서 제거한다.
        SocialLogin socialLogin = oAuthRepository.findByUserId(userId)
            .orElseThrow(() -> new BaseException(StatusCode.NOT_FOUNT_USER));
        oAuthRepository.delete(socialLogin);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BaseException(StatusCode.NOT_FOUNT_USER));
        userRepository.delete(user);
    }

    /**
     * 유저 Email로 조회 후 id를 리턴
     * */
    public int getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BaseException(StatusCode.NOT_FOUNT_USER));
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
        Set<Object> inviteCodeSet = redisService.getSetOperation(TempRoomPrefix.USER_INVITE_TEMPROOM + userId);
        if (inviteCodeSet == null || inviteCodeSet.isEmpty()) {
            throw new BaseException(StatusCode.NOT_FOUND_TEMP_ROOM);
        }

        // List로 정보 반환
        List<Map<String, Object>> tempRoomSettingList = new ArrayList<>();
        // 참여하고 있는 대기방 순회
        for (Object item : inviteCodeSet) {
            String inviteCode = item.toString();

            // 대기방 정보로 변환
            Map<String, Object> tempRoomSetting = redisService.getHashOperationByString(
                TempRoomPrefix.SETTING_OPTION + inviteCode);
            int masterId = Integer.parseInt((String)tempRoomSetting.get("masterId"));
            String createdAt = (String)tempRoomSetting.get("createdAt");
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

        // 참여하고 있는 roomUid 정보 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(StatusCode.NOT_FOUNT_USER));

        // List로 정보 반환
        List<Map<String, Object>> gameRoomList = new ArrayList<>();

        // 유저가 "플레이어"로 참여하고 있는 리스트 순회
        for (Player player : user.getPlayerList()) {
            GameRoom gameRoom = player.getRoom(); // GameRoom
            SettingOptionDto settingOptionDto = gameRoomMapper.entityToSettingOptionDto(gameRoom);

            // TODO :: 개선 해야할 작업
            DailyQuestionDto dailyQuestionDto = gameRoom.getRoomQuestionList().stream()
                .filter(roomQuestion -> roomQuestion.getDate().equals(LocalDate.now()))
                .findFirst()
                .map(roomQuestionMapper::entityToDailyQuestionDto).orElse(null);

            // 추가적인 필요 컬럼 넣기
            Map<String, Object> roomMap = new HashMap<>();
            roomMap.put("room_uid", gameRoom.getRoomUid());
            roomMap.put("daily_question", dailyQuestionDto);
            roomMap.put("created_at", CustomDateUtil.convertDateTimeToString(gameRoom.getCreatedAt()));
            roomMap.put("setting", settingOptionDto);
            gameRoomList.add(roomMap);
        }
        return gameRoomList;
    }
}
