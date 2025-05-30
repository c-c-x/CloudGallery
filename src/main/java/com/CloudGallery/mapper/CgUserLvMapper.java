package com.CloudGallery.mapper;

import com.CloudGallery.domain.PO.CgUserLv;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户权益等级关联表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2025-05-27
 */
public interface CgUserLvMapper extends BaseMapper<CgUserLv> {

    /**
     * 批量绑定用户等级
     * @param cgUserLvs 用户等级列表
     * @return 绑定结果
     */
    boolean bindLv(List<CgUserLv> cgUserLvs);
}
