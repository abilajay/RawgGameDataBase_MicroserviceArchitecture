package com.gamediscovery.Platform.service.impl;

import com.gamediscovery.Platform.entity.Platform;
import com.gamediscovery.Platform.exception.PlatformNotFoundException;
import com.gamediscovery.Platform.repository.PlatformRepository;
import com.gamediscovery.Platform.service.PlatformService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlatformServiceImpl implements PlatformService {
    private final PlatformRepository platformRepository;

    public PlatformServiceImpl(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public Platform getPlatformById(Long platformId) {
        return platformRepository.findById(platformId).orElseThrow(() ->new PlatformNotFoundException(platformId));
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public List<Platform> getAllPlatform() {
        return platformRepository.findAll();
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public Platform createPlatform(Platform platform) {
        Optional<Platform> optionalPlatform = platformRepository.findByName(platform.getName());
        if (optionalPlatform.isEmpty())
           return platformRepository.save(platform);
        else return null;
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public List<Platform> createMultiplePlatforms(List<Platform> platforms) {
        return platforms.stream()
                .map(pt -> platformRepository.findByName(pt.getName())
                        .orElseGet(() -> platformRepository.save(pt)))
                .collect(Collectors.toList());
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public Platform getPlatformByName(String platformName) {
        Optional<Platform> optionalPlatform = platformRepository.findByName(platformName);
        return optionalPlatform.orElseThrow(() -> new PlatformNotFoundException(platformName));
    }


}
