package com.gamediscovery.gamemicroservice.external;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Genre {

    private Long id;

    private String name;

    private String background_image;
}
