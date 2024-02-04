package com.oomool.api.domain.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.room.service.TempRoomRedisService;
import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TempRoomRedisService tempRoomRedisService;

    public int regist(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        return user.getId();
    }

    public UserDto searchUserEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());

        return userDto;
    }

    /**
     * 유저가 참여하고 있는 대기방 정보를 조회한다.
     * */
    public List<Map<String, Object>> getTempRoomList(int userId) {

        // 참여하고 있는 inviteCode 정보 조회
        List<String> tempRoomList = tempRoomRedisService.getUserInviteTempRoomList(userId);
        List<Map<String, Object>> tempRoomSettingList = new ArrayList<>();
        for (String inviteCode : tempRoomList) {
            tempRoomSettingList.add(tempRoomRedisService.getTempRoomSetting(inviteCode));
        }
        return tempRoomSettingList;
    }

}
