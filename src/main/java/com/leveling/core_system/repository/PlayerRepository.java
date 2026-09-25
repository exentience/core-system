package com.leveling.core_system.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leveling.core_system.data.entity.Player;

@Repository 
public interface PlayerRepository extends JpaRepository<Player, UUID>{
    
    @Query(value = "SELECT EXISTS(SELECT 1 FROM players WHERE username = :username AND active = true)", nativeQuery = true)
    boolean existsAndActiveByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM players WHERE id = :id AND active = true", nativeQuery = true)
    Optional<Player> findActivePlayerById(@Param("id") UUID id);
}
