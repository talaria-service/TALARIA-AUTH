package com.yonyk.talaria.auth.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

// Swagger 설정파일
@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    Info info =
        new Info()
            .title("TALARIA_AUTH API 명세서")
            .description("TALARIA 서비스 인증서버의 API 명세서입니다.")
            .version("1.0.0");
    return new OpenAPI().info(info);
  }
}
