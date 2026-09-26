package com.leveling.core_system.data.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter 
@Builder 
public class LoginRs {
    
    private UUID playerId;
    private String username;
    private String token;

}
