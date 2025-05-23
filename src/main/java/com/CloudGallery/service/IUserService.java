package com.CloudGallery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.CloudGallery.common.response.Result;
import com.CloudGallery.domain.po.User;
import com.CloudGallery.domain.DTO.EnrollUserDTO;
import com.CloudGallery.domain.DTO.LoginUserDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IUserService extends IService<User> {
    /**
     * 注册用户
     * @param enrollUserDTO 注册信息
     */
    Result<Boolean> enrollUser(EnrollUserDTO enrollUserDTO);


    /**
     * 用户登录
     * @param loginUserDTO 登录信息
     * @return 登录结果
     */
    Result<String> loginUser(LoginUserDTO loginUserDTO, HttpServletRequest request);
}
