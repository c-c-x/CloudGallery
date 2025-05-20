package com.cloudgallery.controller;

import com.cloudgallery.common.response.Result;
import com.cloudgallery.pojo.DTO.EnrollUserDTO;
import com.cloudgallery.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共控制层
 */
@RestController
@RequestMapping("/public")
public class PublicController {

    /**
     * 用户服务
     */
    @Resource
    private UserService userService;

    /**
     * 注册用户
     * @return 注册结果
     */
    @PostMapping("/enroll")
    public Result<Boolean> enrollUser(
            @Validated @RequestBody EnrollUserDTO enrollUserDTO){
        return userService.enrollUser(enrollUserDTO);
    }
}
