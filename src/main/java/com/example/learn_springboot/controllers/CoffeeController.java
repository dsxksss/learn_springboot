package com.example.learn_springboot.controllers;

import com.example.learn_springboot.entitys.Coffee;
import com.example.learn_springboot.service.CoffeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* RESTful API的方式来设计的URL请求
 *获取所有咖啡信息：GET /coffees
 *获取某款咖啡信息：GET /coffees/{id}
 *新增一款咖啡：POST /coffees
 *修改某款咖啡信息：PUT /coffees/{id}
 *删除某款咖啡：DELETE /coffees/{id}
 *购买某款咖啡：POST /coffees/{id}/purchase
 */

//这是slf4j的接口，由于我们引入了logback-classic依赖，所以底层实现是logback
// private static final Logger log = LoggerFactory.getLogger(Test.class);
// 使用lombok自带的Slf4j注解 可以实现和上方代码相同的事情
// 只不过调用日志对象要使用log.xxx 来使用
@Slf4j
@Api(tags = "咖啡商品api")
@RestController
// 路由根路径
@RequestMapping("/coffees")
public class CoffeeController {

  @Autowired
  private CoffeeService coffeeService;

  // TODO 实现权限访问
  // 使用URL例子 : http://localhost:2546/coffees/
  
  @ApiOperation("根据页数获取咖啡商品信息列表")
  @ApiResponses(
    {
      @ApiResponse(code = 200, message = "获取成功"),
      @ApiResponse(code = 404, message = "咖啡数据列表为空"),
    }
  )
  @GetMapping
  public List<Coffee> getCoffees(
    @RequestParam(value = "startPage", defaultValue = "1") @ApiParam(
      "从哪页开始获取咖啡数据,每页10条数据,默认从第1页开始获取"
    ) int startPage
  ) {
    log.info("开始执行getCoffees~"); // 测试日志是否有效
    return coffeeService.getCoffees(startPage);
  }

  // 当URL指向的是某一具体业务资源（或者资源列表），例如博客，用户时，使用@PathVariable
  // 当URL需要对资源或者资源列表进行过滤，筛选时，用@RequestParam
  // 使用URL例子 : http://localhost:2546/coffees/1

  @ApiOperation("通过id获取单个咖啡商品信息")
  @ApiResponses(
    {
      @ApiResponse(code = 200, message = "获取成功"),
      @ApiResponse(code = 404, message = "没找到包含了该id的咖啡信息"),
    }
  )
  @GetMapping("/{id}")
  // @PathVariable 会去搜索path上是否有被{}包裹起来的同名的id值
  // ! 并且表示该值作用于下方的参数中 , 需注意同名
  public Coffee getCoffee(
    @NotBlank(message = "id不能为空") @PathVariable @ApiParam(
      "咖啡id"
    ) String id
  ) {
    return coffeeService.getCoffee(id);
  }

  @ApiOperation("添加咖啡商品信息")
  @ApiResponses(
    {
      @ApiResponse(code = 200, message = "添加成功"),
      @ApiResponse(code = 400, message = "添加失败"),
    }
  )
  @PostMapping
  public Coffee addCoffee(
    @Validated @RequestBody @ApiParam("要添加的商品信息") Coffee coffee
  ) {
    return coffeeService.addCoffee(coffee);
  }

  @ApiOperation("购买咖啡")
  @PostMapping("/{id}/purchase")
  public void purchaseCoffee() {
    // TODO 待实现购买咖啡功能
  }

  @ApiOperation("通过id修改单个咖啡商品信息")
  @ApiResponses(
    {
      @ApiResponse(code = 200, message = "修改成功"),
      @ApiResponse(code = 404, message = "没找到包含了该id的咖啡信息"),
    }
  )
  @PutMapping
  public Coffee updateCoffee(@Validated @RequestBody Coffee reqCoffee) {
    return coffeeService.updateCoffee(reqCoffee);
  }

  @ApiOperation("通过id删除单个咖啡商品信息")
  @ApiResponses(
    {
      @ApiResponse(code = 200, message = "删除成功"),
      @ApiResponse(code = 404, message = "没找到包含了该id的咖啡信息"),
    }
  )
  @DeleteMapping("/{id}")
  public Coffee delectCoffee(
    @NotBlank(message = "id不能为空") @PathVariable @ApiParam(
      "咖啡id"
    ) String id
  ) {
    return coffeeService.deleteCoffee(id);
  }
}
