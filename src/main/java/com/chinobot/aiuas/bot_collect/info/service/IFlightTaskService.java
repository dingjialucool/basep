package com.chinobot.aiuas.bot_collect.info.service;

import com.chinobot.aiuas.bot_collect.info.entity.FlightTask;
import com.chinobot.aiuas.bot_collect.info.entity.vo.FlightTaskListVo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 采查策略任务关联表 服务类
 * </p>
 *
 * @author shizt
 * @since 2020-02-24
 */
public interface IFlightTaskService extends IBaseService<FlightTask> {
    /**
     * 新增航班采查任务关联
     * @Author: shizt
     * @Date: 2020/2/24 17:49
     */
    void addFlightTaskList(FlightTaskListVo flightTaskListVo);
}
