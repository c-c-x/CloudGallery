package com.CloudGallery.interceptor;

import com.CloudGallery.common.exception.CgServiceException;
import com.CloudGallery.common.utils.BaseContext;
import com.CloudGallery.common.utils.JWTUtils;
import com.CloudGallery.constants.RedisConstants;
import com.CloudGallery.domain.DTO.ThreadLocalUser;
import com.CloudGallery.domain.PO.User;
import com.CloudGallery.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

/**
 * 校验token拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private  StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserMapper userMapper;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {
        //  获取refreshToken
        String refreshToken = request.getHeader("refresh_token");
        //  获取请求头中的jwt
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            throw new CgServiceException("请先登录");

        }
        try {
            User user = JWTUtils.parseJWT(token);
            BaseContext.setCurrentUser( getThreadLocalUser(user));
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
            BaseContext.setCurrentUser(getThreadLocalUser(user));
            // 将新JWT写入响应头（关键修改）
            response.setHeader("Authorization",jwt);
            // 解决跨域时客户端无法获取响应头的问题
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return true;
        }
        return true;
    }

    /**
     * 获取用户信息
     * @param user 用户信息
     * @return 线程用户信息
     */
    private ThreadLocalUser getThreadLocalUser(User user) {
        Set<String> roles = userMapper.getRoles(user.getId());
        return ThreadLocalUser.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .nickName(user.getNickName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .gender(user.getGender())
                .idNumber(user.getIdNumber())
                .status(user.getStatus())
                .roles(roles)
                .build();
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request,
                                @NotNull HttpServletResponse response,
                                @NotNull Object handler, Exception ex) {
        // 请求处理完成后，清除 ThreadLocal 中的数据
        BaseContext.removeCurrentUser();
    }
}
