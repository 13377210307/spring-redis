package com.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redis.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<SysUser> {

    SysUser getUserInfo(@Param("username") String username);
}
