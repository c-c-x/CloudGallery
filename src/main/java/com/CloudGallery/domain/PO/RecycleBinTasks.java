package com.CloudGallery.domain.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 回收站清理任务表
 * </p>
 *
 * @author author
 * @since 2025-06-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("recycle_bin_tasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecycleBinTasks implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 图片ID
     */
    @TableField("image_id")
    private Long imageId;

    /**
     * 计划删除时间
     */
    @TableField("delete_time")
    private LocalDateTime deleteTime;

    /**
     * 任务状态(0=待处理,1=处理中,2=处理完毕,3=任务取消)
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


}
