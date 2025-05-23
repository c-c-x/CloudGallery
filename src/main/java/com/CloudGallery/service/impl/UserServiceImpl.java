package com.CloudGallery.service.impl;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.CloudGallery.common.utils.IpUtils;
import com.CloudGallery.common.utils.JWTUtils;
import com.CloudGallery.constants.RightsLvConstants;
import com.CloudGallery.constants.StatusConstants;
import com.CloudGallery.domain.po.LoginLog;
import com.CloudGallery.domain.po.Rights;
import com.CloudGallery.service.ILoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.CloudGallery.common.exception.CgServiceException;
import com.CloudGallery.common.response.Result;
import com.CloudGallery.common.utils.DesensitizationUtils;
import com.CloudGallery.domain.po.User;
import com.CloudGallery.mapper.UserMapper;
import com.CloudGallery.domain.DTO.EnrollUserDTO;
import com.CloudGallery.domain.DTO.LoginUserDTO;
import com.CloudGallery.service.ICgRightsService;
import com.CloudGallery.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 权益服务
     */
    @Resource
    private ICgRightsService cgRightsService;

    /**
     * 雪花算法生成器
     */
    @Resource
    private SnowflakeGenerator snowflakeGenerator;

    /**
     * redis
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录日志服务
     */
    @Resource
    private ILoginLogService loginLogService;


    /**
     * 注册用户
     *
     * @param enrollUserDTO 注册信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> enrollUser(EnrollUserDTO enrollUserDTO) {
        // 构建用户对象
        try {
            User user = User.builder()
                    .id(snowflakeGenerator.next())
                    .userName(enrollUserDTO.getUserName())
                    .nickName(enrollUserDTO.getNickName())
                    .password(DesensitizationUtils.encrypt(enrollUserDTO.getPassword()))
                    .build();
            // 判断用户名或昵称是否存在
            User one = this.getOne(new LambdaQueryWrapper<User>()
                    .eq(user.getUserName() != null, User::getUserName, user.getUserName())
                    .eq(User::getStatus, StatusConstants.YES_STATUS));
            if (one != null) {
                throw new CgServiceException("用户名已存在");
            }
            //  保存用户
            this.save(user);
            // 新增权益表,新用户为一级
            Rights isRights = cgRightsService.getOne(new LambdaQueryWrapper<Rights>()
                    .eq(Rights::getUserId, user.getId())
                    .eq(Rights::getStatus, StatusConstants.YES_STATUS));
            if (isRights != null) {
                throw new CgServiceException("图库已存在，请联系管理员");
            }
            Rights rights = Rights.builder()
                    .userId(user.getId())
                    .status(StatusConstants.YES_STATUS)
                    .maxStorage(RightsLvConstants.LV_ONE_MAX_STORAGE_SIZE)
                    .maxImageSize(RightsLvConstants.LV_ONE_MAX_IMAGE_SIZE)
                    .build();
            cgRightsService.save(rights);
            return Result.success();
        } catch (Exception e) {
            log.error("注册用户异常------------------>{}", e.getMessage());
            throw new CgServiceException("注册用户失败" + e.getMessage(), 500);
        }
    }

    /**
     * 用户登录
     *
     * @param loginUserDTO 登录信息
     * @param request      请求
     * @return 登录结果
     */
    @Override
    public Result<String> loginUser(LoginUserDTO loginUserDTO, HttpServletRequest request) {
        try {
            //校验登录信息
            User user = this.getOne(new LambdaQueryWrapper<User>()
                    .eq(loginUserDTO.getUserName() != null, User::getUserName, loginUserDTO.getUserName())
                    .eq(User::getStatus, StatusConstants.YES_STATUS));
            String password = DesensitizationUtils.decrypt(user.getPassword());
            if (!password.equals(loginUserDTO.getPassword())) {
                throw new CgServiceException("用户名或密码错误");
            }

            String ip = IpUtils.getIpAddr(request);
            String cityInfo = null;
            try {
                cityInfo = IpUtils.getCityInfo(ip);
            } catch (Exception e) {
                log.error("获取ip归属地信息失败！");
            }
            //保存登录日志
            LoginLog loginLog = LoginLog.builder()
                    .userId(user.getId())
                    .userName(user.getUserName())
                    .userIp(ip)
                    .ipAttribution(cityInfo)
                    .build();
            loginLogService.save(loginLog);
            return Result.success(JWTUtils.createJWT(user));
        } catch (Exception e) {
            throw new CgServiceException("登录失败," + e.getMessage());
        }
    }
}
