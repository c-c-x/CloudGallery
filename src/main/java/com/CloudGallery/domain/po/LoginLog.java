package com.CloudGallery.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: 用户登录日志实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("login_log")
public class LoginLog {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 登陆人id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 登陆人姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 登录ip
     */
    @TableField("user_ip")
    private String userIp;

    /**
     * ip归属地信息
     */
    @TableField("ip_attribution")
    private String ipAttribution;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}