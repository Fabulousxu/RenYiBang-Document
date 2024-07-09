package com.example.consumer.ProducerClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "producer")
public interface ProducerClient {
    @GetMapping("/hello")
    String getHello(@RequestParam("name") String name);
}
