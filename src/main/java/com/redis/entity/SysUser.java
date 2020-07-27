package com.redis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUser implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String username;

    private String password;

    @TableField(exist = false)
    private List<SysRole> roles;
}
