package com.cloudgallery.common.domain;

import lombok.Data;

/**
 * 所有实体类通用属性
 */
@Data
public class pojo {
    /**
     * 创建时间
     */
    private Data createTime;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改时间
     */
    private Data updateTime;

    /**
     * 修改人
     */
    private Integer updateUser;

}
