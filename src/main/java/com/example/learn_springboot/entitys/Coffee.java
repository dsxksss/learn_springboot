package com.example.learn_springboot.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "咖啡商品信息模型")
public class Coffee {

  // 表生成方式
  // @GeneratedValue(strategy = GenerationType.IDENTITY) // 按自增id生成
  @Id // 表示主键
  @Column(name = "id") // 表字段名
  @ApiModelProperty("咖啡商品ID")
  private String id;
  
  @Column(name = "name")
  @ApiModelProperty("咖啡名称")
  private String name;
  
  @Column(name = "price")
  @ApiModelProperty("咖啡价格")
  private double price;
  
  @Column(name = "quantity")
  @ApiModelProperty("售卖数量")
  private Integer quantity;
  
  @Column(name = "image")
  @ApiModelProperty("咖啡图片地址")
  private String image;
  
  @Column(name = "description")
  @ApiModelProperty("咖啡商品描述")
  private String description;
  
  @Column(name = "createTime")
  @ApiModelProperty("商品创建时间")
  private long createTime;
}
