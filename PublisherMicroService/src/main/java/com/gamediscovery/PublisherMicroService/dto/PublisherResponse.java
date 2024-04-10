package com.gamediscovery.PublisherMicroService.dto;

import com.gamediscovery.PublisherMicroService.entity.Publisher;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PublisherResponse {
    private Publisher publisher;
    private Long gameCount;
}
