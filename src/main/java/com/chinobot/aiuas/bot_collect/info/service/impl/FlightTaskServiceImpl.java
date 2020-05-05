package com.chinobot.aiuas.bot_collect.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chinobot.aiuas.bot_collect.info.entity.FlightTask;
import com.chinobot.aiuas.bot_collect.info.entity.vo.FlightTaskListVo;
import com.chinobot.aiuas.bot_collect.info.mapper.FlightTaskMapper;
import com.chinobot.aiuas.bot_collect.info.service.IFlightTaskService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 采查策略任务关联表 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2020-02-24
 */
@Service
public class FlightTaskServiceImpl extends BaseService<FlightTaskMapper, FlightTask> implements IFlightTaskService {

    @Override
    public void addFlightTaskList(FlightTaskListVo flightTaskListVo) {
        String collectId = flightTaskListVo.getCollectId();
        List<String> flightIds = flightTaskListVo.getFlightIds();
        List<String> taskIds = flightTaskListVo.getTaskIds();
        // 删除
        UpdateWrapper<FlightTask> flightTaskUpdateWrapper = new UpdateWrapper<>();
        flightTaskUpdateWrapper.lambda()
                .eq(FlightTask::getCollectUuid, collectId);
        this.remove(flightTaskUpdateWrapper);

        // 新增
        List<FlightTask> flightTasks = new ArrayList<>();
        for(String t: taskIds){
            for (String f: flightIds) {
                FlightTask flightTask = new FlightTask();
                flightTask.setTaskUuid(t)
                        .setCollectUuid(collectId)
                        .setFlightUuid(f);
                flightTasks.add(flightTask);
            }
        }
        this.saveBatch(flightTasks);
    }
}
