package com.gamediscovery.gamemicroservice.controller;

import com.gamediscovery.gamemicroservice.controller.GameController;
import com.gamediscovery.gamemicroservice.dto.GameDto;
import com.gamediscovery.gamemicroservice.dto.GamesResponse;
import com.gamediscovery.gamemicroservice.entity.Game;
import com.gamediscovery.gamemicroservice.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @Test
    public void testFetchAllGames() {
        // Prepare mock data
        List<GameDto> gameDtoList = new ArrayList<>();
        gameDtoList.add(new GameDto());
        GamesResponse gamesResponse = new GamesResponse();
        gamesResponse.setGames(gameDtoList);

        // Mock service method
        when(gameService.fetchAllGames(any(Integer.class), any(Integer.class), any(String.class), any(String.class)))
                .thenReturn(gamesResponse);

        // Call controller method
        ResponseEntity<GamesResponse> response = gameController.fetchAllGames(0, 10, "id", "ASC");

        // Verify response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(gamesResponse);
    }

    @Test
    public void testFetchGameById() {
        // Prepare mock data
        GameDto gameDto = new GameDto();

        // Mock service method
        when(gameService.fetchGameById(323L)).thenReturn(gameDto);

        // Call controller method
        ResponseEntity<GameDto> response = gameController.fetchGameById(323L);

        // Verify response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(gameDto);
    }

    @Test
    public void testFetchGameByUniqueId() {
        // Prepare mock data
        Game game = new Game();
        game.setUniqueId(1234L);

        // Mock service method
        when(gameService.fetchGameByUniqueId(anyLong())).thenReturn(game);

        // Call controller method
        ResponseEntity<Game> response = gameController.fetchGameByUniqueId(1234L);

        // Verify response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(game);
    }

    @Test
    public void testCreateGame() {
        // Prepare mock data
        GameDto gameDto = new GameDto();
        gameDto.setName("Test Game");

        // Mock service method
        when(gameService.createGame(any(GameDto.class))).thenReturn(gameDto);

        // Call controller method
        ResponseEntity<GameDto> response = gameController.createGame(gameDto);

        // Verify response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(gameDto);
    }

    @Test
    public void testUpdateGame() {
        // Prepare mock data
        GameDto gameDto = new GameDto();
        gameDto.setName("Updated Game");

        // Mock service method
        when(gameService.updateGameById(anyLong(), any(GameDto.class))).thenReturn(gameDto);

        // Call controller method
        ResponseEntity<GameDto> response = gameController.updateGame(gameDto, 1L);

        // Verify response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(gameDto);
    }

    // Add more tests for other repository methods similarly
}
