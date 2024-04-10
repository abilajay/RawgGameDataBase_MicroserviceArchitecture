package com.gamediscovery.gamemicroservice.service.impl;

import com.gamediscovery.gamemicroservice.clients.GamePlatformClient;
import com.gamediscovery.gamemicroservice.clients.GenreClient;
import com.gamediscovery.gamemicroservice.clients.PlatformClient;
import com.gamediscovery.gamemicroservice.clients.PublisherClient;
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

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final GenreClient genreClient;

    private final PlatformClient platformClient;

    private final GamePlatformClient gamePlatformClient;

    private final PublisherClient publisherClient;


    public GameServiceImpl(GameRepository gameRepository, GenreClient genreClient, PlatformClient platformClient, GamePlatformClient gamePlatformClient, PublisherClient publisherClient) {
        this.gameRepository = gameRepository;
        this.genreClient = genreClient;
        this.platformClient = platformClient;
        this.gamePlatformClient = gamePlatformClient;
        this.publisherClient = publisherClient;
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
        List<Game> gameList = new ArrayList<>();
        for (Game game: gamePage.getContent()) {
            List<Long> platformIds = gamePlatformClient.fetchRecordByGameId(game.getId()).stream().map(GamePlatform::getPlatformId).toList();
            List<Platform> platforms = new ArrayList<>();
            for (Long platformId: platformIds) {
                platforms.add(platformClient.getPlatformById(platformId));
            }
            game.setPlatforms(platforms);
            gameList.add(game);
        }
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gameList);
        gamesResponse.setGameCount(gameRepository.count());
        return gamesResponse;
    }



    @Override
    public Game createGame(Game game) {

        // Save Platform
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

        // Check GenreId Is Exist Or Not
        Long genreId = game.getGenreId();
        genreClient.getGenreById(genreId);

        // Save Game_Platform Relationship Record
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
    public GamesResponse getGamesByPublisherId(Long publisherId, int pageNo, int pageSize, String sortBy, String order){
        PageRequest pageable = createPageRequest(pageNo, pageSize, sortBy, order);
        Page<Game> gamePage = gameRepository.findByPublisherId(publisherId, pageable);
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gamePage.getContent());
        gamesResponse.setGameCount(gameRepository.countByPublisherId(publisherId));
        return gamesResponse;
    }

    @Override
    public GamesResponse getGamesByPublisherName(String publisherName, int pageNo, int pageSize, String sortBy, String order){
        Long publisherId = publisherClient.getPublisherByName(publisherName).getId();
        PageRequest pageable = createPageRequest(pageNo, pageSize, sortBy, order);
        Page<Game> gamePage = gameRepository.findByPublisherId(publisherId, pageable);
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gamePage.getContent());
        gamesResponse.setGameCount(gameRepository.countByPublisherId(publisherId));
        return gamesResponse;
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
