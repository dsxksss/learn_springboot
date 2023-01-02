package com.example.learn_springboot;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
// 这里使用了按顺序执行
// 下列方法中的@Order注解表示执行的顺序
// 用于配置方法的执行顺序，数字越低执行顺序越高
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoffeeMapperTests {}
