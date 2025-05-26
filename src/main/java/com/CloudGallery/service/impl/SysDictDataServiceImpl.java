package com.CloudGallery.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.CloudGallery.common.exception.CgServiceException;
import com.CloudGallery.common.response.Result;
import com.CloudGallery.common.utils.UserUtils;
import com.CloudGallery.constants.StatusConstants;
import com.CloudGallery.domain.DTO.cg.AddDictDataDTO;
import com.CloudGallery.domain.PO.SysDict;
import com.CloudGallery.domain.PO.SysDictData;
import com.CloudGallery.mapper.SysDictDataMapper;
import com.CloudGallery.service.ISysDictDataService;
import com.CloudGallery.service.ISysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统字典数据表 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-05-26
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    /**
     * 字典服务
     */
    @Resource
    private ISysDictService dictService;

    /**
     * 添加字典数据
     * @param addDictDataDTO 请求参数
     * @return 添加结果
     */
    @Override
    public Result<Boolean> addDictData(AddDictDataDTO addDictDataDTO) {
        //1.查询字典分类数据是否存在
        SysDict dict = dictService.getOne(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getTypeCode, addDictDataDTO.getDictType()));
        if (ObjectUtil.isEmpty(dict)){
            throw new CgServiceException("字典分类不存在");
        }
        //2.查询字典数据是否存在
        SysDictData dictData = this.getOne(new LambdaQueryWrapper<SysDictData>()
                .eq(SysDictData::getDictType, addDictDataDTO.getDictType())
                .eq(SysDictData::getValue, addDictDataDTO.getValue()));
        if (ObjectUtil.isNotEmpty(dictData)){
            throw new CgServiceException("字典数据已存在");
        }
        return this.save(SysDictData.builder()
                .dictType(addDictDataDTO.getDictType())
                .label(addDictDataDTO.getLabel())
                .value(addDictDataDTO.getValue())
                .sort(addDictDataDTO.getSort())
                .status(StatusConstants.YES_STATUS)
                .createUser(UserUtils.getUserId())
                .build()) ? Result.success() : Result.fail();
    }
}
