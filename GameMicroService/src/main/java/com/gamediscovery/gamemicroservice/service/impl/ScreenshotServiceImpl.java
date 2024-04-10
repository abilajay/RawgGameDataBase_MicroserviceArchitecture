package com.gamediscovery.gamemicroservice.service.impl;

import com.gamediscovery.gamemicroservice.entity.Game;
import com.gamediscovery.gamemicroservice.entity.Screenshot;
import com.gamediscovery.gamemicroservice.exception.GameNotFoundException;
import com.gamediscovery.gamemicroservice.repository.GameRepository;
import com.gamediscovery.gamemicroservice.repository.ScreenshotRepository;
import com.gamediscovery.gamemicroservice.service.ScreenshotService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ScreenshotServiceImpl implements ScreenshotService {

    private final ScreenshotRepository screenshotRepository;

    private final GameRepository gameRepository;

    public ScreenshotServiceImpl(ScreenshotRepository screenshotRepository, GameRepository gameRepository) {
        this.screenshotRepository = screenshotRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Set<Screenshot> getScreenshotByGameId(Long gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent())
            return screenshotRepository.findByGameId(gameId);
        else
            throw new GameNotFoundException(gameId);
    }
}
