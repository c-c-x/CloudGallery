package com.CloudGallery.domain.DTO.cg;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddDictDataDTO {
    /**
     * 关联的字典类型编码（对应sys_dict.type_code）
     */
    @NotNull(message = "字典类型不能为空")
    private String dictType;

    /**
     * 字典标签（展示值，如"男"）
     */
    @NotNull(message = "字典标签不能为空")
    private String label;

    /**
     * 字典值（存储值，如"1"）
     */
    @NotNull(message = "字典值不能为空")
    private Long value;

    /**
     * 排序（数值越小越靠前）
     */
    private Integer sort;

}
