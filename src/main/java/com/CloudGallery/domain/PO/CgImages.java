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
 * 图片表
 * </p>
 *
 * @author author
 * @since 2025-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cg_images")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CgImages implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "image_id", type = IdType.AUTO)
    private Long imageId;

    /**
     * 原始文件名
     */
    @TableField("original_filename")
    private String originalFilename;

    /**
     * 文件扩展名
     */
    @TableField("file_extension")
    private String fileExtension;

    /**
     * 存储路径
     */
    @TableField("storage_path")
    private String storagePath;

    /**
     * 对象存储桶
     */
    @TableField("oss_bucket")
    private String ossBucket;

    /**
     * 文件大小(字节)
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 宽度(像素)
     */
    @TableField("width")
    private Integer width;

    /**
     * 高度(像素)
     */
    @TableField("height")
    private Integer height;

    /**
     * MIME类型
     */
    @TableField("mime_type")
    private String mimeType;

    /**
     * 文件哈希值
     */
    @TableField("hash_value")
    private String hashValue;

    /**
     * 相册ID
     */
    @TableField("album_id")
    private Integer albumId;

    /**
     * 标签(JSON格式)，支持自定义
     */
    @TableField("tags")
    private String tags;

    /**
     * 分类ID
     */
    @TableField("category_id")
    private Integer categoryId;

    /**
     * 是否收藏(1=收藏，0=没收藏)
     */
    @TableField("favorites")
    private Integer favorites;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 可见性(0=私有,1=公开,2=仅好友可见)
     */
    @TableField("visibility")
    private Integer visibility;

    /**
     * 状态(0=已删除,1=正常,2=审核中,3=删除中)
     */
    @TableField("status")
    private Integer status;

    /**
     * 图片描述
     */
    @TableField("description")
    private String description;

    /**
     * 拍摄地点
     */
    @TableField("location")
    private String location;

    /**
     * 删除时间(软删除)，倒计时15天，15天内可取消删除状态
     */
    @TableField("delete_time")
    private LocalDateTime deleteTime;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("create_user")
    private Long createUser;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("update_user")
    private Long updateUser;


}
