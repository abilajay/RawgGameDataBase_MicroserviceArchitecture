package com.gamediscovery.gamemicroservice.service;

import com.gamediscovery.gamemicroservice.entity.Screenshot;

import java.util.Set;

public interface ScreenshotService {
    Set<Screenshot> getScreenshotByGameId(Long gameId);

}
