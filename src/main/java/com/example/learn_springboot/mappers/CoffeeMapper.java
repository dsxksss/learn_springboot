package com.example.learn_springboot.mappers;

import com.example.learn_springboot.entitys.Coffee;
import java.util.List;

import org.apache.ibatis.annotations.Select;
// import org.apache.ibatis.annotations.Results;
// import org.apache.ibatis.annotations.Result;

// Integer id;// 产品ID
// String name;// 产品名称
// double price;// 产品价格
// Integer quantity;// 售卖数量
// String image;// 产品图片
// String description;// 产品描述
// long createTime;// 创建时间

/*
 * 
 * @Select 是查询类的注解，所有的查询均使用这个
 * @Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，
 *    如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
 * @Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
 * @Update 负责修改，也可以直接传入对象
 * @delete 负责删除
 */

public interface CoffeeMapper {

  // TODO: 待实现剩下功能
  @Select("SELECT * FROM coffees")
  List<Coffee> getAll();

  @Select("SELECT * FROM coffees WHERE _name = #{coffeeName}")
  Coffee getCoffeeBayName(String coffeeName);
}
