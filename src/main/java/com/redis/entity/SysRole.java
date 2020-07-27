package com.redis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRole implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    private String key;
}
