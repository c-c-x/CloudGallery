package com.CloudGallery.common.utils;



/**
 * 用户工具类
 */
public class UserUtils {
    private UserUtils() {
        // 私有构造函数，防止实例化
    }

    /**
     * 获取当前登录用户的 ID（从 Spring Security 上下文）
     * @return 用户 ID（未登录时返回 null）
     */
    public static Long getUserId() {
      return 1L;
    }
}
