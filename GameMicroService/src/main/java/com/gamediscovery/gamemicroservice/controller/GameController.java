package com.gamediscovery.gamemicroservice.controller;

import com.gamediscovery.gamemicroservice.dto.GameDto;
import com.gamediscovery.gamemicroservice.dto.GamesResponse;
import com.gamediscovery.gamemicroservice.entity.Game;
import com.gamediscovery.gamemicroservice.entity.Screenshot;
import com.gamediscovery.gamemicroservice.entity.Trailer;
import com.gamediscovery.gamemicroservice.service.GameService;
import com.gamediscovery.gamemicroservice.service.ScreenshotService;
import com.gamediscovery.gamemicroservice.service.TrailerService;
import com.gamediscovery.gamemicroservice.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("games")
public class GameController {

    private final GameService gameService;

    private final ScreenshotService screenshotService;

    private final TrailerService trailerService;

    public GameController(GameService gameService, ScreenshotService screenshotService, TrailerService trailerService) {
        this.gameService = gameService;
        this.screenshotService = screenshotService;
        this.trailerService = trailerService;
    }

    @GetMapping()
    public ResponseEntity<GamesResponse> fetchAllGames(@RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                    @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                    @RequestParam(name = "order", defaultValue = AppConstants.DEFAULT_ORDER, required = false) String order){
        return new ResponseEntity<>(gameService.fetchAllGames(pageNo, pageSize, sortBy, order), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> fetchGameById(@PathVariable(name="id") Long gameId){
        return new ResponseEntity<>(gameService.fetchGameById(gameId), HttpStatus.OK);
    }

    @GetMapping("/uniqueId/{id}")
    public ResponseEntity<Game> fetchGameByUniqueId(@PathVariable(name="id") Long gameUniqueId){
        return new ResponseEntity<>(gameService.fetchGameByUniqueId(gameUniqueId), HttpStatus.OK);
    }

    @PostMapping ()
    public ResponseEntity<GameDto> createGame(@RequestBody GameDto gameDto){
        return new ResponseEntity<>(gameService.createGame(gameDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDto> updateGame(@RequestBody GameDto gameDto, @PathVariable(name = "id") Long gameId){
        return new ResponseEntity<>(gameService.updateGameById(gameId, gameDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable(name = "id") Long gameId){
        return new ResponseEntity<>(gameService.deleteGameById(gameId), HttpStatus.OK);
    }

    @GetMapping("/{id}/screenshots")
    public ResponseEntity<Set<Screenshot>> getGameScreenshots(@PathVariable(name = "id") Long gameId){
        return new ResponseEntity<>(screenshotService.getScreenshotByGameId(gameId), HttpStatus.OK);
    }

    @GetMapping("/{id}/trailers")
    public ResponseEntity<Set<Trailer>> getGameTrailers(@PathVariable(name = "id") Long gameId){
        return new ResponseEntity<>(trailerService.getTrailerByGameId(gameId), HttpStatus.OK);
    }

    @GetMapping(params = "genreId")
    public ResponseEntity<GamesResponse> fetchGamesByGenreId(@RequestParam(name = "genreId") Long genreId,
                                                             @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                             @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                             @RequestParam(name = "order", defaultValue = AppConstants.DEFAULT_ORDER, required = false) String order){
        return new ResponseEntity<>(gameService.getGamesByGenreId(genreId, pageNo, pageSize, sortBy, order), HttpStatus.OK);
    }

    @GetMapping(params = "genreName")
    public ResponseEntity<GamesResponse> fetchGamesByGenreName(@RequestParam(name = "genreName") String genreName,
                                                          @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                                          @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                          @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                          @RequestParam(name = "order", defaultValue = AppConstants.DEFAULT_ORDER, required = false) String order){
        return new ResponseEntity<>(gameService.getGamesByGenreName(genreName, pageNo, pageSize, sortBy, order), HttpStatus.OK);
    }

    @GetMapping(params = "publisherId")
    public ResponseEntity<GamesResponse> fetchGamesByPublisherId(@RequestParam(name = "publisherId") Long publisherId,
                                                             @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                             @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                             @RequestParam(name = "order", defaultValue = AppConstants.DEFAULT_ORDER, required = false) String order){
        return new ResponseEntity<>(gameService.getGamesByPublisherId(publisherId, pageNo, pageSize, sortBy, order), HttpStatus.OK);
    }

    @GetMapping(params = "publisherName")
    public ResponseEntity<GamesResponse> fetchGamesByPublisherName(@RequestParam(name = "publisherName") String publisherName,
                                                                 @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                                                 @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                 @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                 @RequestParam(name = "order", defaultValue = AppConstants.DEFAULT_ORDER, required = false) String order){
        return new ResponseEntity<>(gameService.getGamesByPublisherName(publisherName, pageNo, pageSize, sortBy, order), HttpStatus.OK);
    }

    @GetMapping(params = "platformName")
    public ResponseEntity<GamesResponse> fetchGamesByPlatformIdentifier(@RequestParam(name = "platformName") String platformName,
                                                                   @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                                                   @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                   @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                   @RequestParam(name = "order", defaultValue = AppConstants.DEFAULT_ORDER, required = false) String order){
        return new ResponseEntity<>(gameService.getGamesByPlatformIdentifier(platformName, pageNo, pageSize, sortBy, order), HttpStatus.OK);
    }

    @GetMapping(params = "platformId")
    public ResponseEntity<GamesResponse> fetchGamesByPlatformIdentifier(@RequestParam(name = "platformId") Long platformId,
                                                                        @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                                                        @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                        @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                        @RequestParam(name = "order", defaultValue = AppConstants.DEFAULT_ORDER, required = false) String order){
        return new ResponseEntity<>(gameService.getGamesByPlatformIdentifier(platformId, pageNo, pageSize, sortBy, order), HttpStatus.OK);
    }






}
