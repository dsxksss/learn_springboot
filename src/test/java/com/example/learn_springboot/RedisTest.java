package com.example.learn_springboot;

import com.example.learn_springboot.dao.RedisDao;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class RedisTest {

  @Autowired
  RedisDao redisDao;

  @Test
  @Order(0)
  void testSet() {
    Assert.isTrue(
      redisDao.setKey("testValue", "hello"),
      "test redis set expected!"
    );
  }

  @Test
  @Order(1)
  void testGet() {
    Assert.isTrue(
      redisDao.getValue("testValue").equals("hello"),
      "test redis get expected!"
    );
  }

  @Test
  @Order(2)
  void testDelete() {
    Assert.isTrue(
      redisDao.deleteValue("testValue"),
      "test redis delete expected!"
    );
  }
}
