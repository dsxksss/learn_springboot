package com.example.learn_springboot.controllers;

import com.example.learn_springboot.entitys.Coffee;
import com.example.learn_springboot.mappers.CoffeeMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/* RESTful API的方式来设计的URL请求
  * 获取所有咖啡信息：GET /coffees
  *获取某款咖啡信息：GET /coffees/{id}
  *新增一款咖啡：POST /coffees
  *修改某款咖啡信息：PUT /coffees/{id}
  *删除某款咖啡：DELETE /coffees/{id}
  *购买某款咖啡：POST /coffees/{id}/purchase 
 */

@RestController
// 路由根路径
@RequestMapping("/coffees")
public class CoffeeController {
  
  // TODO 待增加post、put、delete方法
  @Autowired
  private CoffeeMapper coffeeMapper;

  // 获取全部咖啡信息
  // 使用URL例子 : http://localhost:2546/coffees/
  @GetMapping
  public List<Coffee> getCoffees() {
    List<Coffee> coffeeList = coffeeMapper.getAll();
    return coffeeList;
  }

  
  // 当URL指向的是某一具体业务资源（或者资源列表），例如博客，用户时，使用@PathVariable
  // 当URL需要对资源或者资源列表进行过滤，筛选时，用@RequestParam
  
  
  // 通过id获取单个咖啡信息
  // 使用URL例子 : http://localhost:2546/coffees/1
  @GetMapping("/{id}")
  // @PathVariable 会去搜索path上是否有被{}包裹起来的同名的id值
  //! 并且表示该值作用于下方的参数中 , 需注意同名
    public Coffee getCoffee(@PathVariable String id) {
    System.out.println(id);
    Coffee coffee = coffeeMapper.getOne(id);
    // 如果没找到的话 则返回404状态码
    if (coffee == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    // 找到则返回
    return coffee;
  }
}
