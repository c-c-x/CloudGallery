package com.CloudGallery.mapper;

import com.CloudGallery.domain.PO.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2025-05-26
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据角色编码获取角色id
     * @param roleCode 角色编码
     * @return 角色id
     */
    @Select("select id from sys_role where role_code = #{roleCode}")
    long getRoleId(String roleCode);
}
