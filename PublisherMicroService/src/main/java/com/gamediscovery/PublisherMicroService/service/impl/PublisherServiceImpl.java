package com.gamediscovery.PublisherMicroService.service.impl;

import com.gamediscovery.PublisherMicroService.client.GameClient;
import com.gamediscovery.PublisherMicroService.dto.PublisherResponse;
import com.gamediscovery.PublisherMicroService.entity.Publisher;
import com.gamediscovery.PublisherMicroService.exception.PublisherNotFoundException;
import com.gamediscovery.PublisherMicroService.repository.PublisherRepository;
import com.gamediscovery.PublisherMicroService.service.PublisherService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    private final GameClient gameClient;

    public PublisherServiceImpl(PublisherRepository publisherRepository, GameClient gameClient) {
        this.publisherRepository = publisherRepository;
        this.gameClient = gameClient;
    }

    @Override
    public Publisher createPublisher(Publisher publisher) {
        Optional<Publisher> existingPublisher = publisherRepository.findByName(publisher.getName());
        return existingPublisher.orElseGet(() -> publisherRepository.save(publisher));
    }


    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public PublisherResponse getPublisherById(Long publisherId) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisherId);
        Publisher publisher = optionalPublisher.orElseThrow(() ->new PublisherNotFoundException(publisherId));
        PublisherResponse publisherResponse = new PublisherResponse();
        publisherResponse.setPublisher(publisher);
        publisherResponse.setGameCount(gameClient.getGamesByPublisherId(publisherId).getGameCount());
        return publisherResponse;
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public Publisher getPublisherByIdForGame(Long publisherId) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisherId);
        return optionalPublisher.orElseThrow(() ->new PublisherNotFoundException(publisherId));
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public Publisher getPublisherByNameForGame(String publisherName) {
        Optional<Publisher> optionalPublisher = publisherRepository.findByName(publisherName);
        return optionalPublisher.orElseThrow(() ->new PublisherNotFoundException(publisherName));
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public PublisherResponse getPublisherByName(String publisherName) {
        Optional<Publisher> optionalPublisher = publisherRepository.findByName(publisherName);
        Publisher publisher = optionalPublisher.orElseThrow(() ->new PublisherNotFoundException(publisherName));
        PublisherResponse publisherResponse = new PublisherResponse();
        publisherResponse.setPublisher(publisher);
        publisherResponse.setGameCount(gameClient.getGamesByPublisherId(publisher.getId()).getGameCount());
        return publisherResponse;
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public List<PublisherResponse> getListOfPublishers() {
        List<Publisher> publishers = publisherRepository.findAll();
        List<PublisherResponse> publisherResponses = new ArrayList<>();
        for (Publisher publisher : publishers) {
            PublisherResponse publisherResponse = new PublisherResponse();
            publisherResponse.setPublisher(publisher);
            publisherResponse.setGameCount(gameClient.getGamesByPublisherId(publisher.getId()).getGameCount());
            publisherResponses.add(publisherResponse);
        }
        return publisherResponses;
    }

}
