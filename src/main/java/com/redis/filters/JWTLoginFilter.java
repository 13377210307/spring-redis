package com.redis.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.entity.SysUser;
import com.redis.utils.JWTUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static String KEY = "security";

    private static String SALT = "security";

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SysUser sysUser = new ObjectMapper().readValue(request.getInputStream(),SysUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            sysUser.getUsername(),
                            sysUser.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("用户名或密码错误");
            throw new RuntimeException(e);
        }
    }

    // 用户登录成功之后此方法会被调用，我们可以在这个方法中生成token

    /**
     *
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<>();
        map.put("username",authResult.getName());  //auth中包含用户名
        //map.put("userId",);
        map.put("roles",authResult.getAuthorities());
        String token = JWTUtils.encode(KEY, map, SALT);
        response.addHeader("Authorization", "Bearer " + token);
    }
}
