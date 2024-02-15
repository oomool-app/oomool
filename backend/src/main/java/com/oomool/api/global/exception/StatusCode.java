package com.oomool.api.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

    /**
     * Common
     * */

    // 4XX (Client Error)
    BAD_REQUEST(400, "요청의 구문이 잘못되었습니다."),
    UNAUTHORIZED(401, "지정한 리소스에 대한 액세스 권한이 없습니다."),
    FORBIDDEN(403, "지정한 리소스에 대한 액세스가 금지되었습니다."),
    NOT_FOUND(404, "지정한 리소스를 찾을 수 없습니다."),

    // 5XX
    INTERNAL_SERVER_ERROR(500, "내부 서버에 오류가 발생하였습니다."),
    NOT_IMPLEMENTED(501, "서버에 구현되지 않습니다."),
    BAD_GATEWAY(502, "잘못된 게이트웨이입니다."),

    /**
     * User
     * */
    NOT_FOUNT_USER(401, "유저 정보를 찾을 수 없습니다."),

    /**
     * 대기방
     * */
    INVALID_INVITE_CODE(404, "유효하지 않은 초대 코드입니다."),
    NOT_MASTER_AUTH_TEMPROOM(403, "방장의 권한이 없습니다."),
    DUPLICATION_INVITE_CODE(403, "이미 참여하고 있는 대기방 입니다."),
    NOT_COMPLETE_SETTING_OPTION(403, "설정 옵션이 지정되지 않았습니다."),
    NOT_FOUND_TEMP_ROOM(404, "참여하고 있는 대기방을 찾을 수 없습니다."),
    CANNOT_ACCESS_TEMP_ROOM(403, "이미 강제퇴장 당한 방입니다."),

    /**
     * 문답방
     * */
    INVILD_OOMOOL_ROOM_UUID(404, "유효하지 않은 방 UUID 입니다."),
    CANNOT_START_OOMOOL_ROOM(403, "방을 생성할 수 없습니다.");

    private final int statusCode;
    private final String message;
}
