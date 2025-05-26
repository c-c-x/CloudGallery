package com.CloudGallery.controller;


import com.CloudGallery.annotations.Permission;
import com.CloudGallery.common.enums.PermissionType;
import com.CloudGallery.common.response.Result;
import com.CloudGallery.domain.DTO.admin.UpdateUserDTO;
import com.CloudGallery.domain.DTO.admin.UserPageDTO;
import com.CloudGallery.domain.VO.ByIdUserVO;
import com.CloudGallery.domain.VO.UserPageVO;
import com.CloudGallery.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     管理员控制器
 * <P>
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IUserService userService;

    /**
     * 获取用户列表
     * @param userPageDTO 用户分页
     * @return 用户列表
     */
    @PostMapping("/getUserList")
    @Permission(PermissionType.ADMIN)
    public Result<UserPageVO> getUserList(@Validated @RequestBody UserPageDTO userPageDTO){
        return userService.getUserList(userPageDTO);
    }


    /**
     * 修改用户信息
     * @param updateUserDTO 修改用户信息
     * @return 修改结果
     */
    @PutMapping("/updateUser")
    @Permission(PermissionType.ADMIN)
    public Result<Boolean> updateUser(@Validated @RequestBody UpdateUserDTO updateUserDTO){
        return userService.updateUser(updateUserDTO);
    }

    /**
     * 获取指定用户信息
     * @param id 用户id
     * @return  用户信息
     */
    @GetMapping("/getUserById/{id}")
    @Permission(PermissionType.ADMIN)
    public Result<ByIdUserVO> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    /**
     * 设置管理员
     * @param id 用户id
     * @return 设置结果
     */
    @GetMapping("/setAdmin/{id}")
    @Permission(PermissionType.ADMIN)
    public Result<Boolean> setAdmin(@PathVariable Long id){
        return userService.setAdmin(id);
    }

    /**
     * 移除管理员
     * @param id 用户id
     * @return 移除结果
     */
    @GetMapping("/removeAdmin/{id}")
    @Permission(PermissionType.ADMIN)
    public Result<Boolean> removeAdmin(@PathVariable Long id){
        return userService.removeAdmin(id);
    }


}
