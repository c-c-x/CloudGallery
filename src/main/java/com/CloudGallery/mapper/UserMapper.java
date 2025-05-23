package com.CloudGallery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.CloudGallery.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
