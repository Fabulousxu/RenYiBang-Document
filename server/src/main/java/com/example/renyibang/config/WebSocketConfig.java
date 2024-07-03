package com.example.renyibang.config;

import com.example.renyibang.component.ChatWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
  @Autowired private ChatWebSocketHandler chatWebSocketHandler;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(chatWebSocketHandler, "/chat").setAllowedOrigins("*");
  }
}
