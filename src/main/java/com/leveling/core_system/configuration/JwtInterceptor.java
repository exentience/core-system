package com.leveling.core_system.configuration;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.leveling.core_system.data.exception.ApiException;
import com.leveling.core_system.usecase.JwtUsecase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component 
@AllArgsConstructor 
public class JwtInterceptor implements HandlerInterceptor{
    
    private final JwtUsecase jwtUsecase;

    @Override 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new ApiException("Token not found!", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        try{
            UUID playerId = jwtUsecase.extractPlayerId(token);
            request.setAttribute("playerId", playerId);
        }catch(Exception e){
            throw new ApiException("Token is invalid or expired!", HttpStatus.UNAUTHORIZED);
        }

        return true;
    }

}
