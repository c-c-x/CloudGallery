package com.CloudGallery.model;

/**
 * 权益数据模型
 */
public interface Rights {

    /**
     * <p>
     * 获取用户最大存储空间
     * </P>
     */
    long getMaxStorageSize();


    /**
     * <p>
     * 获取用户最大图片大小
     * </p>
     */
    long getMaxImageSize();
}
