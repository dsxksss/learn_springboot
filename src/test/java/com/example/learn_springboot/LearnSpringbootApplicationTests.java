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
		coffeeMapper.insert(new Coffee(1, "Apple iPad Pro", 899.99, 100, "images/ipadpro.jpg", "The new Apple iPad Pro with 12.9-inch Retina display", 1587882545));
		coffeeMapper.insert(new Coffee(2, "Samsung Galaxy S20", 699.99, 200, "images/s20.jpg", "The new Samsung Galaxy S20 with 6.2-inch Infinity-O Display", 1588067345));
		coffeeMapper.insert(new Coffee(3, "Xiaomi Mi Note 10", 399.99, 300, "images/minote10.jpg", "The new Xiaomi Mi Note 10 with 6.47-inch AMOLED display", 1588251745));
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
