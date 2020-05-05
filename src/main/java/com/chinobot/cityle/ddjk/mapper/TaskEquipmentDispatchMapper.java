package com.chinobot.cityle.ddjk.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.ddjk.entity.TaskEquipmentDispatch;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 任务设备调度表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-03-19
 */
public interface TaskEquipmentDispatchMapper extends IBaseMapper<TaskEquipmentDispatch> {

    public void deleteUavDispatch(@Param("taskId")String taskId);
    
    /**
     * 根据设备id获取任务场景
     * @param param
     * @return
     * @author shizt  
     * @date 2019年5月23日
     * @company chinobot
     */
    IPage getUavTaskScene(Page page, @Param("p") Map<String, Object> param);

}
