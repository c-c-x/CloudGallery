package com.CloudGallery.domain.DTO;

import com.CloudGallery.domain.PO.User;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict_data")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThreadLocalUser  {
    private Long id;

    private String userName;

    private String password;

    private String nickName;

    private String phone;

    private String email;

    private Integer gender;

    private String idNumber;

    private Integer status;

    Set<String> roles;
}
