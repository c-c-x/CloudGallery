package com.CloudGallery.common.utils;

import java.util.List;

/**
 * List工具类
 */
public class ListUtils {

    /**
     * 判断数组是否为空
     * @param array  数组
     * @return
     * @param <T>
     */
    public static <T> boolean isEmpty(List<T> array) {
        return array == null || array.size() == 0;
    }
}
