package com.cloudgallery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudgallery.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
