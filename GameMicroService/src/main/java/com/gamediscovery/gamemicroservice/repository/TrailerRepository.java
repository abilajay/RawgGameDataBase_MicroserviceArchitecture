package com.gamediscovery.gamemicroservice.repository;

import com.gamediscovery.gamemicroservice.entity.Trailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TrailerRepository extends JpaRepository<Trailer, Long> {

    Set<Trailer> findByGameId(Long gameId);

}
