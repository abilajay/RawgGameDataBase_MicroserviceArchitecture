package com.gamediscovery.gamemicroservice.service;

import com.gamediscovery.gamemicroservice.entity.Trailer;

import java.util.Set;

public interface TrailerService {
    Set<Trailer> getTrailerByGameId(Long gameId);
}
