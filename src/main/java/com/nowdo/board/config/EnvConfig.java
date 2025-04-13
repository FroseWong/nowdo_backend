//package com.nowdo.board.config;
//
//import io.github.cdimascio.dotenv.Dotenv;
//import jakarta.annotation.PostConstruct;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class EnvConfig {
//
//    @PostConstruct
//    public void loadEnv() {
//        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
//
//        // 將 .env 中的變數寫入系統屬性
//        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
//        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
//        System.setProperty("FRONTEND_URL", dotenv.get("FRONTEND_URL"));
//    }
//}