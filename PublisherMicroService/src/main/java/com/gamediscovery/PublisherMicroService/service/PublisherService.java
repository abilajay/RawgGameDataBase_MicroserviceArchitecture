package com.gamediscovery.PublisherMicroService.service;

import com.gamediscovery.PublisherMicroService.dto.PublisherResponse;
import com.gamediscovery.PublisherMicroService.entity.Publisher;

import java.util.List;

public interface PublisherService {
    Publisher createPublisher(Publisher publisher);

    PublisherResponse getPublisherById(Long publisherId);

    Publisher getPublisherByName(String publisherName);

    List<PublisherResponse> getListOfPublishers();
}
