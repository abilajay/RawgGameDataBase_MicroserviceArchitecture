package com.gamediscovery.gamemicroservice.dto;

import com.gamediscovery.gamemicroservice.entity.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GamesResponse {
    private Long gameCount;

    private List<Game> games;
}
