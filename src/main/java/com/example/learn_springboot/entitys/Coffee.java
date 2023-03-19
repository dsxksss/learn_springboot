package com.example.learn_springboot.entitys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/*
  * JSR提供的校验注解：
      * @Null 被注释的元素必须为null
      * @NotNull 被注释的元素必须不为null
      * @AssertTrue 被注释的元素必须为true
      * @AssertFalse 被注释的元素必须为false
      * @Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
      * @Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
      * @DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
      * @DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
      * @Size(max=,min=) 被注释的元素的大小必须在指定的范围内
      * @Digits(integer, fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内
      * @Past 被注释的元素必须是一个过去的日期
      * @Future 被注释的元素必须是一个将来的日期
      * @Pattern(regex=,flag=) 被注释的元素必须符合指定的正则表达式

  * HibernateValidator提供的校验注解：
      * @NotBlank(message=) 验证字符串非null，且trim后长度必须大于0
      * @Email 被注释的元素必须是电子邮箱地址
      * @Length(min=,max=) 被注释的字符串的大小必须在指定的范围内
      * @NotEmpty 被注释的字符串的必须非空
      * @Range(min=,max=,message=) 被注释的元素必须在合适的范围内
 */

@Data // 包含属性的SetGet方法
@AllArgsConstructor // 生成有参构造方法
@NoArgsConstructor // 生成无参构造方法
@ToString // 生成toString方法
@Entity // 表示我是一个实体类
@Table(name = "coffees") // 对应生成的表名
@ApiModel(description = "咖啡商品信息模型")
public class Coffee implements Serializable {

  // 表生成方式
  // @GeneratedValue(strategy = GenerationType.IDENTITY) // 按自增id生成
  @Id // 表示主键
  @Column(name = "id") // 表字段名
  @ApiModelProperty("咖啡商品ID")
  private String id;

  @Column(name = "name")
  @ApiModelProperty("咖啡名称")
  @NotBlank(message = "咖啡名称不能为空")
  @Length(min = 2, max = 10)
  private String name;

  @Column(name = "price")
  @ApiModelProperty("咖啡价格")
  @NotNull(message = "咖啡价格不能为空")
  @Min(value = 0, message = "咖啡价格不能低于0")
  @Max(value = 100000, message = "咖啡价格不能大于100000")
  private double price;

  @Column(name = "quantity")
  @ApiModelProperty("售卖数量")
  @NotNull(message = "咖啡数量不能为空")
  @Min(value = 1, message = "咖啡数量不能低于1")
  @Max(value = 10000, message = "咖啡数量不能大于10000")
  private Integer quantity;

  @Column(name = "image")
  @ApiModelProperty("咖啡图片地址")
  @NotBlank(message = "咖啡图片不能为空")
  @Length(min = 1, max = 255, message = "咖啡图片地址长度只能是1至255之间")
  private String image;

  @Column(name = "description")
  @ApiModelProperty("咖啡商品描述")
  @NotBlank(message = "咖啡描述不能为空")
  @Length(min = 1, max = 255, message = "咖啡描述长度只能是1至255之间")
  private String description;

  @Column(name = "createTime")
  @ApiModelProperty("商品创建时间")
  private long createTime;
}
