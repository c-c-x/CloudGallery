package com.cloudgallery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudgallery.common.response.Result;
import com.cloudgallery.domain.User;
import com.cloudgallery.pojo.DTO.EnrollUserDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
    /**
     * 注册用户
     * @param enrollUserDTO 注册信息
     * @return
     */
    Result<Boolean> enrollUser(EnrollUserDTO enrollUserDTO);
}
