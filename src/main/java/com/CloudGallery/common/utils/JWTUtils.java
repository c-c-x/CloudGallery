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
import java.util.Date;
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
    private static final int EXPIRE_TIME = 1800;
//    private static final int EXPIRE_TIME = 10;

    /**
     * 根据用户信息生成令牌
     * @param user 用户信息
     * @return JWT令牌
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

    /**
     * 验证JWT令牌是否已过期
     * @param token JWT令牌
     * @return true表示已过期，false表示未过期或令牌无效
     */
    public static boolean isTokenExpired(String token) {
        if (token == null || token.trim().isEmpty()) {
            return true; // 空令牌视为过期
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(SIGN);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            // 检查过期时间
            return jwt.getExpiresAt().before(new Date());
        } catch (TokenExpiredException e) {
            // 明确捕获过期异常
            return true;
        } catch (JWTVerificationException e) {
            // 签名无效、令牌被篡改等情况，视为无效令牌（逻辑上等同于过期）
            return true;
        } catch (Exception e) {
            // 其他异常，如解析错误
            return true;
        }
    }


    /**
     * 即使令牌过期也能获取声明信息
     * @param token JWT令牌
     * @return 令牌声明信息，若令牌无效则返回null
     */
    public static DecodedJWT getClaimsFromExpiredToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return null;
        }

        try {
            // 先尝试常规验证
            Algorithm algorithm = Algorithm.HMAC256(SIGN);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            return verifier.verify(token);
        } catch (TokenExpiredException e) {
            // 令牌过期，使用不验证过期时间的方式解析
            try {
                // 解析令牌但不进行验证（包括过期时间）
                DecodedJWT decodedJWT = JWT.decode(token);
                // 单独验证签名，确保令牌未被篡改
                Algorithm algorithm = Algorithm.HMAC256(SIGN);
                algorithm.verify(decodedJWT);
                return decodedJWT;
            } catch (Exception ex) {
                // 签名无效或其他错误
                return null;
            }
        } catch (Exception e) {
            // 其他验证错误
            return null;
        }
    }

    /**
     * 从过期令牌中获取用户信息
     * @param token JWT令牌
     * @return 用户信息，若令牌无效则返回null
     */
    public static User getUserInfoFromExpiredToken(String token) {
        DecodedJWT decodedJWT = getClaimsFromExpiredToken(token);
        if (decodedJWT == null) {
            return null;
        }

        long id = decodedJWT.getClaim("id").asLong();
        String userName = decodedJWT.getClaim("userName").asString();
        int status = decodedJWT.getClaim("status").asInt();

        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setStatus(status);
        return user;
    }

    public static void main(String[] args) {
        User user = User.builder().id(1925115956049215488L).userName("2251600315").status(1).build();
        String token = createJWT(user);
        System.out.println(token);
        User parseJWT = parseJWT(token);
        System.out.println(parseJWT);
    }
}
