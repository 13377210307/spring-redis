package com.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redis.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<SysRole> {

    List<SysRole> getRoleByUserId(@Param("userId") String userId);
}
