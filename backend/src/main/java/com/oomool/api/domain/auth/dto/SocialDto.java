package com.oomool.api.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialDto {
    private String providerId;
    private String provider;
    private int userId;
}
