package com.CloudGallery.common.utils;

import cn.hutool.jwt.Claims;
import com.CloudGallery.domain.po.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Calendar;
import java.util.HashMap;

/**
 * JWT工具类
 */
//TODO : JWTUtils
public class JWTUtils {

    /**
     * 签名
     */
    private static final String SIGN = "CHENCHUXIAN";
    /**
     * 过期时间
     */
    private static final int EXPIRE_TIME = 86400;

    /**
     * 根据用户信息生成令牌
     * @param user 用户信息
     * @return
     */
    public static String createJWT(User user){
        HashMap<String, Object> map = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        // 20秒后令牌token失效
        instance.add(Calendar.SECOND,EXPIRE_TIME);
        return JWT.create()
                .withHeader(map) // header可以不写，因为默认值就是它
                .withClaim("id", user.getId())  //payload
                .withClaim("userName",user.getUserName())
                .withClaim("status",user.getStatus())
                .withExpiresAt(instance.getTime()) // 指定令牌的过期时间
                .sign(Algorithm.HMAC256(SIGN));
    }


    /**
     * 解析并验证 JWT 令牌
     * @param token JWT 令牌
     * @return 解析后的用户信息
     * @throws JWTVerificationException 如果验证失败
     * @throws TokenExpiredException    如果令牌已过期
     */
    public static User parseJWT(String token) throws JWTVerificationException, TokenExpiredException {
        try {
            // 创建验证器：指定签名算法和验证规则
            Algorithm algorithm = Algorithm.HMAC256(SIGN);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); // 可添加其他验证规则（如 iss、nbf 等）

            // 验证并解析令牌
            DecodedJWT jwt = verifier.verify(token);

            // 提取 payload 中的声明
            long id = jwt.getClaim("id").asLong();
            String userName = jwt.getClaim("userName").asString();
            int status = jwt.getClaim("status").asInt();

            // 构造并返回用户对象
            User user = new User();
            user.setId(id);
            user.setUserName(userName);
            user.setStatus(status);
            return user;
        } catch (TokenExpiredException e) {
            // 令牌过期异常单独处理
            throw new TokenExpiredException("JWT 令牌已过期", e.getExpiredOn());
        } catch (JWTVerificationException e) {
            // 签名无效或其他验证失败
            throw new JWTVerificationException("JWT 验证失败: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        User user = User.builder().id(1925115956049215488L).userName("2251600315").status(1).build();
        String token = createJWT(user);
        System.out.println(token);
        User parseJWT = parseJWT(token);
        System.out.println(parseJWT);
    }
}
