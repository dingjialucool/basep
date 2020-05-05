package com.chinobot.cityle.ddjk.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.ddjk.constant.DdjkConstant;
import com.chinobot.cityle.ddjk.entity.TaskEquipmentDispatch;
import com.chinobot.cityle.ddjk.entity.TaskXc;
import com.chinobot.cityle.ddjk.mapper.TaskEquipmentDispatchMapper;
import com.chinobot.cityle.ddjk.service.ITaskEquipmentDispatchService;
import com.chinobot.cityle.ddjk.service.ITaskXcService;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 任务设备调度表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-03-19
 */
@Service
public class TaskEquipmentDispatchServiceImpl extends BaseService<TaskEquipmentDispatchMapper, TaskEquipmentDispatch> implements ITaskEquipmentDispatchService {
    @Autowired
    private TaskEquipmentDispatchMapper taskEquipmentDispatchMapper;
    @Autowired
    private ITaskXcService taskXcService;
    @Override
    public void doDispatch(String taskId,String[] uavValue){
        //先删除历史记录
        taskEquipmentDispatchMapper.deleteUavDispatch(taskId);
        List<TaskEquipmentDispatch> list = new ArrayList<TaskEquipmentDispatch>();
        TaskXc task = taskXcService.getById(taskId);
        //组装对象
        if(uavValue.length>0 && !uavValue[0].equals("")){
            for (String id:uavValue){
                TaskEquipmentDispatch dispatch = new TaskEquipmentDispatch();
                dispatch.setTaskId(taskId);
                dispatch.setEquipmentId(id);
                dispatch.setDuration(DdjkConstant.TASK_DURATION);//暂时默认30分钟
                list.add(dispatch);
            }
            this.saveBatch(list);
            task.setTaskStatus("2");
        }else{
            task.setTaskStatus("1");
        }

        //更新任务状态
        taskXcService.updateById(task);
    }
    @Override
    public void doCancel(String taskId){
        //先删除历史记录
        taskEquipmentDispatchMapper.deleteUavDispatch(taskId);
        //更新任务状态
        TaskXc task = taskXcService.getById(taskId);
        task.setTaskStatus("5");
        taskXcService.updateById(task);
    }
	@Override
	public IPage getUavTaskScene(Page page, Map<String, Object> param) {
		
		return taskEquipmentDispatchMapper.getUavTaskScene(page, param);
	}
}
