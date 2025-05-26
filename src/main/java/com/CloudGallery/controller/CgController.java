package com.CloudGallery.controller;

import com.CloudGallery.annotations.Permission;
import com.CloudGallery.common.enums.PermissionType;
import com.CloudGallery.common.response.Result;
import com.CloudGallery.domain.DTO.cg.AddDictDTO;
import com.CloudGallery.domain.DTO.cg.AddDictDataDTO;
import com.CloudGallery.service.ISysDictDataService;
import com.CloudGallery.service.ISysDictService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统控制器
 */
@RestController
@RequestMapping("/cg")
public class CgController {

    /**
     * 字典服务
     */
    @Resource
    private ISysDictService dictService;

    /**
     * 字典数据服务
      */
    @Resource
    private ISysDictDataService dictDataService;
    /**
     * 添加字典类型
     * @param addDictDTO  请求参数
     * @return 添加结果
     */
    @PostMapping("/addDict")
    @Permission(PermissionType.ADMIN)
    public Result<Boolean> addDict(@Validated @RequestBody AddDictDTO addDictDTO){
        return dictService.addDict(addDictDTO);
    }


    /**
     * 添加字典数据
     * @param addDictDataDTO 请求参数
     * @return 添加结果
     */
    @PostMapping("/addDictData")
    @Permission(PermissionType.ADMIN)
    public Result<Boolean> addDictData(@Validated @RequestBody AddDictDataDTO addDictDataDTO){
        return dictDataService.addDictData(addDictDataDTO);
    }
}
