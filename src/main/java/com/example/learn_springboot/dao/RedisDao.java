package com.example.learn_springboot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisDao {

  @Autowired
  private StringRedisTemplate stringredisTemplate;

  public boolean setKey(String key, String value) {
    // 1分钟过期
    // this.template.opsForValue().set(key, value, 1, TimeUnit.MINUTES);

    // 不设置过期
    try {
      this.stringredisTemplate.opsForValue().set(key, value);
      return true; // true表示设置成功
    } catch (Exception e) {
      return false; // false表示设置失败
    }
  }

  public String getValue(String key) {
    return this.stringredisTemplate.opsForValue().get(key);
  }

  public boolean deleteValue(String key) {
    return this.stringredisTemplate.delete(key);
  }
}
