package com.CloudGallery.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.util.Date;

/**
 * 所有实体类通用属性
 */
@Data
@TableName("cg_user")
@AllArgsConstructor
@NoArgsConstructor
public class pojo {
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    @TableField("create_user")
    private Long createUser;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 修改人
     */
    @TableField("update_user")
    private Long updateUser;

}
