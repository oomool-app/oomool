package com.oomool.api.global.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host; // host url

    @Value("${spring.data.redis.port}")
    private int port; // redis port 번호

    @Value("${spring.data.redis.username}")
    private String userName;

    @Value("${spring.data.redis.password}")
    private String password;

    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setUsername(userName);
        redisStandaloneConfiguration.setPassword(password);

        if (profile.equals("prod")) {
            redisStandaloneConfiguration.setDatabase(1);
        }
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // 객체를 JSON으로 직렬화/역직렬화

        // hashOps
        redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        return redisTemplate;
    }
}
