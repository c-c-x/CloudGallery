package com.CloudGallery.service;

import com.CloudGallery.domain.DTO.UpdateUserDTO;
import com.CloudGallery.domain.DTO.UserPageDTO;
import com.CloudGallery.domain.VO.ByIdUserVO;
import com.CloudGallery.domain.VO.LoginUserVO;
import com.CloudGallery.domain.VO.UserPageVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.CloudGallery.common.response.Result;
import com.CloudGallery.domain.PO.User;
import com.CloudGallery.domain.DTO.EnrollUserDTO;
import com.CloudGallery.domain.DTO.LoginUserDTO;
import com.github.pagehelper.PageInfo;
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
    Result<LoginUserVO> loginUser(LoginUserDTO loginUserDTO, HttpServletRequest request);

    /**
     * 获取用户列表
     * @param userPageDTO
     * @return 用户列表
     */
    Result<UserPageVO> getUserList(UserPageDTO userPageDTO);

    /**
     * 登出
     * @return 登出结果
     */
    Result<Boolean> loginOut();

    /**
     * 修改用户信息
     * @param updateUserDTO 修改用户信息
     * @return 修改结果
     */
    Result<Boolean> updateUser(UpdateUserDTO updateUserDTO);


    /**
     * 获取指定用户信息
     * @param id 用户id
     * @return  用户信息
     */
    Result<ByIdUserVO> getUserById(Long id);


    /**
     * 设置管理员
     * @param id 用户id
     * @return 设置结果
     */
    Result<Boolean> setAdmin(Long id);


    /**
     * 移除管理员
     * @param id 用户id
     * @return 移除结果
     */
    Result<Boolean> removeAdmin(Long id);
}
