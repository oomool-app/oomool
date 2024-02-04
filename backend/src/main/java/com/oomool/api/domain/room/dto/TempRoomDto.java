package com.oomool.api.domain.room.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.oomool.api.domain.player.dto.PlayerDto;
import com.oomool.api.domain.user.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempRoomDto {
    private String inviteCode;
    private LocalDateTime createAt;
    private int masterId;
    private SettingOptionDto setting;
    private List<PlayerDto> players;
    private List<UserDto> banList;
}
