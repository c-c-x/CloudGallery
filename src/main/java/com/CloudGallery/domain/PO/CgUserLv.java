package com.CloudGallery.domain.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户权益等级关联表
 * </p>
 *
 * @author author
 * @since 2025-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cg_user_lv")
@Builder
public class CgUserLv implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户等级，字典表获取
     */
    private Long lv;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 用户id
     */
    private Long userId;

    private LocalDateTime createTime;

    private Long createUser;

    private LocalDateTime updateTime;

    private Long updateUser;


}
