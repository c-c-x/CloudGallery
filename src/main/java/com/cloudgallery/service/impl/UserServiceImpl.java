package com.cloudgallery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudgallery.common.exception.CgServiceException;
import com.cloudgallery.common.response.Result;
import com.cloudgallery.common.utils.DesensitizationUtils;
import com.cloudgallery.common.utils.SnowFlakeUtils;
import com.cloudgallery.domain.User;
import com.cloudgallery.mapper.UserMapper;
import com.cloudgallery.pojo.DTO.EnrollUserDTO;
import com.cloudgallery.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 注册用户
     * @param enrollUserDTO 注册信息
     * @return
     */
    @Override
    public Result<Boolean> enrollUser(EnrollUserDTO enrollUserDTO) {
        // 构建用户对象
        try {
            User user = User.builder()
                    .id(SnowFlakeUtils.nextId())
                    .userName(enrollUserDTO.getUserName())
                    .nickName(enrollUserDTO.getNickName())
                    .password(DesensitizationUtils.encrypt(enrollUserDTO.getPassword()))
                    .build();
            // 判断用户名或昵称是否存在
            User one = this.getOne(new LambdaQueryWrapper<User>()
                    .eq(user.getUserName() != null, User::getUserName, user.getUserName()));
            if (one != null) {
                throw new CgServiceException("用户名已存在");
            }
            //  保存用户
            return this.save(user) ? Result.success(true) : Result.fail("失败");
        }catch (Exception e){
            log.error("注册用户异常--->{}",e.getMessage());
            throw new CgServiceException("注册用户失败"+e.getMessage(), 500);
        }
    }
}
