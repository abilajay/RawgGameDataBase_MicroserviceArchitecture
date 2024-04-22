package com.gamediscovery.gamemicroservice.messaging;

import com.gamediscovery.gamemicroservice.dto.GameDto;
import com.gamediscovery.gamemicroservice.service.GameService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class GameMessageConsumer {
    private final GameService gameService;

    public GameMessageConsumer(GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues = "UpdatedGameQueue")
    public void updatedGameMessageConsumer(GameDto gameDto){
        System.out.println(gameDto.getGenre());
        gameService.updateGameGenreAndPublisher(gameDto);
    }

}

