package com.gamediscovery.gamemicroservice.clients;

import com.gamediscovery.gamemicroservice.external.Publisher;
import com.gamediscovery.gamemicroservice.external.PublisherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Publisher-Microservice")
public interface PublisherClient {

    @GetMapping(value = "/publishers/{publisherId}")
    ResponseEntity<PublisherResponse> getPublisherById(@PathVariable Long publisherId);

    @GetMapping(value = "/publishers", params = "publisherName")
    ResponseEntity<PublisherResponse> getPublisherByName(@RequestParam String publisherName);

    @GetMapping(value = "/publishers/publisher/{id}")
    Publisher getPublisherByIdForGame(@PathVariable Long id);

    @GetMapping(value = "/publishers/publisher", params = "publisherName")
    ResponseEntity<Publisher> getPublisherByNameForGame(@RequestParam(name = "publisherName") String publisherName);
}
