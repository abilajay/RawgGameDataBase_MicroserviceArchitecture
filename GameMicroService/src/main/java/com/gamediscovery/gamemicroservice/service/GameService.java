package com.gamediscovery.gamemicroservice.service;

import com.gamediscovery.gamemicroservice.dto.GamesResponse;
import com.gamediscovery.gamemicroservice.entity.Game;

import java.util.List;

public interface GameService {
    Game fetchGameById(Long gameId);

    GamesResponse fetchAllGames(int pageNo, int pageSize, String sortBy, String order);

    Game createGame(Game game);

    Game updateGameById(Long gameId, Game game);

    String deleteGameById(Long gameId);

    GamesResponse getGamesByGenreId(Long genreId, int pageNo, int pageSize, String sortBy, String order);

    GamesResponse getGamesByGenreName(String genreName, int pageNo, int pageSize, String sortBy, String order);

    Long getGamesCountByGenreId(Long genreId);


}
