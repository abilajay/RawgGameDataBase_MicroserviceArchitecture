package com.gamediscovery.game_platform.messaging;

import com.gamediscovery.game_platform.clients.PlatformClient;
import com.gamediscovery.game_platform.entity.GamePlatform;
import com.gamediscovery.game_platform.entity.Platform;
import com.gamediscovery.game_platform.external.GameDto;
import com.gamediscovery.game_platform.service.GamePlatformService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameMessageConsumer {
    private final PlatformClient platformClient;

    private final GamePlatformService gamePlatformService;

    public GameMessageConsumer(PlatformClient platformClient, GamePlatformService gamePlatformService) {
        this.platformClient = platformClient;
        this.gamePlatformService = gamePlatformService;
    }

    @RabbitListener(queues = "PlatformQueue")
    public void consumeGameMessage(GameDto gameDto){
        List<Platform> platforms = platformClient.createMultiplePlatform(gameDto.getPlatforms());
        for (Platform platform: platforms){
            // Check if the record already exists
            System.out.println("GameID:"+gameDto.getId()+"PlatformID:"+ platform.getId()+"Exist:"+gamePlatformService.exists(gameDto.getId(), platform.getId()));
            if (!gamePlatformService.exists(gameDto.getId(), platform.getId())) {
                GamePlatform gamePlatform = new GamePlatform();
                gamePlatform.setGameId(gameDto.getId());
                gamePlatform.setPlatformId(platform.getId());
                gamePlatformService.createRecord(gamePlatform);
            }
        }
    }
}
