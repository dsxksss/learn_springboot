package com.example.learn_springboot.service;

import com.example.learn_springboot.entitys.Coffee;
import com.example.learn_springboot.repositorys.CoffeeRep;
import java.util.Date;
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
    // 起始页减一，符合后续逻辑操作
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
    // 如果没找到的话 则返回404状态码
    if (!coffeeRep.existsById(coffeeID)) throw new ResponseStatusException(
      HttpStatus.NOT_FOUND
    );

    // 找到则返回
    return coffeeRep.findById(coffeeID).get();
  }

  /**
   * 添加单个咖啡信息
   * @param coffee
   * @return
   */
  public Coffee addCoffee(Coffee coffee) {
    // 创建数据副本
    Coffee newCoffee = coffee;

    // 生成ID
    newCoffee.setId(UUID.randomUUID().toString());

    // 生成13位时间戳 (13位时间戳是精确到毫秒)
    // 如果想生成10位时间戳的话 (10位时间戳是精确到秒) 在13位基础上除以1000即可
    newCoffee.setCreateTime(new Date().getTime());

    // 保存加工后的数据至数据库
    coffeeRep.save(newCoffee);
    return newCoffee;
  }

  /**
   * 更新咖啡字段
   * @param coffee
   * @return
   */
  public Coffee updateCoffee(Coffee coffee) {
    // 创建数据副本
    Coffee newCoffee = coffee;

    // 查找是否存在该信息,如果不存在则返回404状态码
    if (
      !coffeeRep.existsById(newCoffee.getId())
    ) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    // 获取该ID对应的旧数据
    Coffee oldCoffee = coffeeRep.findById(newCoffee.getId()).get();

    // 将老数据中的创建时间同步，并且保存新咖啡字段至数据库
    newCoffee.setId(oldCoffee.getId());
    newCoffee.setCreateTime(oldCoffee.getCreateTime());
    coffeeRep.save(newCoffee);

    return newCoffee;
  }

  public Coffee deleteCoffee(String deleteID) {
    // 如果没找到的话 则返回404状态码
    if (!coffeeRep.existsById(deleteID)) throw new ResponseStatusException(
      HttpStatus.NOT_FOUND
    );

    // 获取即将删除的咖啡数据用于返回给客户端
    Coffee deletCoffee = coffeeRep.findById(deleteID).get();

    // 删除数据库内对应的咖啡数据
    coffeeRep.deleteById(deleteID);

    return deletCoffee;
  }
}
