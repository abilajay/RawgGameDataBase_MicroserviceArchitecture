package com.gamediscovery.gamemicroservice.service;

import com.gamediscovery.gamemicroservice.dto.GamesResponse;
import com.gamediscovery.gamemicroservice.entity.Game;


public interface GameService {
    Game fetchGameById(Long gameId);

    Game fetchGameByUniqueId(Long uniqueId);

    GamesResponse fetchAllGames(int pageNo, int pageSize, String sortBy, String order);

    Game createGame(Game game);

    Game updateGameById(Long gameId, Game game);

    String deleteGameById(Long gameId);

    GamesResponse getGamesByGenreId(Long genreId, int pageNo, int pageSize, String sortBy, String order);

    GamesResponse getGamesByGenreName(String genreName, int pageNo, int pageSize, String sortBy, String order);


    GamesResponse getGamesByPublisherId(Long publisherId, int pageNo, int pageSize, String sortBy, String order);

    GamesResponse getGamesByPublisherName(String publisherName, int pageNo, int pageSize, String sortBy, String order);

    GamesResponse getGamesByPlatformIdentifier(Long platformId, int pageNo, int pageSize, String sortBy, String order);

    GamesResponse getGamesByPlatformIdentifier(String platformName, int pageNo, int pageSize, String sortBy, String order);
}
