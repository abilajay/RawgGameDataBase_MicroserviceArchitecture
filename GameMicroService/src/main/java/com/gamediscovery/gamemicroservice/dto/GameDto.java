package com.gamediscovery.gamemicroservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamediscovery.gamemicroservice.entity.Screenshot;
import com.gamediscovery.gamemicroservice.entity.Trailer;
import com.gamediscovery.gamemicroservice.external.Genre;
import com.gamediscovery.gamemicroservice.external.Platform;
import com.gamediscovery.gamemicroservice.external.Publisher;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameDto {

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

    private Genre genre;

    private Publisher publisher;

    private List<Platform> platforms;
}
