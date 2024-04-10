package com.gamediscovery.gamemicroservice.clients;

import com.gamediscovery.gamemicroservice.external.Publisher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "publisher-microservice", url = "localhost:8084")
public interface PublisherClient {

    @GetMapping(value = "/publishers/{publisherId}")
    Publisher getPublisherById(@PathVariable Long publisherId);

    @GetMapping(value = "/publishers", params = "publisherName")
    Publisher getPublisherByName(@RequestParam String publisherName);
}
