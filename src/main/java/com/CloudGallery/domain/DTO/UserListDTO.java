package com.CloudGallery.domain.DTO;

import lombok.Data;

@Data
public class UserListDTO {
    private Long id;

    private String userName;

    private String nickName;

    private String phone;

    private String email;

    private Integer gender;

    private String idNumber;
}
