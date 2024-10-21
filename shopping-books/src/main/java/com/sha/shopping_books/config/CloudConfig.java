package com.sha.shopping_books.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> map = new HashMap<>();
        String CLOUD_NAME = "zzzz";
        map.put("cloud_name", CLOUD_NAME);
        String API_KEY = "zzzzz";
        map.put("api_key", API_KEY);
        String SECRET_KEY = "zzzzz";
        map.put("api_secret", SECRET_KEY);
        return new Cloudinary(map);
    }
}
