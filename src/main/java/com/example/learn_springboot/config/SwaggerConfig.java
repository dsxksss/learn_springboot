package com.example.learn_springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.OAS_30)
      .apiInfo(
        new ApiInfoBuilder()
          .contact(
            new Contact("VentCat", "https://ventroar.xyz", "2546650292@qq.com")
          )
          .title("LearnSpringBoot - 在线API接口文档")
          .description("一些描述...")
          .version("0.1")
          .build()
      );
  }
}
