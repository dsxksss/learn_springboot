package com.example.learn_springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.learn_springboot.mappers")
public class LearnSpringbootApplication {

  // TODO 待创建init类处理自动生成数据表模板工作

  public static void main(String[] args) {
    SpringApplication.run(LearnSpringbootApplication.class, args);
  }
}
