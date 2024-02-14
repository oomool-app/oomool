package com.oomool.api.global.dummy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.player.entity.Avatar;
import com.oomool.api.domain.player.repository.AvatarRepository;
import com.oomool.api.domain.question.entity.QuestionType;
import com.oomool.api.domain.room.constant.TempRoomPrefix;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.service.TempRoomServiceImpl;
import com.oomool.api.domain.room.util.TempRoomMapper;
import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.domain.user.service.UserService;
import com.oomool.api.global.config.redis.RedisService;
import com.oomool.api.global.util.CustomDateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("TempRoomDummy")
@RequiredArgsConstructor
public class TempRoomDummy {

    private final RedisService redisService;
    private final UserService userService;
    private final TempRoomServiceImpl tempRoomService;
    private final TempRoomMapper tempRoomMapper;
    private final AvatarRepository avatarRepository;

    // dummy 용 create Temp Room
    public void createTempRoom(String inviteCode, SettingOptionDto settingOptionDto, int masterUserId) {

        Map<String, Object> settingRoomMap = tempRoomMapper.settingRoomDtoToMap(settingOptionDto);
        settingRoomMap.put("inviteCode", inviteCode);
        settingRoomMap.put("createdAt", CustomDateUtil.convertDateTimeToString(LocalDateTime.now()));
        settingRoomMap.put("masterId", Integer.toString(masterUserId));

        redisService.saveAllHashOperation(TempRoomPrefix.SETTING_OPTION + inviteCode, settingRoomMap);
        redisService.saveValueOperation(TempRoomPrefix.START_CHECK + inviteCode, "false");
    }

    // dummy용 PlayerDto
    public PlayerDto userDtoToPlayerDto(UserDto user, String nickName, String backGround, String avatar) {
        return PlayerDto.builder()
            .userId(user.getId())
            .userEmail(user.getEmail())
            .playerNickname(nickName)
            .playerBackgroundColor(backGround)
            .playerAvatarUrl(avatar)
            .build();
    }

    @PostConstruct
    public void init() {

        List<Avatar> avatarList = avatarRepository.findAll(); // 아바타 전체 목록
        List<String> backgroundList = new ArrayList<>(
            Arrays.asList("#B2E8EB", "#FFEAC4", "#A4B5C3", "#CCE3FF", "#F8C4D6", "#D8A5CC")); // 더미로 지정할 색상

        // 구해조 멤버
        UserDto 세정 = userService.searchUserEmail("mblaqsj1015@naver.com");
        UserDto 필모 = userService.searchUserEmail("itsmo@kakao.com");
        UserDto 은평 = userService.searchUserEmail("jep1995@kakao.com");
        UserDto 현지 = userService.searchUserEmail("dhkddhkd1234@naver.com");
        UserDto 병현 = userService.searchUserEmail("bb05qudgus@naver.com");
        UserDto 성수 = userService.searchUserEmail("zxzxzlxlzlxl@naver.com");

        // 대기방 테스트용
        SettingOptionDto temp_1 = SettingOptionDto.builder()
            .title("싸피 더미 테스트")
            .startDate(LocalDate.parse("2024-02-15"))
            .endDate(LocalDate.parse("2024-02-18"))
            .questionType(QuestionType.AW)
            .maxMember(3)
            .build();

        createTempRoom("tempDummy1", temp_1, 1);
        tempRoomService.joinTempRoom("tempDummy1",
            userDtoToPlayerDto(세정, "세정", backgroundList.get(0), avatarList.get(0).getUrl()));
        tempRoomService.joinTempRoom("tempDummy1",
            userDtoToPlayerDto(필모, "필모", backgroundList.get(1), avatarList.get(1).getUrl()));
        tempRoomService.joinTempRoom("tempDummy1",
            userDtoToPlayerDto(성수, "성수", backgroundList.get(2), avatarList.get(2).getUrl()));

        // 진행 중 테스트용
        SettingOptionDto progress_1 = SettingOptionDto.builder()
            .title("싸피 더미 테스트")
            .startDate(LocalDate.parse("2024-02-15"))
            .endDate(LocalDate.parse("2024-02-18"))
            .questionType(QuestionType.AW)
            .maxMember(4)
            .build();

        createTempRoom("progressDummy1", progress_1, 6);
        tempRoomService.joinTempRoom("progressDummy1",
            userDtoToPlayerDto(세정, "세정", backgroundList.get(1), avatarList.get(3).getUrl()));
        tempRoomService.joinTempRoom("progressDummy1",
            userDtoToPlayerDto(필모, "필모", backgroundList.get(2), avatarList.get(1).getUrl()));
        tempRoomService.joinTempRoom("progressDummy1",
            userDtoToPlayerDto(성수, "성수", backgroundList.get(3), avatarList.get(2).getUrl()));
        tempRoomService.joinTempRoom("progressDummy1",
            userDtoToPlayerDto(현지, "현지", backgroundList.get(4), avatarList.get(4).getUrl()));

        // 종료방 테스트용
        SettingOptionDto ended_1 = SettingOptionDto.builder()
            .title("싸피 더미 테스트")
            .startDate(LocalDate.parse("2024-02-09"))
            .endDate(LocalDate.parse("2024-02-12"))
            .questionType(QuestionType.AW)
            .maxMember(4)
            .build();

        createTempRoom("endedDummy1", ended_1, 3);
        tempRoomService.joinTempRoom("endedDummy1",
            userDtoToPlayerDto(세정, "세정", backgroundList.get(1), avatarList.get(3).getUrl()));
        tempRoomService.joinTempRoom("endedDummy1",
            userDtoToPlayerDto(은평, "은평", backgroundList.get(2), avatarList.get(1).getUrl()));
        tempRoomService.joinTempRoom("endedDummy1",
            userDtoToPlayerDto(성수, "성수", backgroundList.get(3), avatarList.get(2).getUrl()));
        tempRoomService.joinTempRoom("endedDummy1",
            userDtoToPlayerDto(병현, "병현", backgroundList.get(4), avatarList.get(4).getUrl()));

        log.info("[0] 대기방 더미 생성 완료.");
    }

}
