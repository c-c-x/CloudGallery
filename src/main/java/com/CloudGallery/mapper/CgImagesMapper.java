package com.CloudGallery.mapper;

import com.CloudGallery.domain.PO.CgImages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 图片表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2025-06-14
 */
public interface CgImagesMapper extends BaseMapper<CgImages> {

    /**
     * 批量删除图片
     * @param images
     * @return
     */
    boolean removeImages(@Param("images") List<CgImages> images, @Param("deleteTime") LocalDateTime deleteTime);
}
