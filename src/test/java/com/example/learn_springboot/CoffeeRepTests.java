package com.example.learn_springboot;

import com.example.learn_springboot.entitys.Coffee;
import com.example.learn_springboot.repositorys.CoffeeRep;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@SpringBootTest
// 这里使用了按顺序执行
// 下列方法中的@Order注解表示执行的顺序
// 用于配置方法的执行顺序，数字越低执行顺序越高
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoffeeRepTests {

  @Resource
  private CoffeeRep coffeeRep;

  @Test
  @Order(1)
  void testInsert() throws Exception {
    coffeeRep.save(
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
    coffeeRep.save(
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
    coffeeRep.save(
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
    Assert.isTrue(coffeeRep.findAll().size() == 3, "insertTest expected!");
  }

  @Test
  @Order(2)
  void testQuery() throws Exception {
    Assert.isTrue(
      !coffeeRep.findById("1").isEmpty(),
      "test Query Single expected!"
    );
    Assert.isTrue(coffeeRep.findAll().size() > 0, "test Query All expected!");
  }

  @Test
  @Order(3)
  /*
   * 如果一个类或者一个类中的 public 方法上被标注@Transactional 注解的话，Spring 容器就会在启动的时候为其创建一个代理类，
   * 在调用被@Transactional 注解的 public 方法的时候，实际调用的是，TransactionInterceptor 类中的 invoke()方法。
   * 这个方法的作用就是在目标方法之前开启事务，方法执行过程中如果遇到异常的时候回滚事务，方法调用完成之后提交事务。
   */
  @Transactional
  void testUpdate() throws Exception {
    Coffee oldCoffee = coffeeRep.findById("3").get();
    oldCoffee.setName("test update ! ! !");
    // save()方法在不指定主键的情况下，即实体对象的主键为null时，执行的是insert操作，
    // 而在指定主键的情况下，执行的操作为update操作

    System.out.println(oldCoffee);
    coffeeRep.save(oldCoffee);
    Coffee newCoffee = coffeeRep.findById("3").get();
    Assert.isTrue(
      newCoffee.getName().equals(oldCoffee.getName()),
      "testUpdate expected!"
    );
  }

  @Test
  @Order(4)
  void testDelete() throws Exception {
    coffeeRep.deleteById("1");
    Assert.isTrue(
      coffeeRep.findAll().size() == 2,
      "test Delete Single expected!"
    );

    coffeeRep.deleteAll();
    Assert.isTrue(coffeeRep.findAll().size() == 0, "test Delete All expected!");
  }

  @Test
  void testInsertAll() throws Exception {
    List<Coffee> coffeeList = List.of(
      new Coffee(
        UUID.randomUUID().toString(),
        "咖啡",
        10.00,
        10,
        "coffee.jpg",
        "精选阿拉比卡咖啡豆，口感醇厚，滋味浓郁",
        1588503568
      ),
      new Coffee(
        UUID.randomUUID().toString(),
        "拿铁咖啡",
        15.00,
        15,
        "latte.jpg",
        "拿铁咖啡以热牛奶和浓缩咖啡调制而成，口感香浓，醇厚",
        1588503568
      ),
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

    coffeeRep.saveAll(coffeeList);
    Assert.isTrue(coffeeRep.findAll().size() == 3, "test Insert All expected!");
  }
}
