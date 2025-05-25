package com.CloudGallery.controller;

import com.CloudGallery.common.response.Result;
import com.CloudGallery.domain.DTO.EnrollUserDTO;
import com.CloudGallery.domain.DTO.LoginUserDTO;
import com.CloudGallery.domain.VO.LoginUserVO;
import com.CloudGallery.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private IUserService userService;


    /**
     * 注册用户
     * @return 注册结果
     */
    @PostMapping("/enroll")
    public Result<Boolean> enrollUser(
            @Validated @RequestBody EnrollUserDTO enrollUserDTO){
        return userService.enrollUser(enrollUserDTO);
    }

    /**
     * 用户登录
     * @param loginUserDTO 登录信息
     * &#064;Param  request 请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<LoginUserVO> loginUser(@Validated @RequestBody LoginUserDTO loginUserDTO, HttpServletRequest request){
        return userService.loginUser(loginUserDTO,request);
    }


    /**
     * 登出
     * @return 登出结果
     */
    @GetMapping("/loginOut")
    public Result<Boolean> loginOut(){
        return userService.loginOut();
    }

    @PostMapping("/upload")
    public void upload(HttpServletRequest request){
    }
}
