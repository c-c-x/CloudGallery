package com.CloudGallery.domain.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.CloudGallery.common.domain.pojo;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("cg_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends pojo {
        @TableId(type = IdType.INPUT)
        private Long id;

        @TableField("user_name")
        private String userName;

        @TableField("password")
        private String password;

        @TableField("nick_name")
        private String nickName;

        @TableField("phone")
        private String phone;

        @TableField("email")
        private String email;

        @TableField("gender")
        private Integer gender;

        @TableField("id_number")
        private String idNumber;

        @TableField("status")
        private Integer status;

}
