package com.example.demo.config; // ← ต้องตรงกับตำแหน่งไฟล์ของคุณ

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // อนุญาตให้ Camunda Modeler (หรือ Frontend อื่น) เรียกได้
        // ถ้าทดสอบ localhost อาจใช้ "*" ได้ แต่หากส่ง Production ควรระบุ Port ให้ชัดเจน
        config.addAllowedOriginPattern("*"); 
        
        // อนุญาตให้ส่ง Header ใดๆ ได้ (จำเป็นสำหรับ Camunda REST API)
        config.addAllowedHeader("*");
        
        // อนุญาตให้ใช้ Method ใดๆ (GET, POST, PUT, DELETE)
        config.addAllowedMethod("*");
        
        // อนุญาตให้ส่ง Cookie หรือ Authentication Header ได้
        config.setAllowCredentials(true);

        // กำหนดให้ Config นี้ใช้กับ Path ของ Camunda REST API เท่านั้น
        // เพื่อความปลอดภัย ไม่ควรเปิดทั้งหมดถ้าไม่จำเป็น
        source.registerCorsConfiguration("/engine-rest/**", config);

        return new CorsFilter(source);
    }
}