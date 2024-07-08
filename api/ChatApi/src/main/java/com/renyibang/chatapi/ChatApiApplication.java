package com.renyibang.chatapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ChatApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChatApiApplication.class, args);
  }
}
