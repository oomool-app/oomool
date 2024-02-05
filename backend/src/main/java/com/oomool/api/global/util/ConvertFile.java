package com.oomool.api.global.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oomool.api.domain.feed.dto.FeedImageDto;

import lombok.extern.log4j.Log4j2;

/**
 *
 * 클래스 목적 : 중복되는 파일 업로드를 외부로 빼서 재사용하기 위해.
 */
@Log4j2
@Component
public class ConvertFile {

    @Value("${file.path}")
    private String uploadPath;

    public FeedImageDto convertFile(MultipartFile file) throws IOException {
        // DB에 파일 저장
        String today = new SimpleDateFormat("yyMMdd").format(new Date());

        log.info("uploadPath : {}", uploadPath);

        File folder = new File(uploadPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();

        assert originalFileName != null;
        if (!originalFileName.isEmpty()) {
            String saveFileName = UUID.randomUUID().toString()
                + originalFileName.substring(originalFileName.lastIndexOf('.'));

            FeedImageDto feedImageDto = FeedImageDto
                .builder()
                .fileName(saveFileName)
                .folderName(today)
                .originalName(originalFileName)
                .url("임의의 임시 url")
                .build();

            file.transferTo(new File(folder, saveFileName));

            return feedImageDto;
        }
        return new FeedImageDto();
    }
}

