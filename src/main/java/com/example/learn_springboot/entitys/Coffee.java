package com.example.learn_springboot.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // 包含属性的SetGet方法
@AllArgsConstructor // 生成有参构造方法
@NoArgsConstructor // 生成无参构造方法
@ToString // 生成toString方法
public class Coffee {

  private String id; // 产品ID
  private String name; // 产品名称
  private double price; // 产品价格
  private Integer quantity; // 售卖数量
  private String image; // 产品图片
  private String description; // 产品描述
  private long createTime; // 创建时间
}
