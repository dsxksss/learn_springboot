package com.example.learn_springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/*
  * @Api(tags = "我是某个Controller类的名称")
  * @ApiOperation("我是某个api的名称")
  * @ApiParam("我是某个api里的形参变量描述")
  * @ApiResponses 用作于描述该api的返回内容
    @ApiResponses(
      {@ApiResponse(code = 200, message = "成功"},
      {@ApiResponse(code = 400, message = "失败"}
    )
  * @ApiIgnore 用作于忽略某个api请求的映射
  * @ApiModel(description = "某个entity类的描述")
  * @ApiModelProperty("某个entity类的数据模型描述")  
*/

// 访问下方URL 进入api文档
// http://localhost:3001/swagger-ui/index.html
@Configuration
public class SwaggerConfig {

  @Value("${spring.profiles.active:NA}")
    private String active;

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .contact(
        new Contact("VentCat", "https://ventroar.xyz", "2546650292@qq.com")
      )
      .title("LearnSpringBoot - 在线API接口文档")
      .description("一些描述...")
      .version("0.1")
      .build();
  }

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.OAS_30)
      // api文档简介
      .enable("dev".equals(active))  // 仅在开发环境开启Swagger
      .apiInfo(apiInfo())
      // 限制只有哪些api才会被选择并生成文档
      .select()
      // 只扫描本项目里的api、忽略掉springboot自带的api
      .apis(RequestHandlerSelectors.basePackage("com.example.learn_springboot"))
      .build();
  }
}
