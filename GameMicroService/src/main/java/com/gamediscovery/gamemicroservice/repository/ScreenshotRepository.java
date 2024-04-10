package com.gamediscovery.gamemicroservice.repository;

import com.gamediscovery.gamemicroservice.entity.Screenshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {
    Set<Screenshot> findByGameId(Long id);
}
