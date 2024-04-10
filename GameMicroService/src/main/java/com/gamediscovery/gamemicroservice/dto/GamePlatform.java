package com.gamediscovery.gamemicroservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamePlatform {

    private Long id;

    private Long gameId;

    private Long platformId;
}
