package com.gamediscovery.game_platform.external;

import com.gamediscovery.game_platform.entity.Platform;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class GameDto {

    private Long id;

    private List<Platform> platforms;
}
