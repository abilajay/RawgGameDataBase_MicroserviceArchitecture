package com.gamediscovery.GenreMicroService.messaging;

import com.gamediscovery.GenreMicroService.entity.Genre;
import com.gamediscovery.GenreMicroService.external.GameDto;
import com.gamediscovery.GenreMicroService.service.GenreService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameMessageConsumer {

    private final GenreService genreService;

    private final RabbitTemplate rabbitTemplate;


    public GameMessageConsumer(GenreService genreService, RabbitTemplate rabbitTemplate) {
        this.genreService = genreService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "GenreQueue")
    public void consumeGameMessage(GameDto gameDto) {
        Genre genre = gameDto.getGenre();
        Genre newGenre = genreService.createGenre(genre);
        gameDto.setGenre(newGenre);
        if (gameDto.getPublisher() != null)
            rabbitTemplate.convertAndSend("GameExchange", "publisher-routing-key", gameDto);
        else
            rabbitTemplate.convertAndSend("GameExchange", "updated-game-routing-key", gameDto);
    }
}
