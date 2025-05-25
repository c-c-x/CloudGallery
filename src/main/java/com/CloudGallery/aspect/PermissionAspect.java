package com.CloudGallery.aspect;

import com.CloudGallery.annotations.Permission;
import com.CloudGallery.common.enums.PermissionType;
import com.CloudGallery.common.exception.CgServiceException;
import com.CloudGallery.common.utils.UserUtils;
import com.CloudGallery.constants.RoleConstants;
import com.CloudGallery.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;
import java.util.Set;

@Aspect
@Component
public class PermissionAspect {

    @Resource
    private UserMapper userMapper;

    /**
     * 定义切点：所有使用@Permission注解的方法
     */
    @Pointcut("@annotation(com.CloudGallery.annotations.Permission)")
    public void permissionPointcut() {}


    @Before("permissionPointcut()")
    public Object before(JoinPoint joinPoint){
        // 通过JoinPoint获取方法签名（包含方法信息）
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 从方法签名中获取目标方法对象（被@Permission注解修饰的方法）
        Method method = signature.getMethod();

        //获取用户权限表
        Long userId = UserUtils.getUserId();
        Set<String> roles = userMapper.getRoles(userId);

        //获取方法上的@Permission注解实例
        // 通过Method对象获取@Permission注解（因为注解的RetentionPolicy是RUNTIME，所以运行时可获取）
        Permission permissionAnnotation = method.getAnnotation(Permission.class);
        // 提取注解中声明的权限类型（ADMIN或USER）
        PermissionType requiredType = permissionAnnotation.value();

        //判断是否具有权限
        switch (requiredType){
            case ADMIN -> {
                if (!roles.contains(RoleConstants.ADMIN)){
                    throw new CgServiceException("权限不足");
                }
            }
            case USER -> {
                if (!roles.contains(RoleConstants.CG_USER)){
                    throw new CgServiceException("权限不足");
                }
            }
            default -> throw new CgServiceException("权限不足");
        }
        return true;
    }

}
