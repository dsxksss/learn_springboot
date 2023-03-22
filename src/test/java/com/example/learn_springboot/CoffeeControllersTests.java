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
  private static final String BASE_URL = "/coffees";
  private static final String NAME = "拿铁咖啡";
  private static final double PRICE = 15.00;
  private static final int QUANTITY = 15;
  private static final String IMAGE = "拿铁咖啡.jpg";
  private static final String DESCRIPTION =
    "拿铁咖啡以热牛奶和浓缩咖啡调制而成，口感香浓，醇厚";
  private static final String JSON_CONTENT_TYPE =
    MediaType.APPLICATION_JSON_VALUE;

  @Test
  @Order(1)
  void testAddCoffee() throws Exception {
    // 先确认空内容是否可以被获取
    mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL))
      .andExpect(MockMvcResultMatchers.status().isNotFound());

    // 构造测试数据
    JSONObject testData1 = new JSONObject();
    testData1.put("name", NAME);
    testData1.put("price", PRICE);
    testData1.put("quantity", QUANTITY);
    testData1.put("image", IMAGE);
    testData1.put("description", DESCRIPTION);

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post(BASE_URL)
          .content(testData1.toString())
          .contentType(JSON_CONTENT_TYPE)
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(JSON_CONTENT_TYPE))
      .andExpectAll(
        MockMvcResultMatchers.jsonPath("name").value(NAME),
        MockMvcResultMatchers.jsonPath("price").value(PRICE),
        MockMvcResultMatchers.jsonPath("quantity").value(QUANTITY),
        MockMvcResultMatchers.jsonPath("image").value(IMAGE),
        MockMvcResultMatchers.jsonPath("description").value(DESCRIPTION)
      );

    // 发送 POST 请求，传入空数据，验证返回结果是否符合预期
    JSONObject testData2 = new JSONObject();

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post(BASE_URL)
          .content(testData2.toString())
          .contentType(JSON_CONTENT_TYPE)
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
      .andExpect(MockMvcResultMatchers.content().contentType(JSON_CONTENT_TYPE))
      .andExpectAll(
        MockMvcResultMatchers.jsonPath("$[0].name").value(NAME),
        MockMvcResultMatchers.jsonPath("$[0].price").value(PRICE),
        MockMvcResultMatchers.jsonPath("$[0].quantity").value(QUANTITY),
        MockMvcResultMatchers.jsonPath("$[0].image").value(IMAGE),
        MockMvcResultMatchers.jsonPath("$[0].description").value(DESCRIPTION)
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
      .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + TEST_ID))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(JSON_CONTENT_TYPE))
      .andExpectAll(
        MockMvcResultMatchers.jsonPath("$.name").value(NAME),
        MockMvcResultMatchers.jsonPath("$.price").value(PRICE),
        MockMvcResultMatchers.jsonPath("$.quantity").value(QUANTITY),
        MockMvcResultMatchers.jsonPath("$.image").value(IMAGE),
        MockMvcResultMatchers.jsonPath("$.description").value(DESCRIPTION)
      );

    // 测试非法id的操作是否符合预期
    mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + UUID.randomUUID()))
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
    testData.put("id", TEST_ID);
    testData.put("name", "test");
    testData.put("price", 15.00);
    testData.put("quantity", 15);
    testData.put("image", "拿铁咖啡.jpg");
    testData.put("description", "热牛奶和浓缩咖啡调制而成");

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .put(BASE_URL)
          .content(testData.toString())
          .contentType(JSON_CONTENT_TYPE)
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType(JSON_CONTENT_TYPE))
      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"));

    // 测试非法id的操作是否符合预期
    testData.put("id", UUID.randomUUID());
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .put(BASE_URL)
          .content(testData.toString())
          .contentType(JSON_CONTENT_TYPE)
      )
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @Order(4)
  void testDeleteCoffee() throws Exception {
    mockMvc
      .perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + TEST_ID))
      .andExpect(MockMvcResultMatchers.status().isOk());

    mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + TEST_ID))
      .andExpect(MockMvcResultMatchers.status().isNotFound());

    // 测试非法id的操作是否符合预期
    mockMvc
      .perform(
        MockMvcRequestBuilders.delete(BASE_URL + "/" + UUID.randomUUID())
      )
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
