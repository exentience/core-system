package com.leveling.core_system.data.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Builder  
public class CreatePlayerRs {
    
    private UUID id;
    private String email;
    private String username;

}
