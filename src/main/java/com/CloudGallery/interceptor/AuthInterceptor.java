package com.CloudGallery.interceptor;

import com.CloudGallery.common.exception.CgServiceException;
import com.CloudGallery.common.utils.JWTUtils;
import com.CloudGallery.domain.po.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录拦截器
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            throw new CgServiceException("请先登录");

        }
        try {
            JWTUtils.parseJWT(token);
        }catch (Exception e){
            throw new CgServiceException(e.getMessage());
        }
        return true;
    }

}
