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
 * 系统字典数据表
 * </p>
 *
 * @author author
 * @since 2025-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict_data")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysDictData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联的字典类型编码（对应sys_dict.type_code）
     */
    private String dictType;

    /**
     * 字典标签（展示值，如"男"）
     */
    private String label;

    /**
     * 字典值（存储值，如"1"）
     */
    private String value;

    /**
     * 排序（数值越小越靠前）
     */
    private Integer sort;

    /**
     * 状态（1=启用，0=禁用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Long createUser;

}
