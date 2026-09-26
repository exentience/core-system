package com.leveling.core_system.usecase.implementation;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.leveling.core_system.data.dto.CreatePlayerRq;
import com.leveling.core_system.data.dto.CreatePlayerRs;
import com.leveling.core_system.data.dto.GetPlayerRs;
import com.leveling.core_system.data.dto.LoginRq;
import com.leveling.core_system.data.dto.LoginRs;
import com.leveling.core_system.data.dto.UpdatePasswordRq;
import com.leveling.core_system.data.entity.Player;
import com.leveling.core_system.data.exception.ApiException;
import com.leveling.core_system.repository.PlayerRepository;
import com.leveling.core_system.usecase.JwtUsecase;
import com.leveling.core_system.usecase.PlayerUsecase;

import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor
public class PlayerUsecaseImpl implements PlayerUsecase{

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUsecase jwtUsecase;

    @Override
    public CreatePlayerRs createPlayer(CreatePlayerRq player) {

        if(playerRepository.existsAndActiveByUsername(player.getUsername())){
            throw new ApiException("Username " + player.getUsername() + " already registered!", HttpStatus.CONFLICT);
        }
        
        String hashedPassword = passwordEncoder.encode(player.getPassword());

        Player newPlayer = Player.builder()
            .email(player.getEmail())
            .username(player.getUsername())
            .password(hashedPassword)
            .active(true)
            .build();

        Player savedPlayer = playerRepository.save(newPlayer);
        
        return CreatePlayerRs.builder()
            .id(savedPlayer.getId())
            .email(savedPlayer.getEmail())
            .username(savedPlayer.getUsername())
            .build();
    }

    @Override
    public GetPlayerRs getPlayerById(UUID id) {
        
        Player getPlayer = playerRepository.findActivePlayerById(id)
            // Lambda Expression
            .orElseThrow(() -> new ApiException("Player with id : " + id + " not found!", HttpStatus.NOT_FOUND));

        return GetPlayerRs.builder()
            .id(getPlayer.getId())
            .email(getPlayer.getEmail())
            .username(getPlayer.getUsername())
            .createdAt(getPlayer.getCreatedAt())
            .active(getPlayer.isActive())
            .build();

    }

    @Override
    public void updatePasswordById(UUID id, UpdatePasswordRq updatePasswordRq) {
        
        // Get Player berdasarkan ID dari Player
        Player getPlayer = playerRepository.findActivePlayerById(id)
            // Lambda Expression
            .orElseThrow(() -> new ApiException("Player with id : " + id + " not found!", HttpStatus.NOT_FOUND));

        // Pengecekan apakah password yang diisi kosong atau tidak
        if (!StringUtils.hasText(updatePasswordRq.getNewPassword())) {
            throw new ApiException("Password cannot be empty!", HttpStatus.BAD_REQUEST);
        }

        // Mencocokkan Password lama yang baru di input dengan yang tersimpan di database
        boolean isPasswordMatcb = passwordEncoder.matches(updatePasswordRq.getOldPassword(), getPlayer.getPassword());

        if(!isPasswordMatcb){
            throw new ApiException("Old Password is not match!", HttpStatus.UNAUTHORIZED);
        }      

        getPlayer.setPassword(passwordEncoder.encode(updatePasswordRq.getNewPassword()));
        playerRepository.save(getPlayer);
    }

    @Override
    public void deletePlayerById(UUID id) {
        
        Player getPlayer = playerRepository.findActivePlayerById(id)
            .orElseThrow(() -> new ApiException("Player with id : " + id + " not found!", HttpStatus.NOT_FOUND));
        
        getPlayer.setActive(false);
        playerRepository.save(getPlayer);
    }

    @Override
    public LoginRs login(LoginRq loginRq) {
        
        Player getPlayer = playerRepository.findActivePlayerByUsername(loginRq.getUsername())
            .orElseThrow(() -> new ApiException("Username or password is wrong!", HttpStatus.UNAUTHORIZED));
        
        boolean isPasswordMatcb = passwordEncoder.matches(loginRq.getPassword(), getPlayer.getPassword());
        
        if(!isPasswordMatcb){
            throw new ApiException("Username or password is wrong!", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUsecase.generateToken(getPlayer.getId(), getPlayer.getUsername());

        return LoginRs.builder()
            .playerId(getPlayer.getId())
            .username(getPlayer.getUsername())
            .token(token)
            .build();
    }
    
}
