package com.mk.national_hospital_information.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwtSchemeName = "BearerAuth";

        return new OpenAPI()
            .info(new Info()
                .title("✨전국 병/의원 정보 API✨")
                .version("1.0.0")
                .description(""
                    + "🚩 JWT 인증으로 보안 강화 "
                    + "🚩 전국 병/의원의 '병원명, 주소, 전화번호, 웹사이트 정보 검색' "
                    + "🚩 병/의원의 '리뷰' 작성(만족도 표시 가능) "
                    + "🚩 리뷰 내 '댓글'을 통해 커뮤니티 형성 "
                    + "🚩 리뷰와 댓글에 '좋아요' 가능" ))
            // Security 적용
            .addSecurityItem(new SecurityRequirement().addList(jwtSchemeName))
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                    .name(jwtSchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }

}
