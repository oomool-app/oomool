package com.oomool.api.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oomool.api.domain.user.dto.UserDto;
import com.oomool.api.domain.user.entity.User;
import com.oomool.api.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

}
