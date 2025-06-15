package com.CloudGallery.mapper;

import com.CloudGallery.domain.PO.RecycleBinTasks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 * 回收站清理任务表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2025-06-15
 */
public interface RecycleBinTasksMapper extends BaseMapper<RecycleBinTasks> {
    /**
     * 批量新增定时任务
     *
     * @param recycleBinTasksList
     */
    boolean addRecycleBinTasks(@Param("recycleBinTasksList") List<RecycleBinTasks> recycleBinTasksList);

}
