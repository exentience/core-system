package com.leveling.core_system.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;

@Configuration 
@AllArgsConstructor 
public class WebConfig implements WebMvcConfigurer {
    
    private final JwtInterceptor jwtInterceptor;

    @Override 
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/player/**")
                .excludePathPatterns("/player", "/player/login");
    }
}
