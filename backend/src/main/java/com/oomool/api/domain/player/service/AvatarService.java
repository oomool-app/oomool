package com.oomool.api.domain.player.service;

import com.oomool.api.domain.player.entity.Avatar;

public interface AvatarService {

    /**
     * 아바타를 avatarURL을 기준으로 조회하빈다.
     *
     * @param playerAvatarUrl
     * */
    Avatar getAvatarByPlayerAvatarUrl(String playerAvatarUrl);

}
