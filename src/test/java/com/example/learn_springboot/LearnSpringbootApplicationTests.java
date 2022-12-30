package com.example.learn_springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.learn_springboot.entitys.Coffee;
import com.example.learn_springboot.mappers.CoffeeMapper;

// ! 注意 spring test 是同步执行的
@SpringBootTest
class LearnSpringbootApplicationTests {
	
	@Autowired
	// TODO 待分层测试、同步测试
	private CoffeeMapper coffeeMapper;
	
	@Test
	void testInsert() throws Exception {
		coffeeMapper.insert(new Coffee(1, "咖啡", 10.00, 10, "coffee.jpg", "精选阿拉比卡咖啡豆，口感醇厚，滋味浓郁", 1588503568));
		coffeeMapper.insert(new Coffee(2, "拿铁咖啡", 15.00, 15, "latte.jpg", "拿铁咖啡以热牛奶和浓缩咖啡调制而成，口感香浓，醇厚", 1588503568));
		coffeeMapper.insert(new Coffee(3, "卡布奇诺", 20.00, 20, "cappuccino.jpg", "卡布奇诺以浓缩咖啡、牛奶和奶泡调制而成，口感醇厚，香浓", 1588503568));
		Assert.isTrue(3==coffeeMapper.getAll().size(), "testInsert error !");
	}
	
	@Test
	void testQuery() throws Exception {
		System.out.println(coffeeMapper.getAll());
		Assert.isTrue(coffeeMapper.getAll().size() >= 0, "testQuery error !");
	}

	@Test
	void testUpdate() throws Exception {
		Coffee coffee = coffeeMapper.getOne(3);
		System.out.println(coffee);
		coffee.setName("test test test !!!");
		coffeeMapper.update(coffee);
		Assert.isTrue(coffee.getName().equals( coffeeMapper.getOne(3).getName()), "testUpdate error !");
	}
	
	@Test
	void testDelete() throws Exception {
		coffeeMapper.delete(1);
		coffeeMapper.delete(2);
		coffeeMapper.delete(3);
		
		Assert.isTrue(0==coffeeMapper.getAll().size(), "testDelete error !");
	}
	
}
