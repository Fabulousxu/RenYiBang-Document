package com.example.consumer.ConsumerController;

/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.consumer.ProducerClient.ProducerClient;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Controller
@EnableDiscoveryClient
public class ConsumerController {
//    private final DiscoveryClient discoveryClient;
//
//    private final RestTemplate restTemplate;

    private final ProducerClient producerClient;

    public ConsumerController(ProducerClient producerClient) {
        this.producerClient = producerClient;
    }

    @GetMapping("/hello")
    public void helloUser(String name) {
        String result = producerClient.getHello(name);

        System.out.println(result);
    }
}
