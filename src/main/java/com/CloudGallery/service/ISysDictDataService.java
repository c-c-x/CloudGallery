package com.CloudGallery.service;

import com.CloudGallery.common.response.Result;
import com.CloudGallery.domain.DTO.cg.AddDictDataDTO;
import com.CloudGallery.domain.PO.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统字典数据表 服务类
 * </p>
 *
 * @author author
 * @since 2025-05-26
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 添加字典数据
     * @param addDictDataDTO 请求参数
     * @return 添加结果
     */
    Result<Boolean> addDictData(AddDictDataDTO addDictDataDTO);
}
