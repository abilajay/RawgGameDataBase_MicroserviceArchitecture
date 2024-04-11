package com.gamediscovery.Platform.controller;

import com.gamediscovery.Platform.entity.Platform;
import com.gamediscovery.Platform.service.PlatformService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platforms")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public ResponseEntity<List<Platform>> getAllPlatform(){
        return new ResponseEntity<>(platformService.getAllPlatform(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Platform> getPlatformById(@PathVariable(name = "id") Long platformId){
        return new ResponseEntity<>(platformService.getPlatformById(platformId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<Platform>> createMultiplePlatform(@RequestBody List<Platform> platforms){
        return new ResponseEntity<>(platformService.createMultiplePlatforms(platforms), HttpStatus.CREATED);
    }

    @PostMapping(params = "singlePlatform")
    public ResponseEntity<Platform> createPlatform(@RequestBody Platform platform){
        return new ResponseEntity<>(platformService.createPlatform(platform), HttpStatus.CREATED);
    }

    @GetMapping(params = "platformName")
    public ResponseEntity<Platform> getPlatformByName(@RequestParam String platformName){
        return new ResponseEntity<>(platformService.getPlatformByName(platformName), HttpStatus.OK);
    }



}
