package com.cloudgallery.pojo.DTO;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;

/**
 * 注册用户信息DTO
 */
@Data
public class EnrollUserDTO {

    @NotNull("用户名不能为空")
    @Length(max = 12,min = 6,message = "用户名长度在6-12位之间" )
    private String userName;

    @NotNull("密码不能为空")
    @Length(max = 16,min = 6,message = "密码长度在6-16位之间" )
    private String password;

    @Length(max = 8,min = 4,message = "昵称长度在4-8位之间" )
    private String nickName;
}
