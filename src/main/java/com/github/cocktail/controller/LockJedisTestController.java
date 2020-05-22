package com.github.cocktail.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

/**
 * @author Liuhao
 * Description:Jedis锁简单测试
 * Date:11:26 2020/5/19
 */
@RestController
@Slf4j
@Api(tags = {"API-Jedis简单锁测试"}, protocols = "http")
public class LockJedisTestController {

    /**
     * 锁保持时间；默认5分钟
     */
    private static final Integer LOCK_KEEPS_TIME = 300;
    /**
     * 获取锁的最大尝试时间；默认2分钟
     */
    private static final Integer TRY_TIME_OUT = 120;
    /**
     * 设置成功
     */
    private static final String SET_SUCCESS = "OK";

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private JedisPool jedisPool;

    @ApiOperation(value = "加锁", notes = "加锁")
    @GetMapping(
            value = "/test/jedis/lock",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public String testJedisLock(@ApiParam("锁key") @RequestParam(value = "key") String key) {
        Jedis jedis = this.jedisPool.getResource();
        //默认redis DB
        jedis.select(1);
        //锁value
        String value = UUID.randomUUID().toString().replaceAll("-", "");
        SetParams params = SetParams.setParams().nx().px(LOCK_KEEPS_TIME * 1000L);
        //尝试在最大尝试时间内持续获取锁
        long start = Instant.now().getEpochSecond();
        Boolean getLock = Boolean.FALSE;
        do {
            String lockResult = jedis.set(key, value, params);
            if (SET_SUCCESS.equals(lockResult)) {
                log.info("add lock success. lock_key:{},lock_value:{},lock_keeps_time:{}", key, value, LOCK_KEEPS_TIME * 1000L);
                getLock = Boolean.TRUE;
                return value;
            }
        } while ((Instant.now().getEpochSecond() - TRY_TIME_OUT) < start);
        //关闭连接
        jedis.close();
        if (!getLock) {
            throw new IllegalArgumentException("获取锁失败");
        }
        return value;
    }

    @ApiOperation(value = "解锁", notes = "解锁")
    @GetMapping(
            value = "/test/jedis/unlock",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public Boolean testJedisUnlock(@ApiParam("锁key") @RequestParam(value = "key") String key,
                                   @ApiParam("锁value") @RequestParam(value = "value") String value) {
        String releaseSuccess = "1";
        Jedis jedis = jedisPool.getResource();
        jedis.select(1);
        String command = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        Object eval = jedis.eval(command, Collections.singletonList(key), Collections.singletonList(value));
        Boolean unlock = Boolean.FALSE;
        if (releaseSuccess.equals(eval.toString())) {
            log.info("unlock success. lock_key:{},lock_value:{}", key, value);
            unlock = Boolean.TRUE;
            return true;
        }
        jedis.close();
        if (!unlock) {
            throw new IllegalArgumentException("解锁失败");
        }
        return false;
    }

}