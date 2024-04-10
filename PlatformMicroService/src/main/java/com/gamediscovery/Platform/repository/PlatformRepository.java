package com.gamediscovery.Platform.repository;

import com.gamediscovery.Platform.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Optional<Platform> findByName(String platformName);
}
