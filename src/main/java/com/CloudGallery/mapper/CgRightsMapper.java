package com.CloudGallery.mapper;

import com.CloudGallery.domain.PO.Rights;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 用户权益表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2025-05-21
 */
public interface CgRightsMapper extends BaseMapper<Rights> {

    /**
     * 绑定用户权益
     * @param userIds 用户id
     * @param maxStorageSize  存储空间大小
     * @param maxImageSize 图片大小
     * @return 操作结果
     */
    boolean bindRights(@Param("userIds")List<Long> userIds,
                       @Param("maxStorageSize") long maxStorageSize,
                       @Param("maxImageSize")long maxImageSize,
                       @Param("updateUser")  long updateUser);

    /**
     * 修改用户权益
     * @param userIds 用户id
     * @param status 状态
     */
    boolean updateRights(@Param("userIds")List<Long> userIds, @Param("status") int status);
}
