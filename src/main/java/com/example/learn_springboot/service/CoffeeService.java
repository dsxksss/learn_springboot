package com.example.learn_springboot.service;

import com.example.learn_springboot.entitys.Coffee;
import com.example.learn_springboot.repositorys.CoffeeRep;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// TODO redis缓存层
@Service
public class CoffeeService {

  @Autowired
  private CoffeeRep coffeeRep;

  /**
   * 获取多个咖啡信息
   *  TODO 自定义排序查询
   * @param startPage
   * @return
   */
  public List<Coffee> getCoffees(int startPage) {
    // 起始页减一，符合后续计算逻辑
    startPage--;

    // 每页数据上限为10条
    int pageSize = 10;

    // 计算数量，如果不够向上整除
    double pageCount = Math.ceil(coffeeRep.count() / pageSize);

    // 如果查询页数不存在或小于0则返回404
    if (
      startPage < 0 || startPage > pageCount
    ) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    // 如果前置操作无误则开始获取数据库内的数据
    List<Coffee> coffeeList = coffeeRep.getCoffees(
      startPage * pageSize,
      pageSize
    );

    // 如果全部咖啡信息列表里没有一个信息的话则返回404
    if (coffeeList.size() <= 0) throw new ResponseStatusException(
      HttpStatus.NOT_FOUND
    );

    return coffeeList;
  }

  /**
   * 获取单个咖啡信息
   * @param coffeeID
   * @return
   */
  public Coffee getCoffee(String coffeeID) {
    // 如果没找到的话，则返回 404 状态码
    return coffeeRep
      .findById(coffeeID)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  /**
   * 添加单个咖啡信息
   * @param coffee
   * @return
   */
  public Coffee addCoffee(Coffee coffee) {
    // 生成ID
    String id = UUID.randomUUID().toString();
    coffee.setId(id);

    // 生成13位时间戳 (13位时间戳是精确到毫秒)
    // 如果想生成10位时间戳的话 (10位时间戳是精确到秒) 在13位基础上除以1000即可
    long createTime = System.currentTimeMillis();
    coffee.setCreateTime(createTime);

    // 保存加工后的数据至数据库
    coffeeRep.save(coffee);
    return coffee;
  }

  /**
   * 更新咖啡字段
   * @param coffee
   * @return
   */
  public Coffee updateCoffee(Coffee coffee) {
    // 查找是否存在该信息,如果不存在则返回404状态码
    if (!coffeeRep.existsById(coffee.getId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // 获取该ID对应的旧数据并更新字段
    Coffee newCoffee = coffeeRep.findById(coffee.getId()).orElse(null);
    if (newCoffee != null) {
      newCoffee.setName(coffee.getName());
      newCoffee.setPrice(coffee.getPrice());
      coffeeRep.save(newCoffee);
    }

    return newCoffee;
  }

  /**
   * 删除咖啡数据
   * @param deleteID
   * @return
   */
  public Coffee deleteCoffee(String deleteID) {
    // 判断是否存在该咖啡数据，如果不存在，则抛出 404 异常
    Coffee coffeeToDelete = coffeeRep
      .findById(deleteID)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    // 删除数据库内对应的咖啡数据
    coffeeRep.delete(coffeeToDelete);

    return coffeeToDelete;
  }
}
