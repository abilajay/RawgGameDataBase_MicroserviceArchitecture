package com.gamediscovery.gamemicroservice.service.impl;

import com.gamediscovery.gamemicroservice.clients.GamePlatformClient;
import com.gamediscovery.gamemicroservice.clients.GenreClient;
import com.gamediscovery.gamemicroservice.clients.PlatformClient;
import com.gamediscovery.gamemicroservice.clients.PublisherClient;
import com.gamediscovery.gamemicroservice.dto.GameDto;
import com.gamediscovery.gamemicroservice.dto.GamePlatform;
import com.gamediscovery.gamemicroservice.entity.Game;
import com.gamediscovery.gamemicroservice.entity.Screenshot;
import com.gamediscovery.gamemicroservice.external.Genre;
import com.gamediscovery.gamemicroservice.external.Platform;
import com.gamediscovery.gamemicroservice.external.Publisher;
import com.gamediscovery.gamemicroservice.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GenreClient genreClient;

    @Mock
    private PlatformClient platformClient;

    @Mock
    private GamePlatformClient gamePlatformClient;

    @Mock
    private PublisherClient publisherClient;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    void fetchGameById() {

        //Preparing the data
        Game game = new Game();
        Screenshot screenshot = new Screenshot();
        screenshot.setImage("TestURL");
        Set<Screenshot> screenshotSet = new HashSet<>();
        screenshotSet.add(screenshot);
        game.setName("GTA");
        game.setId(5L);
        game.setScreenshots(screenshotSet);
        game.setPublisherId(12L);
        game.setGenreId(12L);
        Optional<Game> optionalGame = Optional.of(game);

        Genre genre = new Genre();
        genre.setId(12L);
        genre.setName("Action");
        ResponseEntity<Genre> genreResponseEntity = new ResponseEntity<>(genre, HttpStatus.OK);

        Publisher publisher = new Publisher();
        publisher.setId(12L);
        publisher.setName("RockStar");
        ResponseEntity<Publisher> publisherResponseEntity = new ResponseEntity<>(publisher, HttpStatus.OK);

        List<GamePlatform> gamePlatforms = new ArrayList<>();
        GamePlatform gamePlatform = new GamePlatform();
        gamePlatform.setPlatformId(2L);
        gamePlatform.setGameId(5L);
        GamePlatform gamePlatform2 = new GamePlatform();
        gamePlatform2.setPlatformId(3L);
        gamePlatform2.setGameId(5L);
        gamePlatforms.add(gamePlatform);gamePlatforms.add(gamePlatform2);

        Platform platform1 = new Platform();
        platform1.setId(2L);
        Platform platform2 = new Platform();
        platform2.setId(3L);



        //Method Stubbing
        when(gameRepository.findById(any(Long.class))).thenReturn(optionalGame);
        when(genreClient.getGenreByIdForGame(any(Long.class))).thenReturn(genreResponseEntity);
        when(publisherClient.getPublisherByIdForGame(any(Long.class))).thenReturn(publisherResponseEntity.getBody());
        when(gamePlatformClient.fetchRecordByGameId(any(Long.class))).thenReturn(gamePlatforms);
        when(platformClient.getPlatformById(2L)).thenReturn(platform1);
        when(platformClient.getPlatformById(3L)).thenReturn(platform2);

        //Calling service method
        GameDto gameDto = gameService.fetchGameById(5L);

        assertAll(()->assertThat(gameDto).isNotNull(),
                  ()-> {
                      assert gameDto != null;
                      assertThat(gameDto.getName()).isEqualTo(game.getName());
                  },
                  ()-> {
                      assert gameDto != null;
                      assertThat(gameDto.getPlatforms().get(0).getId()).isEqualTo(2L);
                  },
                  ()-> {
                      assert gameDto != null;
                      assertThat(gameDto.getPublisher().getName()).isEqualTo("RockStar");
        }
        );
    }

    @Test
    void createGame() {
    }
}