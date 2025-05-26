package com.CloudGallery.domain.DTO.admin;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserPageDTO {

    @Length(max = 12,min = 6,message = "用户名长度在6-12位之间" )
    private String userName;

    private String nickName;

    private Integer gender;
    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;
}
