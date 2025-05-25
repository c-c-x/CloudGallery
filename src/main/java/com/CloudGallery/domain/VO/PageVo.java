package com.CloudGallery.domain.VO;

import lombok.Data;

@Data
public class PageVo {
    /**
    * 当前页码
     */
    private Long pageNum;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long totalPage;

}
