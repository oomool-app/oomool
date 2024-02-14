package com.oomool.api.global.dummy;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oomool.api.domain.question.service.RoomQuestionServiceImpl;
import com.oomool.api.domain.room.service.GameRoomServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("GameRoomDummy")
@DependsOn("TempRoomDummy")
@RequiredArgsConstructor
@Transactional
public class GameRoomDummy {

    private final GameRoomServiceImpl gameRoomService;
    private final RoomQuestionServiceImpl roomQuestionService;

    @PostConstruct
    public void init() throws Exception {
        Map<String, Object> progress = gameRoomService.createGameRoom("progressDummy1");
        roomQuestionService.publishRoomQuestionList((String)progress.get("room_uid")); // 질문까지 생성

        Map<String, Object> ended = gameRoomService.createGameRoom("endedDummy1");
        roomQuestionService.publishRoomQuestionList((String)progress.get("room_uid"));
    }

}
