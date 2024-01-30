package com.oomool.api.domain.room.service;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.question.entity.QuestionType;
import com.oomool.api.domain.room.dto.SettingRoomDto;

@SpringBootTest
class TempRoomServiceTest {

    @Autowired
    private TempRoomService tempRoomService;
    @Autowired
    private RedisService redisService;

    @Test
    @Rollback(false)
    public void createRoomTest() throws Exception {
        // given
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        SettingRoomDto setting = SettingRoomDto.builder()
            .title("TEST")
            .startDate(dateFormat.parse("2024-01-01"))
            .endDate(dateFormat.parse("2024-01-30"))
            .questionType(QuestionType.AW)
            .maxMember(5)
            .build();

        PlayerDto master = PlayerDto.builder()
            .userId(1)
            .userEmail("test@test.com")
            .playerNickname("바보")
            .playerBackgroundColor("#FFFFF")
            .playerAvatarUrl("https://test.com/image")
            .build();
        // when
        String inviteCode = tempRoomService.createTempRoom(setting, master);
        System.out.println(inviteCode);
    }

}
