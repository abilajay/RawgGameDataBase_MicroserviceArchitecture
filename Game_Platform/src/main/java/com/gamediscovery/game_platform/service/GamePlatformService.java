package com.gamediscovery.game_platform.service;

import com.gamediscovery.game_platform.entity.GamePlatform;

import java.util.List;

public interface GamePlatformService {
    GamePlatform createRecord(GamePlatform gamePlatform);

    List<GamePlatform> fetchPlatformsIdByGameId(Long gameId);

    List<GamePlatform> fetchGamesIdByPlatformId(Long platformId);

    void deleteRecordByGameId(Long gameId);
}