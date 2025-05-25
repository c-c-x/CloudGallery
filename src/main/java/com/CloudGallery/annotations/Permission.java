package com.CloudGallery.annotations;

import com.CloudGallery.common.enums.PermissionType;

import java.lang.annotation.*;

/**
 * 权限控制注解（参数指定权限级别）
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    /**
     * 权限级别枚举参数
     */
    PermissionType value();
}
    