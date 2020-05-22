package com.github.cocktail.controller;

import com.github.cocktail.common.RestfulResponse;
import com.github.cocktail.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liuhao
 * Description:jedis测试
 * Date:16:48 2020/5/21
 */
@RestController
public class JedisTestController {

    @Autowired
    private JedisUtil jedisUtil;

    @GetMapping(
            value = "/test/jedis/util",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public RestfulResponse<String> testJedisUtil(@RequestParam(value = "key") String key) {
        String wwww = this.jedisUtil.setValueWithExpireTime(key, "wwww", 100);
        return RestfulResponse.success(wwww);
    }

}