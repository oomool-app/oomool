package com.oomool.api.domain.room.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;
    private int masterId;
    private SettingOptionDto setting;
    private List<PlayerDto> players;
    private List<UserDto> banList;
}
