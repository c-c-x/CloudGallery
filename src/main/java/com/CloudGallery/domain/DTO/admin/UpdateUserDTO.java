package com.CloudGallery.domain.DTO.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


@Data
@TableName("cg_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDTO implements Serializable {

    @NotNull("id不能为空")
    private Long id;

    @Length(max = 12,min = 6,message = "用户名长度在6-12位之间" )
    private String userName;

    @Length(max = 16,min = 6,message = "密码长度在6-16位之间" )
    private String password;

    @Length(max = 8,min = 4,message = "昵称长度在4-8位之间" )
    private String nickName;

    @Length(max = 11,min = 11,message = "手机号长度为11位" )
    private String phone;

    @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+$",
            message = "邮箱格式不正确")
    private String email;

    @Max(value = 1,message = "不合规的字典参数" )
    @Min(value = 0,message = "不合规的字典参数" )
    private Integer gender;

    @Pattern(
            regexp = "^(?:[1-9]\\d{5}(?:18|19|20)\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]|[1-9]\\d{5}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])\\d{3})$",
            message = "身份证号格式不正确"
    )
    private String idNumber;


    @Max(value = 1,message = "不合规的字典参数" )
    @Min(value = 0,message = "不合规的字典参数" )
    private Integer status;

}
