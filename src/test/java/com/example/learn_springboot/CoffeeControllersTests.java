package com.example.learn_springboot;

import javax.annotation.Resource;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(
  // 和SpringBootTest说明该类是用于测试web应用的单元测试类
  classes = { LearnSpringbootApplication.class },
  // 在模拟每一次测试的时候，测试对象采用的端口都是随机的，符合实际情况下的不同电脑访问和避免端口冲突占用
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
// 自动配置并启用MockMvc
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoffeeControllersTests {

  @Resource
  private MockMvc mockMvc;

  // TODO 待补充其他测试

  @Test
  @Order(1)
  @DisplayName("测试添加咖啡数据")
  void testGetCoffees() throws Exception {
    JSONObject testData = new JSONObject();
    testData.put("name", "拿铁咖啡");
    testData.put("price", 15.00);
    testData.put("quantity", 15);
    testData.put("image", "拿铁咖啡.jpg");
    testData.put(
      "description",
      "拿铁咖啡以热牛奶和浓缩咖啡调制而成，口感香浓，醇厚"
    );

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/coffees")
          .content(testData.toString())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(
        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
      )
      .andExpectAll(
        MockMvcResultMatchers.jsonPath("name").value("拿铁咖啡"),
        MockMvcResultMatchers.jsonPath("price").value(15.00),
        MockMvcResultMatchers.jsonPath("quantity").value(15),
        MockMvcResultMatchers.jsonPath("image").value("拿铁咖啡.jpg"),
        MockMvcResultMatchers
          .jsonPath("description")
          .value("拿铁咖啡以热牛奶和浓缩咖啡调制而成，口感香浓，醇厚")
      )
      .andReturn();
  }
}
