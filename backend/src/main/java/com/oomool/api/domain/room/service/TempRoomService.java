package com.oomool.api.domain.room.service;

import java.util.Map;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.room.dto.SettingOptionDto;
import com.oomool.api.domain.room.dto.TempRoomBanRequestDto;
import com.oomool.api.domain.room.dto.TempRoomDto;

public interface TempRoomService {

    /**
     * 방장은 대기방을 Redis에 생성한다.
     *
     * @param settingOptionDto 대기방 설정 정보
     * @param masterUserId 방장 게임 프로필
     * */
    Map<String, Object> createTempRoom(SettingOptionDto settingOptionDto, int masterUserId);

    /**
     * 플레이어는 대기방에 입장한다.
     *
     * @param inviteCode 대기방 초대 코드
     * @param playerDto 참여자 게임 프로필
     * */
    Map<String, Object> joinTempRoom(String inviteCode, PlayerDto playerDto);

    /**
     * 초대코드로 대기방의 정보를 조회한다.
     *
     * @param inviteCode 대기방 초대코드
     * */
    TempRoomDto getTempRoomDetail(String inviteCode);

    /**
     * 방장은 대기방을 삭제한다.
     *
     * @param inviteCode 대기방 초대코드
     * @param masterUserId 방장의 유저아이디
     * */
    String deleteTempRoom(String inviteCode, int masterUserId);

    /**
     * 플레이어는 대기방을 퇴장한다.
     *
     * @param inviteCode 초대코드
     * @param userId 유저 ID
     * */
    String exitTempRoom(String inviteCode, int userId);

    /**
     * 대기방의 플레이어 프로필을 수정한다.
     *
     * @param inviteCode 초대코드
     * @param requestUpdateProfileDto 수정할 대상의 프로필 정보
     * */
    PlayerDto modifyPlayerProfile(String inviteCode, PlayerDto requestUpdateProfileDto);

    /**
     * 방장은 대기방의 설정 옵션을 수정할 수 있다.
     *
     * @param inviteCode 초대코드
     * @param requestUpdateSettingOptionDto 방 설정 정보
     * */
    SettingOptionDto modifyTempRoomSettingOption(String inviteCode,
        SettingOptionDto requestUpdateSettingOptionDto);

    /**
     * 방장은 대기방의 인원 중 일부를 강퇴 시킬 수 있다.
     *
     * @param inviteCode 초대코드
     * @param requestBanDto 입력받는 banDto
     * */
    String addBanList(String inviteCode, TempRoomBanRequestDto requestBanDto);

    /**
     * // 비즈니스
     * 방장인지 검증한다.
     *
     * @param inviteCode 초대코드
     * @param userId 유저 아이디
     * */
    boolean validateTempRoomMaster(String inviteCode, int userId);

    /**
     * [임시 코드] 대기방 생성 여부를 Check 합니다. <p></p>
     * // SSE 방식으로 변경예정
     *
     * @param inviteCode 초대코드
     * */
    String startCheck(String inviteCode);

    /**
     * SSE Start Connection
     *
     * */
    SseEmitter connection(String inviteCode);
}
