package com.CloudGallery.mapper;

import com.CloudGallery.domain.DTO.UserListDTO;
import com.CloudGallery.domain.DTO.UserPageDTO;
import com.CloudGallery.domain.VO.UserPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.CloudGallery.domain.PO.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * 用户表
 */
@Mapper
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
}
