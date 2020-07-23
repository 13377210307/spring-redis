package com.redis;

import com.redis.entity.User;
import com.redis.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testString() {
        this.redisTemplate.opsForValue().set("string","操作String类型数据");
        System.out.println(this.redisTemplate.opsForValue().get("string"));
    }

    @Test
    public void testObject() {
        User user = new User();
        user.setUsername("张三");
        user.setAge(13);
        this.redisTemplate.opsForValue().set("user",user);
        System.out.println(this.redisTemplate.opsForValue().get("user"));
    }

    @Test
    public void testUtils() {
        this.redisUtil.set("utils","过期时间为10秒",10);
    }

}
