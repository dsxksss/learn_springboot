package com.example.learn_springboot;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * @SpringBootTest	用于指定测试类启用Spring Boot Test，默认会提供Mock环境
 *  @ExtendWith	如果只想启用Spring环境进行简单测试，不想启用Spring Boot环境，可以配置扩展为：SpringExtension
 *  @Test	指定方法为测试方法
 *  @TestMethodOrder	用于配置测试类中方法的执行顺序策略，配置为OrderAnnotation时，按@Order顺序执行
 *  @Order	用于配置方法的执行顺序，数字越低执行顺序越高
 *  @DisplayName	用于指定测试类和测试方法的别名
 *  @BeforeAll	在测试类的所有测试方法前执行一次，可用于全局初始化
 *  @AfterAll	在测试类的所有测试方法后执行一次，可用于全局销毁资源
 *  @BeforeEach	在测试类的每个测试方法前都执行一次
 *  @AfterEach	在测试类的每个测试方法后都执行一次
 *  @Disabled	禁用测试方法
 *  @RepeatedTest	指定测试方法重复执行
 *  @ParameterizedTest	指定参数化测试方法，类似重复执行，从@ValueSource中获取参数
 *  @ValueSource	用于参数化测试指定参数
 *  @AutoConfigureMockMvc	启用MockMvc的自动配置，可用于测试接口
 */

// ! 注意 spring test 是同步执行的
@SpringBootTest

class LearnSpringbootApplicationTests {

 
}
