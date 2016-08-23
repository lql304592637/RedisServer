package com.server.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.server.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by lql on 2016/8/17.
 */
@RestController
@RequestMapping("/RedisServer")
public class RedisServerController {

    private Gson gson = new Gson();
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/set/{key}/{value}", method = RequestMethod.GET)
    public boolean set(@PathVariable("key") final String key, @PathVariable("value") String value) {
        return redisUtil.set(key, value);
    }

    @RequestMapping(value = "/set/{key}/{value}/{expireTime}", method = RequestMethod.GET)
    public boolean set(@PathVariable("key") final String key, @PathVariable("value") String value, @PathVariable("expireTime") Long expireTime) {
        return redisUtil.set(key, value, expireTime);
    }

    @RequestMapping(value = "/setKeys/{map}", method = RequestMethod.GET)
    public boolean setKeys(@PathVariable("map") String parameter) {
        Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
        Map<String, Map<String, Object>> map = gson.fromJson(parameter, type);
        return redisUtil.setKeys(map);
    }

    @RequestMapping(value = "/setKeys/{map}/{expireTime}", method = RequestMethod.GET)
    public boolean setKeys(@PathVariable("map") String parameter, @PathVariable("expireTime") Long expireTime) {
        Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
        Map<String, Map<String, Object>> map = gson.fromJson(parameter, type);
        return redisUtil.setKeys(map, expireTime);
    }

    @RequestMapping(value = "/setMapValue/{key}/{hashKey}/{value}", method = RequestMethod.GET)
    public boolean setMapValue(@PathVariable("key") final String key, @PathVariable("hashKey") String hashKey, @PathVariable("value") String value) {
        return redisUtil.setMapValue(key, hashKey, value);
    }

    @RequestMapping(value = "/get/{key}", method = RequestMethod.GET)
    public Object get(@PathVariable("key") final String key) {
        return redisUtil.get(key);
    }

    @RequestMapping(value = "/get/{key}/{hashKey}", method = RequestMethod.GET)
    public Object get(@PathVariable("key") final String key, @PathVariable("hashKey") final String hashKey) {
        return redisUtil.get(key, hashKey);
    }

    @RequestMapping(value = "/delete/{key}", method = RequestMethod.GET)
    public boolean delete(@PathVariable("key") final String key) {
        return redisUtil.delete(key);
    }
}
