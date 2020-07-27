package com.redis.service.impl;

import com.redis.entity.SysRole;
import com.redis.entity.SysUser;
import com.redis.mapper.RoleMapper;
import com.redis.mapper.UserMapper;
import com.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser userInfo = this.userMapper.getUserInfo(username);
        if (userInfo == null) {
            throw new RuntimeException("用户不存在");
        }
        //设置用户缓存
        this.redisUtil.set("user",username,20);
        //获取角色信息
        String roles = "";
        List<SysRole> sysRoles = this.roleMapper.getRoleByUserId(userInfo.getId());
        if (!CollectionUtils.isEmpty(sysRoles)) {
            roles = sysRoles.stream().map(role -> role.getKey()).collect(Collectors.joining());
        }
        return new User(username,userInfo.getPassword(),true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList(roles));
    }
}
