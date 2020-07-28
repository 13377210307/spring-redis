package com.redis.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JWTUtils {

    /** token 过期时间: 1天 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 1;

    /**
     * 生成token
     * key：公共部分
     * param：签名：存放用户id及昵称
     * salt：盐，可以使用ip地址
     * @param key
     * @param salt
     * @return
     */
    public static String encode (String key, Map<String, Object> param, String salt){
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        if(salt != null){
            key+=salt;
        }
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.signWith(SignatureAlgorithm.HS256,key) //参数1：签名算法；参数2：密钥（盐值）
                .setIssuedAt(iatDate)
                .setExpiration(expiresDate)  //过期时间
                .setClaims(param); //用户信息
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * 解密token值
     */
    public static Map<String, Object> decode(String token, String key, String salt){
        Claims claims = null;
        if(salt != null){
            key+=salt;
        }
        try{
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        }catch(Exception e){
            return null;
        }
        return claims;
    }
}
