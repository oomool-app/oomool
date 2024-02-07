package com.oomool.api.global.config.redis;

import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Hash Operation <p>
     * prefix Key가 존재하는지 검증 <p>
     * - Hash 의 key의 Type과 무관하다.
     *
     *
     * */
    public boolean hasKey(String prefix) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(prefix));
    }

    /**
     * Hash Operation<String, String, Object><p>
     * Key 값이 String 인 Hash Operation<p>
     * Map의 모든 값을 일괄적으로 저장한다.
     * <p>
     * @param prefix REDIS KEY
     * @param map MAP<String, Object>
     * */
    public void saveAllHashOperation(String prefix, Map<String, Object> map) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        hashOps.putAll(prefix, map);
    }

    /**
     * Hash Operation<String, String, Object><p>
     * Key 값이 String 인 Hash Operation
     * prefix + key + Object에 대해 저장한다.
     * <p>
     * @param prefix REDIS KEY
     * @param key [String] key
     * @param value [Object] value
     * */
    public void saveHashOperation(String prefix, String key, Object value) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        hashOps.put(prefix, key, value);
    }

    /**
     * Hash Operation<String, String, Object><p>
     * Key 값이 String 인 Hash Operation
     * prefix에 대한 map 결과를 반환한다.
     * <p>
     * @param prefix REDIS KEY
     * @return Redis prefix 를 갖는 Map
     * */
    public Map<String, Object> getHashOperationByString(String prefix) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(prefix);
    }

    /**
     * Hash Operation<String, String, Object><p>
     * Key 값이 String 인 Hash Operation
     * prefix + key 의 value값을 반환한다.
     * <p>
     * @param prefix REDIS KEY
     * @param key [String] 조회할 key값
     * @return Redis prefix 를 갖는 Map
     * */
    public String getValueHashOperation(String prefix, String key) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return (String)hashOps.get(prefix, key);
    }

    /**
     * prefix 전체 삭제
     *
     * */
    public void deleteKey(String prefix) {
        redisTemplate.delete(prefix);
    }

    /**
     * Hash Operation<String, Integr, Object><p>
     * Key 값이 Integer 인 Hash Operation
     * prefix + key + Object에 대해 저장한다.
     * <p>
     * @param prefix REDIS KEY
     * @param key [Integer] key
     * @param value [Object] value
     * */
    public void saveHashOperation(String prefix, int key, Object value) {
        HashOperations<String, Integer, Object> hashOps = redisTemplate.opsForHash();
        hashOps.put(prefix, key, value);
    }

    /**
     * Hash Operation<String, Integer, Object><p>
     * Key 값이 Integer 인 Hash Operation
     * prefix에 대한 map 결과를 반환한다.
     * <p>
     * @param prefix REDIS KEY
     * @return Redis prefix 를 갖는 Map
     * */
    public Map<Integer, Object> getHashOperationByInteger(String prefix) {
        HashOperations<String, Integer, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(prefix);
    }

    /**
     * Hash Operation<String, Integer, Object><p>
     * Key 값이 Integer 인 Hash Operation
     * prefix + key의 value를 삭제한다.
     * <p>
     * @param prefix REDIS KEY
     * @param key [Integer] key
     * */
    public void deleteKeyOperation(String prefix, int key) {
        HashOperations<String, Integer, Object> hashOps = redisTemplate.opsForHash();
        hashOps.delete(prefix, key);
    }

    /**
     * Set Operation<String Object><p>
     * Key 값이 String 인 Set Operation
     * prefix + key + Object에 대해 저장한다.
     * <p>
     * @param prefix REDIS KEY
     * @param value [Object] value
     * */
    public void saveSetOperation(String prefix, String value) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.add(prefix, value);
    }

    /**
     * Set Operation<String Object><p>
     * Key 값이 String 인 Set Operation
     * prefix 에 대한 Set의 목록을 반환한다.
     * <p>
     * @param prefix REDIS KEY
     * */
    public Set<Object> getSetOperation(String prefix) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        return setOps.members(prefix);
    }

    /**
     * Set Operation<String Object><p>
     * Key 값이 String 인 Set Operation
     * prefix에 대한 값을 삭제한다.
     * <p>
     * @param prefix REDIS KEY
     * @param value inviteCode
     * */
    public void deleteSetOperation(String prefix, String value) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.remove(prefix, value);
    }

    /**
     *  Set Operation<String, Object> <p>
     *  redis Set에 Object가 있는지 여부 확인
     * */
    public boolean hasValueOperation(String prefix, String value) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        return Boolean.TRUE.equals(setOps.isMember(prefix, value));
    }

}


