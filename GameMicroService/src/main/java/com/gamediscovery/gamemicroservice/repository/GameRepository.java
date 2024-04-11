package com.gamediscovery.gamemicroservice.repository;

import com.gamediscovery.gamemicroservice.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Long countByGenreId(Long genreId);

    Game findByUniqueId(Long uniqueId);

    Page<Game> findByGenreId(Long id, Pageable pageable);

    Page<Game> findByPublisherId(Long id, Pageable pageable);

    Long countByPublisherId(Long publisherId);

    @Query("SELECT g FROM Game g WHERE g.id IN :userIds")
    Page<Game> findAllByGameIds(@Param("userIds") List<Long> gameIds, Pageable pageable);

}
