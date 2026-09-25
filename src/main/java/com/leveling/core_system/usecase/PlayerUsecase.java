package com.leveling.core_system.usecase;

import java.util.UUID;

import com.leveling.core_system.data.dto.CreatePlayerRq;
import com.leveling.core_system.data.dto.CreatePlayerRs;
import com.leveling.core_system.data.dto.GetPlayerRs;
import com.leveling.core_system.data.dto.UpdatePasswordRq;

public interface PlayerUsecase {

    public CreatePlayerRs createPlayer(CreatePlayerRq player);

    public GetPlayerRs getPlayerById(UUID id);

    public void updatePasswordById(UUID id, UpdatePasswordRq updatePasswordRq);

    public void deletePlayerById(UUID id);
    
}
