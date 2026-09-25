package com.leveling.core_system.data.dto;

import lombok.Getter;

@Getter 
public class UpdatePasswordRq {
    
    private String oldPassword;
    private String newPassword;
        
}
