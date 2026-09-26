package com.leveling.core_system.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leveling.core_system.data.dto.ApiResponse;
import com.leveling.core_system.data.dto.CreatePlayerRq;
import com.leveling.core_system.data.dto.CreatePlayerRs;
import com.leveling.core_system.data.dto.GetPlayerRs;
import com.leveling.core_system.data.dto.LoginRq;
import com.leveling.core_system.data.dto.LoginRs;
import com.leveling.core_system.data.dto.UpdatePasswordRq;
import com.leveling.core_system.usecase.PlayerUsecase;

import lombok.AllArgsConstructor;

@RestController 
@RequestMapping("/player")
@AllArgsConstructor 
public class PlayerController {
    
    private final PlayerUsecase playerUsecase;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginRs>> login(@RequestBody LoginRq request){

        LoginRs result = playerUsecase.login(request);

        ApiResponse<LoginRs> response = ApiResponse.<LoginRs>builder()
            .success(true)
            .message("Login Success")
            .data(result)
            .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreatePlayerRs>> createPlayer(@RequestBody CreatePlayerRq dto){

        CreatePlayerRs newPlayer = playerUsecase.createPlayer(dto);

        ApiResponse<CreatePlayerRs> response = ApiResponse.<CreatePlayerRs>builder()
            .success(true)
            .message("Created New Player")
            .data(newPlayer)
            .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetPlayerRs>> getPlayerById(@PathVariable UUID id){

        GetPlayerRs getPlayer = playerUsecase.getPlayerById(id);

        ApiResponse<GetPlayerRs> response = ApiResponse.<GetPlayerRs>builder()
            .success(true)
            .message("Get Player Data with ID : " + id)
            .data(getPlayer)
            .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<ApiResponse<Void>> updatePasswordById(
        @PathVariable UUID id,
        @RequestBody UpdatePasswordRq updatePasswordRq){

            playerUsecase.updatePasswordById(id, updatePasswordRq);

            ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Update Password with ID : " + id)
                .data(null)
                .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePlayerById(@PathVariable UUID id){
        
        playerUsecase.deletePlayerById(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Delete Player with ID : " + id)
                .data(null)
                .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
