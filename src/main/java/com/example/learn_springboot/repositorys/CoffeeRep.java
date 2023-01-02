package com.example.learn_springboot.repositorys;

import com.example.learn_springboot.entitys.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

//! JpaRepository有两个泛型 
// 前者对应的时具体操作的对象实体也就是entity类
// 后者时该对象实体类中、字段中对应的id类型
public interface CoffeeRep extends JpaRepository<Coffee, String> {}
