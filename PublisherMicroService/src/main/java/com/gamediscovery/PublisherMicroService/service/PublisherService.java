package com.gamediscovery.PublisherMicroService.service;

import com.gamediscovery.PublisherMicroService.dto.PublisherResponse;
import com.gamediscovery.PublisherMicroService.entity.Publisher;

import java.util.List;

public interface PublisherService {
    Publisher createPublisher(Publisher publisher);

    PublisherResponse getPublisherById(Long publisherId);

    PublisherResponse getPublisherByName(String publisherName);

    List<PublisherResponse> getListOfPublishers();

    Publisher getPublisherByIdForGame(Long publisherId);

    Publisher getPublisherByNameForGame(String publisherName);
}
