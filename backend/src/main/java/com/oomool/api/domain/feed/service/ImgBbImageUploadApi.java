package com.oomool.api.domain.feed.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.oomool.api.domain.feed.dto.ImageResponseDto;
import com.oomool.api.domain.feed.dto.ImgBbDto;
import com.oomool.api.domain.feed.dto.ImgBbInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component // @Value와 Bean 주입을 사용하기 위해 선언
@RequiredArgsConstructor
public class ImgBbImageUploadApi {
    private final String apiUrl = "https://api.imgbb.com/1/upload";
    private final WebClient webClient;
    @Value("${imgbb.secret}")
    private String secret;

    public ImageResponseDto uploadImage(MultipartFile file) throws IOException {

        ImageResponseDto imageResponseDto = new ImageResponseDto();

        log.info("file : {}", file);

        // file이 null로 넘어왔을 때 빈 image 데이터를 넘김으로써 불필요한 이미지 API 요청을 방지한다.
        if (file == null) {
            return imageResponseDto;
        }

        // 요청 바디 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("key", secret);
        try {
            body.add("image", new NamedByteArrayResource(file.getBytes(), file.getName()));
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 읽어들일 수 없습니다.", e);
        }

        // POST 요청 보내서 ImgBbDto 객체에 받아오기
        ImgBbDto imgBbDto = webClient.post()
            .uri(apiUrl)
            .contentType(MediaType.MULTIPART_FORM_DATA) // form-data로 요청을 보낸다.
            .bodyValue(body) // api key, image를 body에 담아서 보낸다.
            .accept(MediaType.APPLICATION_JSON) // json 타입을 허락해준다
            .retrieve() // HTTP 요청이 끝난 후에 응답을 받아오는 메서드
            .bodyToMono(ImgBbInfoDto.class) // 응답 DTO
            .blockOptional() // Nono에서 값을 동기적으로 받아올 때 사용, 값이 null이면 Optional로 받아온다.
            .map(ImgBbInfoDto::getData) // ImgBbInfoDto 객체에서 getData()를 호출하여 ImgBbData 객체를 추출한다.
            .orElseThrow(() -> new IllegalArgumentException("ImgBBAPI에서 응답 데이터를 읽어들이지 못합니다."));

        /**
         * webClient.post()를 하면 data로 응답데이터를 받아오는데. 그걸 통으로 받아오는게 ImgBbInfoDto이다.
         * 그 ImgBbInfoDto Data를 호출해서 객체를 추출한 다음 그걸 ImgBgData에 담아주는데.
         * ImgBbInfoDto에 data가 ImgBbDto 타입이어서 getData하게 되면 ImgBbDto 값만 가져와서 ImgBbDto에 담아주게 된다.
         */
        // 필요한 정보만 파싱하여 ImageResponseDto 에 담아 반환
        return ImageResponseDto
            .builder()
            .image(imgBbDto.getImage().getUrl())
            .thumb(imgBbDto.getThumb().getUrl())
            .build();
    }

    private static class NamedByteArrayResource extends ByteArrayResource {
        private final String filename;

        public NamedByteArrayResource(byte[] byteList, String filename) {
            super(byteList); // 새 ByteArrayResource를 생성합니다.
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return filename;
        }
    }
}
