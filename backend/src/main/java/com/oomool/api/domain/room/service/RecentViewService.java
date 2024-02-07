package com.oomool.api.domain.room.service;

import java.time.LocalDateTime;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.oomool.api.global.util.CustomDateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecentViewService {

    private final RedisTemplate<String, Object> redisTemplate;

    public String recentlyViewRoom(String category, String code, int userId) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        String viewDateTime = CustomDateUtil.convertDateTimeToString(LocalDateTime.now());
        hashOps.put(category + ":" + userId, code, viewDateTime);
        return viewDateTime;
    }

}
