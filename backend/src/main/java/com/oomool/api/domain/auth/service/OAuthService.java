package com.oomool.api.domain.auth.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.oomool.api.domain.auth.dto.SocialDto;
import com.oomool.api.domain.auth.entity.SocialLogin;
import com.oomool.api.domain.auth.repository.OAuthRepository;
import com.oomool.api.domain.user.dto.UserSocialDto;
import com.oomool.api.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 넘겨받은 Authorization_code를 이용하여 Access Token을 발급받는다.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class OAuthService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final OAuthRepository oAuthRepository;

    @Value("${frontend.redirect.uri}")
    private String redirectUri;

    // 넘겨 받은 code를 파라미터로 받는다.
    public String getKakaoAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqUrl = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String kakaoUri = "grant_type=authorization_code"
                + "&client_id=d63ba3628c4ea2cff1e3eb9f0955f47f"
                + "&redirect_uri=" + redirectUri + "/oauth/kakao"
                + "&code=" + code;
            bw.write(kakaoUri);
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonElement element = JsonParser.parseString(result.toString());


            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    public UserSocialDto createKakaoUser(String token) throws Exception {

        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        UserSocialDto userSocialDto = new UserSocialDto();
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonElement element = JsonParser.parseString(result);


            // kakao 고유 ID 가져오기 (provider_id)
            String kakaoId = element.getAsJsonObject().get("id").getAsString();


            String nickname = element.getAsJsonObject()
                .get("properties")
                .getAsJsonObject()
                .get("nickname")
                .getAsString();

            boolean hasEmail = element.getAsJsonObject()
                .get("kakao_account")
                .getAsJsonObject()
                .get("has_email")
                .getAsBoolean();

            String email = "";
            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            userSocialDto = UserSocialDto.builder()
                .providerId(kakaoId)
                .provider("kakao")
                .email(email)
                .nickname(nickname)
                .build();


            br.close();

            return userSocialDto;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userSocialDto;
    }

    // 소셜 로그인 회원가입
    public int socialKakaoRegist(SocialDto socialDto) {

        User user = new User();
        user.setId(socialDto.getUserId());

        SocialLogin socialLogin = SocialLogin.builder()
            .providerId(socialDto.getProviderId())
            .provider(socialDto.getProvider())
            .user(user)
            .build();

        oAuthRepository.save(socialLogin);
        return socialLogin.getId();
    }
}