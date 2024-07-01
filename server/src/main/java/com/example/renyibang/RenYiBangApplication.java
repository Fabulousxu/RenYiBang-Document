package com.example.renyibang;

import jakarta.annotation.PostConstruct;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RenYiBangApplication {

  @Autowired
  private SecurityManager securityManager;

  public static void main(String[] args) {
    SpringApplication.run(RenYiBangApplication.class, args);
  }

  @PostConstruct
  public void init() {
    SecurityUtils.setSecurityManager(securityManager);
  }

}
