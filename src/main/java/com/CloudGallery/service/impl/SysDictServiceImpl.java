package com.CloudGallery.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.CloudGallery.common.exception.CgServiceException;
import com.CloudGallery.common.response.Result;
import com.CloudGallery.common.utils.UserUtils;
import com.CloudGallery.constants.StatusConstants;
import com.CloudGallery.domain.DTO.cg.AddDictDTO;
import com.CloudGallery.domain.PO.SysDict;
import com.CloudGallery.mapper.SysDictMapper;
import com.CloudGallery.service.ISysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统字典分类表 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-05-26
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {


    /**
     * 添加字典类型
     * @param addDictDTO  请求参数
     * @return 添加结果
     */
    @Override
    public Result<Boolean> addDict(AddDictDTO addDictDTO) {
        //1.查询字典分类是否存在
        SysDict oldDict = this.getOne(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getTypeCode, addDictDTO.getTypeCode()));
        if (ObjectUtil.isNotEmpty(oldDict)){
            throw new CgServiceException("字典分类已存在");
        }

        //2.添加字典分类
        return this.save(SysDict.builder()
                .typeCode(addDictDTO.getTypeCode())
                .typeName(addDictDTO.getTypeName())
                .description(addDictDTO.getDescription())
                .status(StatusConstants.YES_STATUS)
                .createUser(UserUtils.getUserId())
                .build()) ? Result.success() : Result.fail();
    }
}
