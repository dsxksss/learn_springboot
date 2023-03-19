package com.example.learn_springboot.manager;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

// TODO 待优化剩余问题，如缓存穿透等问题

@Component
public class RedisCacheManager {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  /**
   * 添加单条缓存
   *
   * @param key   缓存的 key
   * @param value 缓存的 value
   * @param time  缓存的时间，单位为秒
   */
  public void addToCache(String key, Object value, long time) {
    redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
  }

  /**
   * 添加多条缓存
   *
   * @param cacheMap 缓存的 key 和 value 的键值对
   * @param time     缓存的时间，单位为秒
   */
  public void addBatchToCache(Map<String, Object> cacheMap, long time) {
    redisTemplate.opsForValue().multiSet(cacheMap);
    cacheMap
      .keySet()
      .forEach(key -> redisTemplate.expire(key, time, TimeUnit.SECONDS));
  }

  /**
   * 获取缓存
   *
   * @param key 缓存的 key
   * @return 缓存的 value
   */
  public Object getFromCache(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  /**
   * 判断缓存是否存在
   *
   * @param key 缓存的 key
   * @return 存在返回 true，不存在返回 false
   */
  public boolean existsInCache(String key) {
    return redisTemplate.hasKey(key);
  }

  /**
   * 删除缓存
   *
   * @param key 缓存的 key
   */
  public void deleteFromCache(String key) {
    redisTemplate.delete(key);
  }

  /**
   * 批量删除缓存
   *
   * @param keys 缓存的 key 集合
   */
  public void deleteFromCacheBatch(String... keys) {
    for (String key : keys) {
      redisTemplate.delete(key);
    }
  }
}
