package com.CloudGallery.domain.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户-角色关联表
 * </p>
 *
 * @author author
 * @since 2025-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_role")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID（对应sys_user.id）
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色ID（对应sys_role.id）
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 关联时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


}
