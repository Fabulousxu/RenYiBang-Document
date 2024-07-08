package com.example.tester;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping()
    public void test(long userId) {
        //方法一： restTemplate 方法，写死端口号的URL
//        System.out.println("test");
//        String url = "http://localhost:8080/user/getUserById?userId=" + userId;
//        JSONObject result = restTemplate.getForObject(url, JSONObject.class);
//        System.out.println(result);

        //方法二： 注册中心Nacos
    }
}
