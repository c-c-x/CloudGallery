package com.CloudGallery.service.impl;

import com.CloudGallery.domain.PO.UserRole;
import com.CloudGallery.mapper.SysUserRoleMapper;
import com.CloudGallery.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色关联表 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-05-26
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper,UserRole> implements IUserRoleService {

}
