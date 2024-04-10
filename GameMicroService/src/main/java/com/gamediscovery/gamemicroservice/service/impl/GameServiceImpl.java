package com.gamediscovery.gamemicroservice.service.impl;

import com.gamediscovery.gamemicroservice.clients.GamePlatformClient;
import com.gamediscovery.gamemicroservice.clients.GenreClient;
import com.gamediscovery.gamemicroservice.clients.PlatformClient;
import com.gamediscovery.gamemicroservice.dto.GamePlatform;
import com.gamediscovery.gamemicroservice.dto.GamesResponse;
import com.gamediscovery.gamemicroservice.entity.Game;
import com.gamediscovery.gamemicroservice.entity.Screenshot;
import com.gamediscovery.gamemicroservice.entity.Trailer;
import com.gamediscovery.gamemicroservice.exception.GameNotFoundException;
import com.gamediscovery.gamemicroservice.external.Genre;
import com.gamediscovery.gamemicroservice.external.Platform;
import com.gamediscovery.gamemicroservice.repository.GameRepository;
import com.gamediscovery.gamemicroservice.repository.ScreenshotRepository;
import com.gamediscovery.gamemicroservice.service.GameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final GenreClient genreClient;

    private final PlatformClient platformClient;

    private final GamePlatformClient gamePlatformClient;


    public GameServiceImpl(GameRepository gameRepository, ScreenshotRepository screenshotRepository, GenreClient genreClient, PlatformClient platformClient, GamePlatformClient gamePlatformClient) {
        this.gameRepository = gameRepository;
        this.genreClient = genreClient;
        this.platformClient = platformClient;
        this.gamePlatformClient = gamePlatformClient;
    }

    @Override
    public Game fetchGameById(Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        return optionalGame.orElseThrow(() -> new GameNotFoundException(gameId));
    }

    @Override
    public GamesResponse fetchAllGames(int pageNo, int pageSize, String sortBy, String order) {
        PageRequest pageable = createPageRequest(pageNo, pageSize, sortBy, order);
        Page<Game> gamePage = gameRepository.findAll(pageable);
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gamePage.getContent());
        gamesResponse.setGameCount(gameRepository.count());
        return gamesResponse;
    }



    @Override
    public Game createGame(Game game) {

        List<Platform> platforms = game.getPlatforms();
        List<Platform> createdPlatforms = platformClient.createMultiplePlatform(platforms);
        game.getPlatforms().clear();
        game.setPlatforms(createdPlatforms);
        // Save Screenshots and set Game reference:
        Set<Screenshot> screenshots = new HashSet<>();
        Set<Screenshot> userScreenshots = game.getScreenshots();
        if (userScreenshots != null) {
            for (Screenshot ss :
                    game.getScreenshots()) {
                ss.setGame(game);
                screenshots.add(ss);
            }
        }

        // Save Trailers and set Game reference
        Set<Trailer> trailers = new HashSet<>();
        Set<Trailer> userTrailer = game.getTrailers();
        if (userTrailer != null){
            for (Trailer trailer: userTrailer) {
                trailer.setGame(game);
                trailers.add(trailer);
            }
        }

        game.setScreenshots(screenshots);
        game.setTrailers(trailers);
        Long genreId = game.getGenreId();
        genreClient.getGenreById(genreId);
        Game newGame = gameRepository.save(game);
        Long newGameId = newGame.getId();
        for (Platform pt:
             createdPlatforms) {
            GamePlatform gamePlatform = new GamePlatform();
            gamePlatform.setGameId(newGameId);
            gamePlatform.setPlatformId(pt.getId());
            gamePlatformClient.createRecord(gamePlatform);
        }
        return newGame;
    }

    @Override
    public Game updateGameById(Long gameId, Game updatedGame) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Game existingGame = optionalGame.orElseThrow(() -> new GameNotFoundException(gameId));

        // Update fields
        existingGame.setUniqueId(updatedGame.getUniqueId());
        existingGame.setName(updatedGame.getName());
        existingGame.setPlaytime(updatedGame.getPlaytime());
        existingGame.setBackground_image(updatedGame.getBackground_image());
        existingGame.setGenreId(updatedGame.getGenreId());
        existingGame.setPublisherId(updatedGame.getPublisherId());
        existingGame.setMetaCritic(updatedGame.getMetaCritic());
        existingGame.setRating_top(updatedGame.getRating_top());

        // Update trailers
        if (updatedGame.getTrailers() != null) {
            existingGame.getTrailers().clear(); // Clear existing trailers
            existingGame.getTrailers().addAll(updatedGame.getTrailers()); // Add updated trailers
        }

        // Update screenshots
        if (updatedGame.getScreenshots() != null) {
            existingGame.getScreenshots().clear(); // Clear existing screenshots
            existingGame.getScreenshots().addAll(updatedGame.getScreenshots()); // Add updated screenshots
        }

        return gameRepository.save(existingGame);
    }

    @Override
    public String deleteGameById(Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent())
            gameRepository.deleteById(gameId);
        else
            throw new GameNotFoundException(gameId);
        return "Operation Successful";
    }

    @Override
    public GamesResponse getGamesByGenreId(Long genreId, int pageNo, int pageSize, String sortBy, String order) {
        PageRequest pageable = createPageRequest(pageNo, pageSize, sortBy, order);
        Page<Game> gamePage = gameRepository.findByGenreId(genreId, pageable);
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gamePage.getContent());
        gamesResponse.setGameCount(gameRepository.countByGenreId(genreId));
        return gamesResponse;
    }

    @Override
    public GamesResponse getGamesByGenreName(String genreName, int pageNo, int pageSize, String sortBy, String order) {
        Genre genre = genreClient.getGenreByName(genreName);
        Long genreId = genre.getId();
        PageRequest pageable = createPageRequest(pageNo, pageSize, sortBy, order);
        Page<Game> gamePage = gameRepository.findByGenreId(genreId, pageable);
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gamePage.getContent());
        gamesResponse.setGameCount(gameRepository.countByGenreId(genreId));
        return gamesResponse;
    }

    @Override
    public Long getGamesCountByGenreId(Long genreId) {
        return gameRepository.countByGenreId(genreId);
    }

    private PageRequest createPageRequest(int pageNo, int pageSize, String sortBy, String order) {
        if (order.equalsIgnoreCase("DESC")) {
            return PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        } else if (order.equalsIgnoreCase("ASC")) {
            return PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            throw new IllegalArgumentException("Invalid Order Query Parameter");
        }
    }
}
