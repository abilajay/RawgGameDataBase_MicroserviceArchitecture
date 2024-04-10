package com.gamediscovery.Platform.service;

import com.gamediscovery.Platform.entity.Platform;

import java.util.List;

public interface PlatformService {
    Platform getPlatformById(Long platformId);

    List<Platform> getAllPlatform();

    Platform createPlatform(Platform platform);

    List<Platform> createMultiplePlatforms(List<Platform> platforms);

}
