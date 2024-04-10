package com.gamediscovery.gamemicroservice.service.impl;

import com.gamediscovery.gamemicroservice.entity.Game;
import com.gamediscovery.gamemicroservice.entity.Trailer;
import com.gamediscovery.gamemicroservice.exception.GameNotFoundException;
import com.gamediscovery.gamemicroservice.repository.GameRepository;
import com.gamediscovery.gamemicroservice.repository.TrailerRepository;
import com.gamediscovery.gamemicroservice.service.TrailerService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class TrailerServiceImpl implements TrailerService {

    private final TrailerRepository trailerRepository;

    private final GameRepository gameRepository;

    public TrailerServiceImpl(TrailerRepository trailerRepository, GameRepository gameRepository) {
        this.trailerRepository = trailerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Set<Trailer> getTrailerByGameId(Long gameId) {
        return gameRepository.findById(gameId)
                .map(game -> trailerRepository.findByGameId(gameId))
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }
}
