package com.example.learn_springboot.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // 包含属性的SetGet方法
@AllArgsConstructor // 生成有参构造方法
@NoArgsConstructor // 生成无参构造方法
@ToString // 生成toString方法
@Entity // 表示我是一个实体类
@Table(name = "coffees") // 对应生成的表名
public class Coffee {

  // 表生成方式
  // @GeneratedValue(strategy = GenerationType.IDENTITY) // 按自增id生成
  @Id // 表示主键
  @Column(name = "id") // 表字段名
  private String id; // 产品ID

  @Column(name = "name")
  private String name; // 产品名称

  @Column(name = "price")
  private double price; // 产品价格

  @Column(name = "quantity")
  private Integer quantity; // 售卖数量

  @Column(name = "image")
  private String image; // 产品图片

  @Column(name = "description")
  private String description; // 产品描述

  @Column(name = "createTime")
  private long createTime; // 创建时间
}
