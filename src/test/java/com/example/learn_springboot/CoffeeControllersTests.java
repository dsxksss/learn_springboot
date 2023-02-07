package com.example.learn_springboot;

import com.alibaba.fastjson.JSON;
import java.util.UUID;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

  private static String TEST_ID = null;
  private static String BASE_URL = "/coffees";


  @Test
  @Order(1)
  void testAddCoffees() throws Exception {
    // 先确认空内容是否可以被获取
    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
    .andExpect(MockMvcResultMatchers.status().isNotFound());

    JSONObject testData1 = new JSONObject();
    testData1.put("name", "拿铁咖啡");
    testData1.put("price", 15.00);
    testData1.put("quantity", 15);
    testData1.put("image", "拿铁咖啡.jpg");
    testData1.put(
      "description",
      "拿铁咖啡以热牛奶和浓缩咖啡调制而成，口感香浓，醇厚"
    );

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post(BASE_URL)
          .content(testData1.toString())
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
      );

    JSONObject testData2 = new JSONObject();

    // 测试传入空数据后是否符合预期
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post(BASE_URL)
          .content(testData2.toString())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @Order(2)
  void testGetCoffees() throws Exception {
    // getCoffee
    MvcResult result = mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL).param("startPage", "1"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(
        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
      )
      .andExpectAll(
        MockMvcResultMatchers.jsonPath("$[0].name").value("拿铁咖啡"),
        MockMvcResultMatchers.jsonPath("$[0].price").value(15.00),
        MockMvcResultMatchers.jsonPath("$[0].quantity").value(15),
        MockMvcResultMatchers.jsonPath("$[0].image").value("拿铁咖啡.jpg"),
        MockMvcResultMatchers
          .jsonPath("$[0].description")
          .value("拿铁咖啡以热牛奶和浓缩咖啡调制而成，口感香浓，醇厚")
      )
      .andReturn();

    com.alibaba.fastjson.JSONArray dataArray = JSON.parseArray(
      result.getResponse().getContentAsString()
    );
    com.alibaba.fastjson.JSONObject dataObject = dataArray.getJSONObject(0);
    // 提供测试id方便后续测试
    TEST_ID = dataObject.getString("id");

    // getCoffee
    mockMvc
      .perform(MockMvcRequestBuilders.get("/coffees/" + TEST_ID))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(
        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
      )
      .andExpectAll(
        MockMvcResultMatchers.jsonPath("$.name").value("拿铁咖啡"),
        MockMvcResultMatchers.jsonPath("$.price").value(15.00),
        MockMvcResultMatchers.jsonPath("$.quantity").value(15),
        MockMvcResultMatchers.jsonPath("$.image").value("拿铁咖啡.jpg"),
        MockMvcResultMatchers
          .jsonPath("$.description")
          .value("拿铁咖啡以热牛奶和浓缩咖啡调制而成，口感香浓，醇厚")
      );

    // 测试非法id的操作是否符合预期
    mockMvc
      .perform(MockMvcRequestBuilders.get("/coffees/" + UUID.randomUUID()))
      .andExpect(MockMvcResultMatchers.status().isNotFound());

    // 测试获取超过已有页数的报错是否符合预期
    mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL).param("startPage", "2"))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @Order(3)
  void testUpdateCoffee() throws Exception {
    JSONObject testData = new JSONObject();
    testData.put("name", "test");
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
          .put("/coffees/" + TEST_ID)
          .content(testData.toString())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(
        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"));

    // 测试非法id的操作是否符合预期
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .put("/coffees/" + UUID.randomUUID())
          .content(testData.toString())
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @Order(4)
  void testDeleteCoffee() throws Exception {
    mockMvc
      .perform(MockMvcRequestBuilders.delete("/coffees/" + TEST_ID))
      .andExpect(MockMvcResultMatchers.status().isOk());

    mockMvc
      .perform(MockMvcRequestBuilders.get("/coffees/" + TEST_ID))
      .andExpect(MockMvcResultMatchers.status().isNotFound());

    // 测试非法id的操作是否符合预期
    mockMvc
      .perform(MockMvcRequestBuilders.delete("/coffees/" + UUID.randomUUID()))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
