package com.gamediscovery.gamemicroservice.external;

import com.gamediscovery.gamemicroservice.entity.Game;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Platform {

    private Long id;

    private String name;

}
