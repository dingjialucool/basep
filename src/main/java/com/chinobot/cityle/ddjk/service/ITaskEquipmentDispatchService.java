package com.chinobot.cityle.ddjk.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.ddjk.entity.TaskEquipmentDispatch;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 任务设备调度表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-03-19
 */
public interface ITaskEquipmentDispatchService extends IBaseService<TaskEquipmentDispatch> {

    public void doDispatch(String taskId,String[] uavValue);

    public void doCancel(String taskId);
    
    /**
     * 根据设备id获取任务场景
     * @param param
     * @return
     * @author shizt  
     * @date 2019年5月23日
     * @company chinobot
     */
    IPage getUavTaskScene(Page page, Map<String, Object> param);
}
