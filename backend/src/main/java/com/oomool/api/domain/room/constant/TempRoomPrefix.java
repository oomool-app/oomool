package com.oomool.api.domain.room.constant;

// Redis Hash Key PREFIX
// 뒤에 주석 까지 합쳐서 redisService의 key 가 됩니다.
public class TempRoomPrefix {

    public static String SETTING_OPTION = "roomSetting:"; // + inviteCode
    public static String PLAYERS = "roomPlayers:"; // + inviteCode
    public static String USER_INVITE_TEMPROOM = "userInviteTemp:"; // + userId

    // [임시] startCall key
    public static String START_CHECK = "startCheck:";

}
