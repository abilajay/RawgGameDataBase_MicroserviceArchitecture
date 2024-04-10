package com.gamediscovery.game_platform.controller;

import com.gamediscovery.game_platform.entity.GamePlatform;
import com.gamediscovery.game_platform.service.GamePlatformService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-platform")
public class GamePlatformController {

    private final GamePlatformService gamePlatformService;

    public GamePlatformController(GamePlatformService gamePlatformService) {
        this.gamePlatformService = gamePlatformService;
    }

    @PostMapping()
    public ResponseEntity<GamePlatform> createRecord(@RequestBody GamePlatform gamePlatform){
        return new ResponseEntity<>(gamePlatformService.createRecord(gamePlatform), HttpStatus.CREATED);
    }

    @GetMapping(params = "gameId")
    public ResponseEntity<List<GamePlatform>> fetchRecordByGameId(@RequestParam Long gameId){
        return new ResponseEntity<>(gamePlatformService.fetchPlatformsIdByGameId(gameId), HttpStatus.OK);
    }

    @GetMapping(params = "platformId")
    public ResponseEntity<List<GamePlatform>> fetchRecordByPlatformId(@RequestParam Long platformId){
        return new ResponseEntity<>(gamePlatformService.fetchGamesIdByPlatformId(platformId), HttpStatus.OK);
    }

}
