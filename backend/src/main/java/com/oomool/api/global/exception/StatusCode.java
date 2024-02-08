package com.oomool.api.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

    /**
     * Common
     * */

    // 4XX
    FORBIDDEN(403, "Forbidden"),

    /**
     * User
     * */
    NOT_FOUNT_USER(401, "유저 정보를 찾을 수 없습니다."),

    /**
     * 대기방
     * */
    INVALID_INVITE_CODE(404, "유효하지 않은 초대 코드입니다."),
    NOT_MASTER_AUTH_TEMPROOM(404, "방장의 권한이 없습니다."),
    DUPLICATION_INVITE_CODE(404, "이미 참여하고 있는 대기방 입니다."),
    NOT_COMPLETE_SETTING_OPTION(400, "설정 옵션이 지정되지 않았습니다."),
    NOT_FOUND_TEMP_ROOM(400, "참여하고 있는 대기방을 찾을 수 없습니다."),

    /**
     * 문답방
     * */
    INVILD_OOMOOL_ROOM_UUID(404, "유효하지 않은 방 UUID 입니다."),
    CANNOT_START_OOMOOL_ROOM(400, "방을 생성할 수 없습니다.");

    private final int statusCode;
    private final String message;
}
