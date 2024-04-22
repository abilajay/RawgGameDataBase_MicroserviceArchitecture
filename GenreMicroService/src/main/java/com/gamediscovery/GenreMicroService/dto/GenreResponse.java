package com.gamediscovery.GenreMicroService.dto;

import com.gamediscovery.GenreMicroService.entity.Genre;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreResponse {

    private Long gameCount;
    private Genre genre;
}
