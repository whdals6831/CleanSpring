package com.jjm.cleanspring.infrastructure.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI().info(createApiInfo());
    }

    private Info createApiInfo() {
        return new Info().version("v1.0.0")
                         .title("CleanSpring API")
                         .description("CleanSpring API 목록입니다.");
    }
}