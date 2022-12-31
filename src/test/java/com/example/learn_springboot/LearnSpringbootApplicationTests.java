package com.example.learn_springboot;

import com.example.learn_springboot.entitys.Coffee;
import com.example.learn_springboot.mappers.CoffeeMapper;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/*
 * @SpringBootTest	用于指定测试类启用Spring Boot Test，默认会提供Mock环境
 *  @ExtendWith	如果只想启用Spring环境进行简单测试，不想启用Spring Boot环境，可以配置扩展为：SpringExtension
 *  @Test	指定方法为测试方法
 *  @TestMethodOrder	用于配置测试类中方法的执行顺序策略，配置为OrderAnnotation时，按@Order顺序执行
 *  @Order	用于配置方法的执行顺序，数字越低执行顺序越高
 *  @DisplayName	用于指定测试类和测试方法的别名
 *  @BeforeAll	在测试类的所有测试方法前执行一次，可用于全局初始化
 *  @AfterAll	在测试类的所有测试方法后执行一次，可用于全局销毁资源
 *  @BeforeEach	在测试类的每个测试方法前都执行一次
 *  @AfterEach	在测试类的每个测试方法后都执行一次
 *  @Disabled	禁用测试方法
 *  @RepeatedTest	指定测试方法重复执行
 *  @ParameterizedTest	指定参数化测试方法，类似重复执行，从@ValueSource中获取参数
 *  @ValueSource	用于参数化测试指定参数
 *  @AutoConfigureMockMvc	启用MockMvc的自动配置，可用于测试接口
 */

// ! 注意 spring test 是同步执行的
@SpringBootTest
// 这里使用了按顺序执行
// 下列方法中的@Order注解表示执行的顺序
// 用于配置方法的执行顺序，数字越低执行顺序越高
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LearnSpringbootApplicationTests {

  @Autowired
  // TODO 待分层测试
  private CoffeeMapper coffeeMapper;

  @Test
  @Order(1)
  void testInsert() throws Exception {
    coffeeMapper.insert(
      new Coffee(
        "1",
        "咖啡",
        10.00,
        10,
        "coffee.jpg",
        "精选阿拉比卡咖啡豆，口感醇厚，滋味浓郁",
        1588503568
      )
    );
    coffeeMapper.insert(
      new Coffee(
        "2",
        "拿铁咖啡",
        15.00,
        15,
        "latte.jpg",
        "拿铁咖啡以热牛奶和浓缩咖啡调制而成，口感香浓，醇厚",
        1588503568
      )
    );
    coffeeMapper.insert(
      new Coffee(
        "3",
        "卡布奇诺",
        20.00,
        20,
        "cappuccino.jpg",
        "卡布奇诺以浓缩咖啡、牛奶和奶泡调制而成，口感醇厚，香浓",
        1588503568
      )
    );
    Assert.isTrue(3 == coffeeMapper.getAll().size(), "testInsert error !");
  }

  @Test
  @Order(2)
  void testQuery() throws Exception {
    System.out.println(coffeeMapper.getAll());
    Assert.isTrue(coffeeMapper.getAll().size() >= 0, "testQuery error !");
  }

  @Test
  @Order(3)
  void testUpdate() throws Exception {
    Coffee coffee = coffeeMapper.getOne("3");
    System.out.println(coffee);
    coffee.setName("test test test !!!");
    coffeeMapper.update(coffee);
    Assert.isTrue(
      coffee.getName().equals(coffeeMapper.getOne("3").getName()),
      "testUpdate error !"
    );
  }

  @Test
  @Order(4)
  void testDelete() throws Exception {
    coffeeMapper.delete("1");
    coffeeMapper.delete("2");
    coffeeMapper.delete("3");

    Assert.isTrue(coffeeMapper.getAll().size() == 0, "testDelete error !");
  }

  @Test
  void testDeleteAll() throws Exception {
    coffeeMapper.insert(
      new Coffee(
        UUID.randomUUID().toString(),
        "咖啡",
        10.00,
        10,
        "coffee.jpg",
        "精选阿拉比卡咖啡豆，口感醇厚，滋味浓郁",
        1588503568
      )
    );
    coffeeMapper.insert(
      new Coffee(
        UUID.randomUUID().toString(),
        "拿铁咖啡",
        15.00,
        15,
        "latte.jpg",
        "拿铁咖啡以热牛奶和浓缩咖啡调制而成，口感香浓，醇厚",
        1588503568
      )
    );
    coffeeMapper.insert(
      new Coffee(
        UUID.randomUUID().toString(),
        "卡布奇诺",
        20.00,
        20,
        "cappuccino.jpg",
        "卡布奇诺以浓缩咖啡、牛奶和奶泡调制而成，口感醇厚，香浓",
        1588503568
      )
    );

    coffeeMapper.deleteAll();

    Assert.isTrue(coffeeMapper.getAll().size() == 0, "testDeleteAll error!");
  }
}
