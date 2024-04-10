package com.gamediscovery.PublisherMicroService.controller;

import com.gamediscovery.PublisherMicroService.dto.PublisherResponse;
import com.gamediscovery.PublisherMicroService.entity.Publisher;
import com.gamediscovery.PublisherMicroService.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherService publisherService;


    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher){
        return new ResponseEntity<>(publisherService.createPublisher(publisher), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponse> getPublisherById(@PathVariable(name = "id") Long publisherId){
        return new ResponseEntity<>(publisherService.getPublisherById(publisherId), HttpStatus.OK);
    }

    @GetMapping(params = "publisherName")
    public ResponseEntity<Publisher> getPublisherByName(@RequestParam(name = "publisherName") String publisherName){
        return new ResponseEntity<>(publisherService.getPublisherByName(publisherName), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PublisherResponse>> getListOfPublishers(){
        return new ResponseEntity<>(publisherService.getListOfPublishers(), HttpStatus.OK);
    }
}
