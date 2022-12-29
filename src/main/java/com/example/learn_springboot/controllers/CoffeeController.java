package com.example.learn_springboot.controllers;

import com.example.learn_springboot.entitys.Coffee;
import com.example.learn_springboot.mappers.CoffeeMapper;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// 路由根路径
@RequestMapping("/")
public class CoffeeController {

  @Autowired
  private CoffeeMapper coffeeMapper;

  @GetMapping
  public List<Coffee> getAllCoffees() {
    List<Coffee> coffeeList = coffeeMapper.getAll();
    return coffeeList;
  }
}
