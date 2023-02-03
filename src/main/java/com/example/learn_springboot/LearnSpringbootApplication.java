package com.example.learn_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableCaching // 开启缓存
@EnableOpenApi // 开启swagger api文档
public class LearnSpringbootApplication {
  public static void main(String[] args) {
    SpringApplication.run(LearnSpringbootApplication.class, args);
  }
}
