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

package com.example.consumer.demos.web;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Controller
public class BasicController {
    private final DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate;

    public BasicController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hello")
    public void helloUser(String name) {
        List<ServiceInstance> list = discoveryClient.getInstances("producer");
        if(list == null)
        {
            System.out.println("No producer found");
        }

        //负载均衡算法
        ServiceInstance serviceInstance = list.get(0);

        System.out.println(serviceInstance.getUri());

        String result = restTemplate.getForObject(serviceInstance.getUri() + "/hello?name=" + name, String.class);

        System.out.println(result);
    }
}
