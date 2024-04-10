package com.gamediscovery.PublisherMicroService.external;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GamesResponse {
    private Long gameCount;

    private List<Game> games;
}
