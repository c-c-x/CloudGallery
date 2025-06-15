package com.CloudGallery.controller;

import com.CloudGallery.common.response.Result;
import com.CloudGallery.common.utils.BaseContext;
import com.CloudGallery.common.utils.UserUtils;
import com.CloudGallery.domain.DTO.ThreadLocalUser;
import com.CloudGallery.domain.DTO.pubilc.EnrollUserDTO;
import com.CloudGallery.domain.DTO.pubilc.LoginUserDTO;
import com.CloudGallery.domain.VO.LoginUserVO;
import com.CloudGallery.service.ICgImagesService;
import com.CloudGallery.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * 公共控制层
 */
@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController
{

    /**
     * 用户服务
     */
    @Resource
    private IUserService userService;

    /**
     * 图片服务
     */
    private ICgImagesService imagesService;

    /**
     * 注册用户
     *
     * @return 注册结果
     */
    @PostMapping("/enroll")
    public Result<Boolean> enrollUser(
            @Validated @RequestBody EnrollUserDTO enrollUserDTO)
    {
        return userService.enrollUser(enrollUserDTO);
    }

    /**
     * 用户登录
     *
     * @param loginUserDTO 登录信息
     *                     &#064;Param  request 请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<LoginUserVO> loginUser(@Validated @RequestBody LoginUserDTO loginUserDTO, HttpServletRequest request)
    {
        return userService.loginUser(loginUserDTO, request);
    }


    /**
     * 登出
     *
     * @return 登出结果
     */
    @GetMapping("/loginOut")
    public Result<Boolean> loginOut()
    {
        return userService.loginOut();
    }

    /**
     * 上传文件
     *
     * @param request web请求
     */
    @PostMapping("/uploadFile")
    public void uploadFile(HttpServletRequest request)
    {
        try (InputStream inputStream = request.getInputStream())
        {

        } catch (IOException e)
        {
            log.error("上传文件失败:{}", e.getMessage());
        }
    }
}
