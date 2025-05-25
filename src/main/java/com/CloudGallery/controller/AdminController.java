package com.CloudGallery.controller;


import com.CloudGallery.annotations.Permission;
import com.CloudGallery.common.enums.PermissionType;
import com.CloudGallery.common.response.Result;
import com.CloudGallery.domain.DTO.UserPageDTO;
import com.CloudGallery.domain.VO.UserPageVO;
import com.CloudGallery.service.IUserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param userPageDTO
     * @return 用户列表
     */
    @PostMapping("/getUserList")
    @Permission(PermissionType.ADMIN)
    public Result<UserPageVO> getUserList(@Validated @RequestBody UserPageDTO userPageDTO){
        return userService.getUserList(userPageDTO);
    }
}
