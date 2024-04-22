package com.gamediscovery.gamemicroservice.service.impl;

import com.gamediscovery.gamemicroservice.clients.GamePlatformClient;
import com.gamediscovery.gamemicroservice.clients.GenreClient;
import com.gamediscovery.gamemicroservice.clients.PlatformClient;
import com.gamediscovery.gamemicroservice.clients.PublisherClient;
import com.gamediscovery.gamemicroservice.dto.GameDto;
import com.gamediscovery.gamemicroservice.dto.GamePlatform;
import com.gamediscovery.gamemicroservice.dto.GamesResponse;
import com.gamediscovery.gamemicroservice.entity.Game;
import com.gamediscovery.gamemicroservice.entity.Screenshot;
import com.gamediscovery.gamemicroservice.entity.Trailer;
import com.gamediscovery.gamemicroservice.exception.GameNotFoundException;
import com.gamediscovery.gamemicroservice.external.Genre;
import com.gamediscovery.gamemicroservice.external.Platform;
import com.gamediscovery.gamemicroservice.repository.GameRepository;
import com.gamediscovery.gamemicroservice.service.GameService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    private final RabbitTemplate rabbitTemplate;


    public GameServiceImpl(GameRepository gameRepository, GenreClient genreClient, PlatformClient platformClient, GamePlatformClient gamePlatformClient, PublisherClient publisherClient, RabbitTemplate rabbitTemplate) {
        this.gameRepository = gameRepository;
        this.genreClient = genreClient;
        this.platformClient = platformClient;
        this.gamePlatformClient = gamePlatformClient;
        this.publisherClient = publisherClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Retry(name = "myRetry")
    @RateLimiter(name = "myRateLimiter")
    @Override
    public GameDto fetchGameById(Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Game game = optionalGame.orElseThrow(() -> new GameNotFoundException(gameId));
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setUniqueId(game.getUniqueId());
        gameDto.setName(game.getName());
        gameDto.setMetaCritic(game.getMetaCritic());
        gameDto.setPlaytime(game.getPlaytime());
        gameDto.setRating_top(game.getRating_top());
        gameDto.setReleased(game.getReleased());
        gameDto.setScreenshots(game.getScreenshots());
        gameDto.setTrailers(game.getTrailers());
        gameDto.setBackground_image(game.getBackground_image());
        gameDto.setGenre(genreClient.getGenreByIdForGame(game.getGenreId()).getBody());
        gameDto.setPublisher(publisherClient.getPublisherByIdForGame(game.getPublisherId()));
        List<GamePlatform> gamePlatforms =gamePlatformClient.fetchRecordByGameId(game.getId());
        List<Platform> platforms = gamePlatforms.stream().map((gp) -> platformClient.getPlatformById(gp.getPlatformId())).toList();
        gameDto.setPlatforms(platforms);
        return gameDto;
    }


    @Retry(name = "myRetry")
    @RateLimiter(name = "myRateLimiter")
    @Override
    public Game fetchGameByUniqueId(Long uniqueId) {
        Optional<Game> optionalGame = gameRepository.findByUniqueId(uniqueId);
        Game game = optionalGame.orElseThrow(GameNotFoundException::new);
        List<GamePlatform> gamePlatforms =gamePlatformClient.fetchRecordByGameId(game.getId());
        List<Platform> platforms = gamePlatforms.stream().map((gp) -> platformClient.getPlatformById(gp.getPlatformId())).toList();
        game.setPlatforms(platforms);
        return game;
    }

    @Retry(name = "myRetry")
    @RateLimiter(name = "myRateLimiter")
    @Override
    public GamesResponse fetchAllGames(int pageNo, int pageSize, String sortBy, String order) {
        PageRequest pageable = createPageRequest(pageNo, pageSize, sortBy, order);
        Page<Game> gamePage = gameRepository.findAll(pageable);
        List<Game> gameList = fetchPlatformsByGames(gamePage);
        List<GameDto> gameDtoList = getGameDtos(gameList);
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gameDtoList);
        gamesResponse.setGameCount(gameRepository.count());
        return gamesResponse;
    }



    @Retry(name = "myRetry")
    @Override
    public GameDto createGame(GameDto gameDto) {

        Game game = new Game();
        game.setName(gameDto.getName());
        game.setUniqueId(gameDto.getUniqueId());
        game.setMetaCritic(gameDto.getMetaCritic());
        game.setRating_top(gameDto.getRating_top());
        game.setPlaytime(gameDto.getPlaytime());
        game.setBackground_image(gameDto.getBackground_image());
        game.setReleased(gameDto.getReleased());
        game.setScreenshots(gameDto.getScreenshots());
        game.setTrailers(gameDto.getTrailers());

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

        gameDto.setScreenshots(screenshots);
        gameDto.setTrailers(trailers);

        // Save Game_Platform Relationship Record
        Game newGame = gameRepository.save(game);
        gameDto.setId(newGame.getId());
        rabbitTemplate.convertAndSend("GameExchange", "platform-routing-key", gameDto);
        rabbitTemplate.convertAndSend("GameExchange", "genre-routing-key", gameDto);
        return gameDto;
    }


    @Retry(name = "myRetry")
    @Override
    public GameDto updateGameById(Long gameId, GameDto gameDto) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Game existingGame = optionalGame.orElseThrow(() -> new GameNotFoundException(gameId));
        // Update fields
        existingGame.setUniqueId(gameDto.getUniqueId());
        existingGame.setName(gameDto.getName());
        existingGame.setPlaytime(gameDto.getPlaytime());
        existingGame.setBackground_image(gameDto.getBackground_image());
        existingGame.setMetaCritic(gameDto.getMetaCritic());
        existingGame.setRating_top(gameDto.getRating_top());
        Game updatedGame = gameRepository.save(existingGame);
        gameDto.setId(updatedGame.getId());
        gameDto.setTrailers(updatedGame.getTrailers());
        gameDto.setScreenshots(updatedGame.getScreenshots());
        if (gameDto.getGenre() != null)
            rabbitTemplate.convertAndSend("GameExchange", "genre-routing-key", gameDto);
        else if (gameDto.getPublisher() != null)
            rabbitTemplate.convertAndSend("GameExchange", "publisher-routing-key", gameDto);
        if (gameDto.getPlatforms() != null)
            rabbitTemplate.convertAndSend("GameExchange", "platform-routing-key", gameDto);
        return gameDto;

    }

    @Override
    public void updateGameGenreAndPublisher(GameDto gameDto) {
        gameRepository.findById(gameDto.getId()).ifPresent(game -> {
            if (gameDto.getGenre() != null)
                game.setGenreId(gameDto.getGenre().getId());
            if (gameDto.getPublisher() != null)
                game.setPublisherId(gameDto.getPublisher().getId());
            gameRepository.save(game);
        });
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


    @Retry(name = "myRetry")
    @RateLimiter(name = "myRateLimiter")
    @Override
    public GamesResponse getGamesByGenreId(Long genreId, int pageNo, int pageSize, String sortBy, String order) {
        genreClient.getGenreByIdForGame(genreId);
        PageRequest pageable = createPageRequest(pageNo, pageSize, sortBy, order);
        Page<Game> gamePage = gameRepository.findByGenreId(genreId, pageable);
        List<Game> gameList = fetchPlatformsByGames(gamePage);
        List<GameDto> gameDtoList = getGameDtos(gameList);
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gameDtoList);
        gamesResponse.setGameCount(gameRepository.countByGenreId(genreId));
        return gamesResponse;
    }

    @Retry(name = "myRetry")
    @RateLimiter(name = "myRateLimiter")
    @Override
    public GamesResponse getGamesByGenreName(String genreName, int pageNo, int pageSize, String sortBy, String order) {
        Genre genre = Objects.requireNonNull(genreClient.getGenreByName(genreName).getBody()).getGenre();
        Long genreId = genre.getId();
        PageRequest pageable = createPageRequest(pageNo, pageSize, sortBy, order);
        Page<Game> gamePage = gameRepository.findByGenreId(genreId, pageable);
        List<Game> gameList = fetchPlatformsByGames(gamePage);
        List<GameDto> gameDtoList = getGameDtos(gameList);
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gameDtoList);
        gamesResponse.setGameCount(gameRepository.countByGenreId(genreId));
        return gamesResponse;
    }


    @Retry(name = "myRetry")
    @RateLimiter(name = "myRateLimiter")
    @Override
    public GamesResponse getGamesByPublisherId(Long publisherId, int pageNo, int pageSize, String sortBy, String order){
        publisherClient.getPublisherByIdForGame(publisherId);
        return getGamesResponseByPlatformIdentity(pageNo, pageSize, sortBy, order, publisherId);
    }


    @Retry(name = "myRetry")
    @RateLimiter(name = "myRateLimiter")
    @Override
    public GamesResponse getGamesByPublisherName(String publisherName, int pageNo, int pageSize, String sortBy, String order){
        Long publisherId = Objects.requireNonNull(publisherClient.getPublisherByNameForGame(publisherName).getBody()).getId();
        return getGamesResponseByPlatformIdentity(pageNo, pageSize, sortBy, order, publisherId);
    }

    @Retry(name = "myRetry")
    @RateLimiter(name = "myRateLimiter")
    @Override
    public GamesResponse getGamesByPlatformIdentifier(Long platformId, int pageNo, int pageSize, String sortBy, String order) {
        publisherClient.getPublisherById(platformId);
        List<GamePlatform> gamePlatforms = gamePlatformClient.fetchRecordByPlatformId(platformId);
        return getGamesResponse(pageNo, pageSize, sortBy, order, gamePlatforms);
    }

    @Retry(name = "myRetry")
    @RateLimiter(name = "myRateLimiter")
    @Override
    public GamesResponse getGamesByPlatformIdentifier(String platformName, int pageNo, int pageSize, String sortBy, String order) {
        Platform platform = platformClient.getPlatformByName(platformName);
        List<GamePlatform> gamePlatforms = gamePlatformClient.fetchRecordByPlatformId(platform.getId());
        return getGamesResponse(pageNo, pageSize, sortBy, order, gamePlatforms);
    }


    private GamesResponse getGamesResponseByPlatformIdentity(int pageNo, int pageSize, String sortBy, String order, Long publisherId) {
        PageRequest pageable = createPageRequest(pageNo, pageSize, sortBy, order);
        Page<Game> gamePage = gameRepository.findByPublisherId(publisherId, pageable);
        List<Game> gameList = fetchPlatformsByGames(gamePage);
        List<GameDto> gameDtoList = getGameDtos(gameList);
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gameDtoList);
        gamesResponse.setGameCount(gameRepository.countByPublisherId(publisherId));
        return gamesResponse;
    }

    private List<GameDto> getGameDtos(List<Game> gameList) {
        List<GameDto> gameDtoList = new ArrayList<>();
        for (Game game: gameList){
            GameDto gameDto = new GameDto();
            gameDto.setId(game.getId());
            gameDto.setUniqueId(game.getUniqueId());
            gameDto.setName(game.getName());
            gameDto.setMetaCritic(game.getMetaCritic());
            gameDto.setPlaytime(game.getPlaytime());
            gameDto.setRating_top(game.getRating_top());
            gameDto.setReleased(game.getReleased());
            gameDto.setScreenshots(game.getScreenshots());
            gameDto.setTrailers(game.getTrailers());
            gameDto.setBackground_image(game.getBackground_image());
            gameDto.setGenre(genreClient.getGenreByIdForGame(game.getGenreId()).getBody());
            gameDto.setPublisher(publisherClient.getPublisherByIdForGame(game.getPublisherId()));
            gameDto.setPlatforms(game.getPlatforms());
            gameDtoList.add(gameDto);
        }
        return gameDtoList;
    }

    private GamesResponse getGamesResponse(int pageNo, int pageSize, String sortBy, String order, List<GamePlatform> gamePlatforms) {
        PageRequest pageable = createPageRequest(pageNo, pageSize, sortBy, order);
        List<Long> gameIds = new ArrayList<>();
        for (GamePlatform gp: gamePlatforms) {
            gameIds.add(gp.getGameId());
        }
        Page<Game> gamePage = gameRepository.findAllByGameIds(gameIds, pageable);
        List<Game> gameList = fetchPlatformsByGames(gamePage);
        List<GameDto> gameDtoList = getGameDtos(gameList);
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gameDtoList);
        gamesResponse.setGameCount((long) gamePlatforms.size());
        return gamesResponse;
    }

    public List<Game> fetchPlatformsByGames(Page<Game> gamePage){
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
        return gameList;
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
