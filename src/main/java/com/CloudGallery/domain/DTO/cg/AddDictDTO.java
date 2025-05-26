package com.CloudGallery.domain.DTO.cg;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AddDictDTO {

    /**
     * 字典类型编码（唯一，如"gender"）
     */
    @NotNull(message = "字典类型编码不能为空")
    private String typeCode;

    /**
     * 字典类型名称（如"性别"）
     */
    @NotNull(message = "字典类型名称不能为空")
    private String typeName;

    /**
     * 字典类型描述（可选）
     */
    private String description;

}
