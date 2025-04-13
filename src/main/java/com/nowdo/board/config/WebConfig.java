package com.nowdo.board.config; // ← 放在 config 資料夾

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*") // ⚠️ 這邊改成你的前端開發位址
                        .allowedMethods("*") // GET, POST, PATCH, DELETE, ...
                        .allowedHeaders("*");

            }
        };
    }
}
