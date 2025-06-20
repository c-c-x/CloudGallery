package com.CloudGallery.mapper;

import com.CloudGallery.domain.DTO.admin.UserListDTO;
import com.CloudGallery.domain.DTO.admin.UserPageDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.CloudGallery.domain.PO.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * 用户表
 */
public interface UserMapper extends BaseMapper<User> {

    /**
    获取用户角色
     */
    @Select("""
            SELECT
                role_code
            FROM
                sys_role AS f,
                (SELECT role_id AS r FROM sys_user_role WHERE user_id = #{userId} )
                AS ri
            WHERE
                ri.r = f.id
            """)
    Set<String> getRoles(@Param("userId") Long userId);

    /**
     * 获取用户列表
     * @param userPageDTO
     * @return 用户列表
     */
    List<UserListDTO> getUserList(UserPageDTO userPageDTO);

    /**
     * 判断用户是否存在
     * @param id 用户id
     * @return true/false
     */
    @Select("select id from cg_user where id = #{id} and status != 0")
    boolean isInUser(@Param("id") Long id);


    /**
     * 批量删除用户
     * @param userIds 用户id
     * @return  删除结果
     */
    boolean removeUsers(@Param("userIds") List<Long> userIds);
}
