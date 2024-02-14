package com.oomool.api.domain.room.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.oomool.api.domain.room.repository.EmitterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmitterService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 1HOUR
    private final EmitterRepository emitterRepository;

    /**
     * 클라이언트가 구독을 위해 호출하는 메서드
     * */
    public SseEmitter subscribe(String inviteCode) {
        SseEmitter emitter = createEmitter(inviteCode);
        sendToClient(inviteCode, "EventStream Created.");
        return emitter;
    }

    /**
     * 서버의 이벤트 클라이언트에게 보낸다.
     * */
    public void notify(String inviteCode, Object event) {
        sendToClient(inviteCode, event);
    }

    /**
     * 클라이언트에게 데이터 전송
     * */
    public void sendToClient(String inviteCode, Object data) {
        SseEmitter emitter = emitterRepository.get(inviteCode);
        if (emitter != null) {
            try {
                emitter.send(
                    SseEmitter.event()
                        .id(inviteCode)
                        .name("sse")
                        .data(data)
                );
            } catch (IOException ex) {
                emitterRepository.deleteByInviteCode(inviteCode);
                emitter.completeWithError(ex);
            }
        }
    }

    /**
     * 방생성
     * */
    public SseEmitter createEmitter(String inviteCode) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(inviteCode, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteByInviteCode(inviteCode));
        emitter.onTimeout(() -> emitterRepository.deleteByInviteCode(inviteCode));

        return emitter;
    }

}
