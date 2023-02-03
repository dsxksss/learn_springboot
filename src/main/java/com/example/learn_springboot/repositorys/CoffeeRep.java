package com.example.learn_springboot.repositorys;

import com.example.learn_springboot.entitys.Coffee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//! JpaRepository有两个泛型
// 前者对应的时具体操作的对象实体也就是entity类
// 后者时该对象实体类中、字段中对应的id类型
public interface CoffeeRep extends JpaRepository<Coffee, String> {
  // value : jpql或sql 语法
  // nativeQuery : 是否使用sql语法
  @Query(
    value = "SELECT id, create_time, description, image, name, price, quantity FROM coffees ORDER BY create_time DESC LIMIT ?2 OFFSET ?1",
    nativeQuery = true
  )
  /**
   *
   * @param start // 起始数据值
   * @param end // 结束数据值
   */
  List<Coffee> getCoffees(long start, long end);
}
