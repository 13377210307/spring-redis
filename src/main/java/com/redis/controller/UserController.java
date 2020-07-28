package com.redis.controller;

import com.redis.entity.SysUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/all")
    public String testAll() {
        return "任何角色都可访问";
    }

    @GetMapping("/admin")
    public String testAdmin() {
        System.out.println("超管测试成功");
       return "超管测试成功";
    }

    @GetMapping("/user")
    public String testUser() {
        return "用户测试成功";
    }
}
