package com.CloudGallery.service;

import com.CloudGallery.common.response.Result;
import com.CloudGallery.domain.PO.CgUserLv;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户权益等级关联表 服务类
 * </p>
 *
 * @author author
 * @since 2025-05-27
 */
public interface ICgUserLvService extends IService<CgUserLv> {

    /**
     * 绑定等级
     * @param userIds 用户id
     * @param lv 等级
     * @return 绑定结果
     */
    Result<Boolean> bindLv(List<Long> userIds, Long lv);
}
