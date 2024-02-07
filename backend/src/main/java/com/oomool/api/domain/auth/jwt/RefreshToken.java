package com.oomool.api.domain.auth.jwt;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * RefreshToken 객체의 직렬화와 역직렬화가 지원되도록 마커 인터페이스인 Serializable 인터페이스를 구현
 *
 * @RedisHash는 Hash Collection을 명시하는 애너테이션입니다. RedisHash의 key는 value인 jwtToken과 @Id가 붙은 id 필드의 값을 합성하여 사용
 */
@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken implements Serializable {

    @Id
    private String id;

    // @Indexed 애너테이션을 사용하면 JPA를 사용하듯이 findByAccessToken과 같은 질의가 가능해집니다.
    // RefreshToken을 찾을때 AccessToken 기반으로 찾을 것이므로, @Indexed 애너테이션을 붙여주었습니다.
    @Indexed
    private String accessToken;

    private String refreshToken;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
