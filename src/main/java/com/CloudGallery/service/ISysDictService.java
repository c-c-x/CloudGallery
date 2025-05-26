package com.CloudGallery.service;

import com.CloudGallery.common.response.Result;
import com.CloudGallery.domain.DTO.cg.AddDictDTO;
import com.CloudGallery.domain.PO.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统字典分类表 服务类
 * </p>
 *
 * @author author
 * @since 2025-05-26
 */
public interface ISysDictService extends IService<SysDict> {


    /**
     * 添加字典类型
     * @param addDictDTO  请求参数
     * @return 添加结果
     */
    Result<Boolean> addDict(AddDictDTO addDictDTO);
}
