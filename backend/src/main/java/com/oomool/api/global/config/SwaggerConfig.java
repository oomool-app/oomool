package com.oomool.api.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Oomool API")
                .description("Oomool 서비스에 필요한 API를 명세합니다.")
                .version("0.0.1"));
    }
}
