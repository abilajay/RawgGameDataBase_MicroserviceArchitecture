package com.gamediscovery.PublisherMicroService.messaging;

import com.gamediscovery.PublisherMicroService.entity.Publisher;
import com.gamediscovery.PublisherMicroService.external.GameDto;
import com.gamediscovery.PublisherMicroService.service.PublisherService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameMessageConsumer {
    private final PublisherService publisherService;

    private final RabbitTemplate rabbitTemplate;

    public GameMessageConsumer(PublisherService publisherService, RabbitTemplate rabbitTemplate) {
        this.publisherService = publisherService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "PublisherQueue")
    public void consumeGameMessage(GameDto gameDto){
        Publisher publisher = publisherService.createPublisher(gameDto.getPublisher());
        gameDto.setPublisher(publisher);
        rabbitTemplate.convertAndSend("GameExchange", "updated-game-routing-key", gameDto);
    }
}
