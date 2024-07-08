package com.example.consumer.demos.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "producer")
public interface feignTest {
    @GetMapping("/hello")
    String getHello(@RequestParam("name") String name);
}
