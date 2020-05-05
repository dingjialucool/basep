package com.chinobot.aiuas.bot_collect.task.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.Task;
import com.chinobot.aiuas.bot_collect.task.entity.vo.TaskInfoVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.TaskListVo;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查任务 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface ITaskService extends IBaseService<Task> {

    /*
     * 采查任务 列表
     * @Param: [page, param]
     * @Return: com.baomidou.mybatisplus.core.metadata.IPage<com.chinobot.aiuas.bot_collect.task.entity.vo.TaskListVo>
     * @Author: shizt
     * @Date: 2020/1/13 9:57
     */
    IPage<TaskListVo> getTaskList(Page page, Map<String, Object> param);

    /**
     * 采查任务 详细
     * @Param: [uuid]
     * @Return: com.chinobot.aiuas.bot_collect.task.entity.Task
     * @Author: shizt
     * @Date: 2020/1/13 16:49
     */
    TaskInfoVo getTaskInfo(Map<String, Object> param);

    /**
     * 采查任务 编辑
     * @Param: [taskInfoVo]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/14 15:22
     */
    Result editTask(TaskInfoVo taskInfoVo);

    /**
     * 根据场景主键删除采查任务
     * @Param: [sceneUuid]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/13 16:08
     */
    Result delTaskBySceneUuid(String sceneUuid);

}
