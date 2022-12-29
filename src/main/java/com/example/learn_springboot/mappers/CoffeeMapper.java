package com.example.learn_springboot.mappers;

import com.example.learn_springboot.entitys.Coffee;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

// Integer id;// 产品ID
// String name;// 产品名称
// double price;// 产品价格
// Integer quantity;// 售卖数量
// String image;// 产品图片
// String description;// 产品描述
// long createTime;// 创建时间

@Mapper
public interface CoffeeMapper {
  // TODO: 待实现剩下功能
  @Select("SELECT * FROM coffees")
  List<Coffee> getAll();

  @Select("SELECT * FROM coffees WHERE _name = #{coffeeName}")
  Coffee getCoffeeBayName(String coffeeName);
}
