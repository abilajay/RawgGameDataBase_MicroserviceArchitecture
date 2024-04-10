package com.gamediscovery.game_platform.service.impl;

import com.gamediscovery.game_platform.entity.GamePlatform;
import com.gamediscovery.game_platform.repository.GamePlatformRepository;
import com.gamediscovery.game_platform.service.GamePlatformService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamePlatformImpl implements GamePlatformService {
    private final GamePlatformRepository gamePlatformRepository;

    public GamePlatformImpl(GamePlatformRepository gamePlatformRepository) {
        this.gamePlatformRepository = gamePlatformRepository;
    }

    @Override
    public GamePlatform createRecord(GamePlatform gamePlatform) {
        return gamePlatformRepository.save(gamePlatform);
    }

    @Override
    public List<GamePlatform> fetchPlatformsIdByGameId(Long gameId) {
        return gamePlatformRepository.findByGameId(gameId);
    }

    @Override
    public List<GamePlatform> fetchGamesIdByPlatformId(Long platformId) {
        return gamePlatformRepository.findByPlatformId(platformId);
    }

    @Override
    public void deleteRecordByGameId(Long gameId) {

    }
}
