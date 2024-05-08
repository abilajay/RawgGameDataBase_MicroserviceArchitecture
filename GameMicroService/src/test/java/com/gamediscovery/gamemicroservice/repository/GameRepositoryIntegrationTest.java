package com.gamediscovery.gamemicroservice.repository;

import com.gamediscovery.gamemicroservice.entity.Game;
import com.gamediscovery.gamemicroservice.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // Use a test profile for H2 configuration
public class GameRepositoryIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testSaveAndFindGame() {
        // Create a test Game
        Game game = new Game();
        game.setUniqueId(123L);
        game.setName("Test Game");
        game.setReleased(new Date());

        // Save the game
        Game savedGame = gameRepository.save(game);

        // Assert that the game was saved
        assertThat(savedGame).isNotNull();
        assertThat(savedGame.getId()).isNotNull();

        // Find the saved game by ID
        Optional<Game> foundGame = gameRepository.findById(savedGame.getId());

        // Assert that the found game matches the saved game
        assertThat(foundGame).isPresent();
        assertThat(foundGame.get()).isEqualTo(savedGame);
    }

    // Add more tests for other repository methods as needed
}