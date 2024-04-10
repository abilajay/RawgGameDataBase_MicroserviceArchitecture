package com.gamediscovery.GenreMicroService.external;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


@Getter
@Setter
public class Game {

    private Long id;

    private Long uniqueId;

    private String name;

    private Short metaCritic;

    private Integer rating_top;

    private Date released;

    private String background_image;

    private Integer playtime;

    private Set<Screenshot> screenshots;

    private Set<Trailer> trailers;

    private Long genreId;

    private Long publisherId;
}
