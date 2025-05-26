package com.CloudGallery.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.ObjectUtil;
import com.CloudGallery.common.utils.*;
import com.CloudGallery.constants.*;
import com.CloudGallery.domain.DTO.*;
import com.CloudGallery.domain.PO.UserRole;
import com.CloudGallery.domain.VO.ByIdUserVO;
import com.CloudGallery.domain.VO.LoginUserVO;
import com.CloudGallery.domain.PO.LoginLog;
import com.CloudGallery.domain.PO.Rights;
import com.CloudGallery.domain.VO.UserPageVO;
import com.CloudGallery.mapper.SysRoleMapper;
import com.CloudGallery.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.CloudGallery.common.exception.CgServiceException;
import com.CloudGallery.common.response.Result;
import com.CloudGallery.domain.PO.User;
import com.CloudGallery.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
     * 用户mapper
     */
    @Resource
    private UserMapper userMapper;

    /**
     * 角色服务
     */
    @Resource
    private ISysRoleService roleService;

    /**
     * 用户角色服务
     */
    @Resource
    private IUserRoleService userRoleService;

    /**
     * 角色mapper
     */
    @Resource
    private SysRoleMapper roleMapper;
    /**
     * 注册用户
     *
     * @param enrollUserDTO 注册信息
     * @return 注册结果
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
    @Transactional(rollbackFor = Exception.class)
    public Result<LoginUserVO> loginUser(LoginUserDTO loginUserDTO, HttpServletRequest request) {
        //校验登录信息
        User user = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, loginUserDTO.getUserName())
                .eq(User::getStatus, StatusConstants.YES_STATUS));
        if (user == null){
            throw new CgServiceException("用户名或密码错误");
        }

        //验证登录权限是否足够
        if (!loginUserDTO.getPermissions().equals(LoginConstants.ADMIN) &&
                !loginUserDTO.getPermissions().equals(LoginConstants.USER)){
            throw new CgServiceException("登录失败，权限不足");
        }
        Set<String> roles = userMapper.getRoles(user.getId());
        if (loginUserDTO.getPermissions().equals(LoginConstants.ADMIN)) {
            if (!roles.contains(RoleConstants.ADMIN )|| StringUtils.isEmpty(loginUserDTO.getPermissions())) {
                throw new CgServiceException("登录失败，权限不足");
            }else if (!roles.contains(RoleConstants.CG_USER)){
                throw new CgServiceException("登录失败，权限不足");
            }
        }

        try {
            String password = DesensitizationUtils.decrypt(user.getPassword());
            if (!password.equals(loginUserDTO.getPassword())) {
                throw new CgServiceException("用户名或密码错误");
            }
        } catch (Exception e) {
            throw new CgServiceException("登录失败," + e.getMessage());
        }

        //获取用户登录日志
        String ip = IpUtils.getIpAddr(request);
        String cityInfo = null;
        try {
            cityInfo = IpUtils.getCityInfo(ip);
        } catch (Exception e) {
            log.error("获取ip归属地信息失败！");
        }
        LoginLog loginLog = LoginLog.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userIp(ip)
                .ipAttribution(cityInfo)
                .build();
        loginLogService.save(loginLog);

        //获得JWT和REFRESH_TOKEN
        String jwt = JWTUtils.createJWT(user);
        String REFRESH_TOKEN = UUIDUtils.generateUUIDWithoutHyphens();
        //保存UUID到redis设置刷新token
        stringRedisTemplate.opsForValue().set(RedisConstants.USER_TOKEN_KEY + user.getId(), REFRESH_TOKEN, RedisConstants.TOKEN_REFRESH_TIME, TimeUnit.DAYS);
        LoginUserVO vo = LoginUserVO.builder()
                .JWT(jwt)
                .REFRESH_TOKEN(REFRESH_TOKEN)
                .build();
        return Result.success(vo);
    }


    /**
     * 获取用户列表
     * @param userPageDTO 用户分页信息
     * @return 用户列表
     */
    @Override
    public Result<UserPageVO> getUserList(UserPageDTO userPageDTO) {
        //获取用户列表
        PageHelper.startPage(userPageDTO.getPageNum(), userPageDTO.getPageSize());
        List<UserListDTO> userPageVOList = userMapper.getUserList(userPageDTO);
        PageInfo pageInfo = new PageInfo(userPageVOList);
        UserPageVO vo = new UserPageVO();
        BeanUtil.copyProperties(pageInfo,vo);
        return Result.success(vo);
    }


    /**
     * 登出
     * @return 登出结果
     */
    @Override
    public Result<Boolean> loginOut() {
        Long userId = UserUtils.getUserId();
        Boolean delete = stringRedisTemplate.delete(RedisConstants.USER_TOKEN_KEY + userId);
        if (delete){
            return Result.success();
        }
        throw new CgServiceException("身份已过期");
    }


    /**
     * 修改用户信息
     * @param updateUserDTO 修改用户信息
     * @return 修改结果
     */
    @Override
    public Result<Boolean> updateUser(UpdateUserDTO updateUserDTO) {
        if (ObjectUtil.isEmpty(updateUserDTO)){
            throw new CgServiceException("修改信息不能为空");
        }

        try {
            User user = User.builder()
                    .id(updateUserDTO.getId())
                    .userName(updateUserDTO.getUserName())
                    .password(DesensitizationUtils.decrypt(updateUserDTO.getPassword()))
                    .nickName(updateUserDTO.getNickName())
                    .phone(updateUserDTO.getPhone())
                    .email(updateUserDTO.getEmail())
                    .gender(updateUserDTO.getGender())
                    .idNumber(updateUserDTO.getIdNumber())
                    .status(updateUserDTO.getStatus())
                    .build();
            return userMapper.updateById(user) != 0 ? Result.success() : Result.fail();
        }catch (Exception e){
            throw new CgServiceException("修改用户信息失败" + e.getMessage());
        }
    }


    /**
     * 获取指定用户信息
     * @param id 用户id
     * @return  用户信息
     */
    @Override
    public Result<ByIdUserVO> getUserById(Long id) {
        if (ObjectUtil.isEmpty(id)){
            throw new CgServiceException("用户id不能为空");
        }

        User user = this.getById(id);
        if (ObjectUtil.isEmpty(user)){
            throw new CgServiceException("用户不存在");
        }

        //脱敏放回视图
        ByIdUserVO vo = ByIdUserVO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .nickName(user.getNickName())
                .phone(UserUtils.maskPhone(user.getPhone()))
                .email(UserUtils.maskEmail(user.getEmail()))
                .gender(user.getGender())
                .idNumber(UserUtils.maskIdNumber(user.getIdNumber()))
                .status(user.getStatus())
                .build();
        return Result.success(vo);
    }


    /**
     * 设置管理员
     * @param id 用户id
     * @return 设置结果
     */
    @Override
    public Result<Boolean> setAdmin(Long id) {
        if (ObjectUtil.isEmpty(id)){
            throw new CgServiceException("用户id不能为空");
        }

        boolean result =  userMapper.isInUser(id);
        if (!result){
            throw new CgServiceException("用户不存在");
        }

        //  获取管理员角色id
        long roleId =  roleMapper.getRoleId(RoleConstants.ADMIN);

        //判断是否已经是管理员

        if (isRole(id,roleId)){
            throw new CgServiceException("用户已经是管理员");
        }

        UserRole userRole = UserRole.builder()
                .userId(id)
                .roleId(roleId)
                .build();

        return userRoleService.save(userRole) ?
                Result.success() : Result.fail();
    }

    /**
     * 移除管理员
     * @param id 用户id
     * @return 移除结果
     */
    @Override
    public Result<Boolean> removeAdmin(Long id) {
        Long userId = UserUtils.getUserId();
        if (userId.equals(id)){
            throw new CgServiceException("不能移除自己");
        }

        //  获取管理员角色id
        long roleId =  roleMapper.getRoleId(RoleConstants.ADMIN);
        UserRole role = userRoleService.getOne(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, id)
                .eq(UserRole::getRoleId, roleId));
        if (ObjectUtil.isEmpty(role)){
           throw new CgServiceException("用户不是管理员");
        }

        return userRoleService.removeById(role.getId()) ? Result.success() : Result.fail();

    }


    /**
     * 判断是否为某个角色
     * @param userId 用户id
     * @param roleId 角色id
     * @return 判断结果
     */
    private boolean isRole(Long userId,Long roleId){
        UserRole role = userRoleService.getOne(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, roleId));
        return ObjectUtil.isNotEmpty(role);

    }
}
