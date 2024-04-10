package com.gamediscovery.game_platform.repository;

import com.gamediscovery.game_platform.entity.GamePlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamePlatformRepository extends JpaRepository<GamePlatform, Long> {
    List<GamePlatform> findByGameId(Long gameId);

    List<GamePlatform> findByPlatformId(Long platformId);
}
