package com.CloudGallery.interceptor;

import com.CloudGallery.common.exception.CgServiceException;
import com.CloudGallery.common.utils.BaseContext;
import com.CloudGallery.common.utils.JWTUtils;
import com.CloudGallery.constants.RedisConstants;
import com.CloudGallery.domain.po.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 校验token拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private  StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {
        //获取refreshToken
        String refreshToken = request.getHeader("REFRESH_TOKEN");
        //  获取请求头中的jwt
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            throw new CgServiceException("请先登录");

        }
        try {
            User user = JWTUtils.parseJWT(token);
            BaseContext.setCurrentUser(user);
        }catch (Exception e){
            User user = JWTUtils.getUserInfoFromExpiredToken(token);
            if (user == null) {
                throw new CgServiceException("登录信息无效，重新登录");
            }
            String oldRefreshToken = stringRedisTemplate.opsForValue().get(RedisConstants.USER_TOKEN_KEY + user.getId());
            if (oldRefreshToken == null || oldRefreshToken.isEmpty()){
                throw new CgServiceException("身份过期，请重新登录");
            }
            String jwt = JWTUtils.createJWT(user);
            BaseContext.setCurrentUser(user);
            // 3.5 将新JWT写入响应头（关键修改）
            response.setHeader("Authorization",jwt);
            // 可选：解决跨域时客户端无法获取响应头的问题
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return true;
        }
        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request,
                                @NotNull HttpServletResponse response,
                                @NotNull Object handler, Exception ex) {
        // 请求处理完成后，清除 ThreadLocal 中的数据
        BaseContext.removeCurrentUser();
    }
}
