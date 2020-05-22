package com.github.cocktail.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Liuhao
 * Description:Jedis工具
 * Date:14:40 2020/5/21
 */
@Component
@Slf4j
@Validated
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 设置值
     */
    public String setValue(@NotBlank(message = "无效的Key") String key,
                           @NotBlank(message = "无效的Value") String value) {
        Jedis jedis = this.getJedis();
        String result = jedis.set(key, value);
        jedis.close();
        return result;
    }

    /**
     * 设置值和失活时间
     *
     * @param expireTime 失活时间，单位（秒）
     */
    public String setValueWithExpireTime(@NotBlank(message = "无效的Key") String key,
                                         @NotBlank(message = "无效的Value") String value,
                                         @Range(min = 0, max = Integer.MAX_VALUE, message = "无效的失效时间") int expireTime) {
        Jedis jedis = this.getJedis();
        String result = jedis.setex(key, expireTime, value);
        jedis.close();
        return result;
    }

    /**
     * 获取值
     */
    public String get(@NotBlank(message = "无效的Key") String key) {
        Jedis jedis = this.getJedis();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    /**
     * 删除值
     */
    public Long delete(@NotBlank(message = "无效的Key") String key) {
        Jedis jedis = this.getJedis();
        Long del = jedis.del(key);
        jedis.close();
        return del;
    }

    /**
     * 判断是否存在
     */
    public Boolean existKey(@NotBlank(message = "无效的Key") String key) {
        Jedis jedis = this.getJedis();
        Boolean exists = jedis.exists(key);
        jedis.close();
        return exists;
    }

}