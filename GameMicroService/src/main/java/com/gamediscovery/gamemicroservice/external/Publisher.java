package com.gamediscovery.gamemicroservice.external;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Publisher {

    private Long id;

    private String name;

    private String image_background;

}
