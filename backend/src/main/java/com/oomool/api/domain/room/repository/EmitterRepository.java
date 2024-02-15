package com.oomool.api.domain.room.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    /**
     *이미터 저장
     * */
    public void save(String inviteCode, SseEmitter emitter) {
        emitters.put(inviteCode, emitter);
    }

    /**
     * Emitter 제거
     * */
    public void deleteByInviteCode(String inviteCode) {
        emitters.remove(inviteCode);
    }

    /**
     * 주어진 이미터와 아이디 가져옴
     * */
    public SseEmitter get(String inviteCode) {
        return emitters.get(inviteCode);
    }

}
