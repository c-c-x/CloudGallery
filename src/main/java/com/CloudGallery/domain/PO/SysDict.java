package com.CloudGallery.domain.PO;

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
 * 系统字典分类表
 * </p>
 *
 * @author author
 * @since 2025-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysDict implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典类型编码（唯一，如"gender"）
     */
    private String typeCode;

    /**
     * 字典类型名称（如"性别"）
     */
    private String typeName;

    /**
     * 字典类型描述（可选）
     */
    private String description;

    /**
     * 状态（1=启用，0=禁用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人ID
     */
    private Long createUser;
}
