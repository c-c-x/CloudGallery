package com.CloudGallery.common.utils;

import com.CloudGallery.domain.DTO.ThreadLocalUser;
import com.CloudGallery.domain.PO.User;

/**
 * 基于ThreadLocal封装工具类，用于保存和获取当前登录用户ID
 */
public class BaseContext {
    // 使用 ThreadLocal 存储当前线程的用户ID
    private static final ThreadLocal<ThreadLocalUser> threadLocal = new ThreadLocal<>();

    // 设置当前线程的用户
    public static void setCurrentUser(ThreadLocalUser user) {
        threadLocal.set(user);
    }

    // 获取当前线程的用户
    public static ThreadLocalUser getCurrentUser() {
        return threadLocal.get();
    }

    // 移除当前线程的用户（防止内存泄漏）
    public static void removeCurrentUser() {
        threadLocal.remove();
    }
}