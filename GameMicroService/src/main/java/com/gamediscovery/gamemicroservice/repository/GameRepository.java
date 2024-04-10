package com.gamediscovery.gamemicroservice.repository;

import com.gamediscovery.gamemicroservice.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Long countByGenreId(Long genreId);

    Game findByUniqueId(Long uniqueId);

    Page<Game> findByGenreId(Long id, Pageable pageable);

    Page<Game> findByPublisherId(Long id, Pageable pageable);

    Long countByPublisherId(Long publisherId);

}
