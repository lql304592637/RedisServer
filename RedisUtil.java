package com.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lql on 2016/8/16.
 */
@Component
public class RedisUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisTemplate redisTemplate;

    //添加key-value
    public boolean set(final String key, Object value) {
        logger.info("key ==>" + key + ",value ==>" + value);
        boolean result = false;
        if(!key.isEmpty()) {
            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                result = true;
            }
            catch (Exception e) {
                logger.error("key ==>" + key + ",value ==>" + value + "," + e.getMessage());
            }
        }
        return  result;
    }

    //添加key-value-expireTime
    public boolean set(final String key, Object value, Long expireTime) {
        logger.info("key ==>" + key + ",value ==>" + value + ",expireTime" + expireTime);
        boolean result = false;
        if(!key.isEmpty()) {
            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
                result = true;
            }
            catch (Exception e) {
                logger.error("key ==>" + key + ",value ==>" + value + ",expireTime" + expireTime + "," + e.getMessage());
            }
        }
        return result;
    }

    //修改map的value
    public boolean setMapValue(final String key, final String hashKey, Object value) {
        logger.info("key ==>" + key + ",hashKey==>" + hashKey + ",value ==>" + value);
        boolean result = false;
        try {
            HashOperations operations = redisTemplate.opsForHash();
            operations.put(key, hashKey, value);
            result = true;
        }
        catch (Exception e) {
            logger.error("key ==>" + key + ",hashKey==>" + hashKey + ",value ==>" + value + "," + e.getMessage());
        }
        return  result;
    }

    //批量添加
    public boolean setKeys(Map<String, Map<String, Object>> map) {
        logger.info("map==>" + map);
        boolean result = false;
        if(!map.isEmpty()) {
            try {
                HashOperations operations = redisTemplate.opsForHash();
                for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                    operations.putAll(entry.getKey(), entry.getValue());
                }
                result = true;
            }
            catch (Exception e) {
                logger.error("map==>" + map + "," + e.getMessage());
            }
        }
        return result;
    }

    //批量添加-expireTime
    public boolean setKeys(Map<String, Map<String, Object>> map, Long expireTime) {
        logger.info("map==>" + map + ",expireTime==>" + expireTime);
        boolean result = false;
        if(!map.isEmpty()) {
            try {
                HashOperations operations = redisTemplate.opsForHash();
                for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                    operations.putAll(entry.getKey(), entry.getValue());
                    redisTemplate.expire(entry.getKey(), expireTime, TimeUnit.SECONDS);
                }
                result = true;
            }
            catch (Exception e) {
                logger.error("map==>" + map + "," + e.getMessage());
            }
        }
        return result;
    }

    //删除key
    public boolean delete(final String key) {
        logger.info("delete key==>" + key);
        boolean result = false;
        if(exist(key)) {
            try {
                redisTemplate.delete(key);
                result = true;
            }
            catch (Exception e) {
                logger.error("delete key==>" + key + "," + e.getMessage());
            }
        }
        return result;
    }

    //批量删除key
    public boolean deleteKeys(final String keys) {
        logger.info("delete keys==>" + keys);
        boolean result = false;
        try {
            Set<Serializable> keySet = redisTemplate.keys(keys);
            if (keySet.size() > 0) {
                redisTemplate.delete(keySet);
                result = true;
            }
        }
        catch (Exception e) {
            logger.error("delete keys==>" + keys + "," + e.getMessage());
        }
        return  result;
    }

    //key是否存在
    public boolean exist(final String key) {
        return redisTemplate.hasKey(key);
    }

    //获取value
    public Object get(final String key) {
        Object result = null;
        logger.info("get key==>" + key);
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result = operations.get(key);
        }
        catch (Exception e) {
            logger.error("get key==>" + key + "," + e.getMessage());
        }
        return result;
    }

    //获取map
    public Object get(final String key, final String hashKey) {
        Object result = null;
        logger.info("get Map key==>" + key + ",hashKey==>" + hashKey);
        try {
            HashOperations operations = redisTemplate.opsForHash();
            result = operations.get(key, hashKey);
        }
        catch (Exception e) {
            logger.info("get Map key==>" + key + ",hashKey==>" + hashKey + "," + e.getMessage());
        }
        return  result;
    }

}
