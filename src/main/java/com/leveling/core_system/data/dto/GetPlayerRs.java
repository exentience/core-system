package com.leveling.core_system.data.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter 
@Builder 
public class GetPlayerRs {
    
    private UUID id;
    private String email;
    private String username;
    private LocalDateTime createdAt;
    private boolean active;
}
