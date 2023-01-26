package com.example.learn_springboot.controllers;

import com.example.learn_springboot.entitys.Coffee;
import com.example.learn_springboot.repositorys.CoffeeRep;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/* RESTful API的方式来设计的URL请求
 *获取所有咖啡信息：GET /coffees
 *获取某款咖啡信息：GET /coffees/{id}
 *新增一款咖啡：POST /coffees
 *修改某款咖啡信息：PUT /coffees/{id}
 *删除某款咖啡：DELETE /coffees/{id}
 *购买某款咖啡：POST /coffees/{id}/purchase
 */

//这是slf4j的接口，由于我们引入了logback-classic依赖，所以底层实现是logback
// private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
// 使用lombok自带的Slf4j注解 可以实现和上方代码相同的事情
// 只不过调用日志对象要使用log.xxx 来使用
@Slf4j
@Api(tags = "咖啡商品api")
@RestController
// 路由根路径
@RequestMapping("/coffees")
public class CoffeeController {

  // TODO 验证客户端数据是否有效且格式有效 (%10)
  // TODO 实现权限访问
  // TODO redis缓存层

  @Resource
  private CoffeeRep coffeeRep;

  // 使用URL例子 : http://localhost:2546/coffees/
  // TODO 分页查询
  // TODO 排序查询

  @ApiOperation("获取全部咖啡商品信息列表")
  @ApiResponses(
    {
      @ApiResponse(code = 200, message = "获取成功"),
      @ApiResponse(code = 404, message = "咖啡数据列表为空"),
    }
  )
  @GetMapping
  public List<Coffee> getCoffees() {
    // FIXME: 中文输出至日志后有乱码问题
    log.info("开始执行getCoffees~"); // 测试日志是否有效
    List<Coffee> coffeeList = coffeeRep.findAll();

    // 如果全部咖啡信息列表里没有一个信息的话则返回404
    if (coffeeList.size() <= 0) throw new ResponseStatusException(
      HttpStatus.NOT_FOUND
    );

    return coffeeList;
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
    @PathVariable @ApiParam("要获取某个咖啡商品信息的id") String id
  ) {
    // 如果没找到的话 则返回404状态码
    if (!coffeeRep.existsById(id)) throw new ResponseStatusException(
      HttpStatus.NOT_FOUND
    );
    // 找到则返回
    Coffee coffee = coffeeRep.findById(id).get();
    return coffee;
  }

  @ApiOperation("添加咖啡商品信息")
  @ApiResponses(
    {
      @ApiResponse(code = 200, message = "添加成功"),
      @ApiResponse(code = 400, message = "添加失败"),
    }
  )
  @PostMapping
  public Coffee addCoffee(@Validated @RequestBody Coffee coffee) {
    Coffee newCoffee = coffee;
    // 生成ID
    newCoffee.setId(UUID.randomUUID().toString());
    // 生成13位时间戳 (13位时间戳是精确到毫秒)
    // 如果想生成10位时间戳的话 (10位时间戳是精确到秒) 在13位基础上除以1000即可
    newCoffee.setCreateTime(new Date().getTime() / 1000);
    coffeeRep.save(newCoffee);
    return newCoffee;
  }

  @ApiOperation("通过id修改单个咖啡商品信息")
  @ApiResponses(
    {
      @ApiResponse(code = 200, message = "修改成功"),
      @ApiResponse(code = 404, message = "没找到包含了该id的咖啡信息"),
    }
  )
  @PutMapping("/{id}")
  public Coffee updateCoffee(
    @PathVariable String id,
    @RequestBody Coffee reqCoffee
  ) {
    // 如果没找到的话 则返回404状态码
    if (!coffeeRep.existsById(id)) throw new ResponseStatusException(
      HttpStatus.NOT_FOUND
    );
    Coffee oldCoffee = reqCoffee;
    Coffee newCoffee = coffeeRep.findById(id).get();
    oldCoffee.setId(newCoffee.getId());
    oldCoffee.setCreateTime(newCoffee.getCreateTime());
    coffeeRep.save(reqCoffee);
    return newCoffee;
  }

  @ApiOperation("通过id删除单个咖啡商品信息")
  @ApiResponses(
    {
      @ApiResponse(code = 200, message = "删除成功"),
      @ApiResponse(code = 404, message = "没找到包含了该id的咖啡信息"),
    }
  )
  @DeleteMapping("/{id}")
  public Coffee delectCoffee(@PathVariable String id) {
    // 如果没找到的话 则返回404状态码
    if (!coffeeRep.existsById(id)) throw new ResponseStatusException(
      HttpStatus.NOT_FOUND
    );
    Coffee deletCoffee = coffeeRep.findById(id).get();
    coffeeRep.deleteById(id);
    return deletCoffee;
  }
}
