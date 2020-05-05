package com.chinobot.aiuas.bot_collect.task.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.Task;
import com.chinobot.aiuas.bot_collect.task.entity.dto.TaskSceneDto;
import com.chinobot.aiuas.bot_collect.task.entity.vo.TaskInfoVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.TaskListVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查任务 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface TaskMapper extends IBaseMapper<Task> {
    /*
     * 采查任务 列表
     * @Param: [page, param]
     * @Return: com.baomidou.mybatisplus.core.metadata.IPage<com.chinobot.aiuas.bot_collect.task.entity.vo.TaskListVo>
     * @Author: shizt
     * @Date: 2020/1/13 9:57
     */
    IPage<TaskListVo> getTaskList(Page page, @Param("p") Map<String, Object> param);

    /**
     * 采查任务 详细
     * @Param: [uuid]
     * @Return: com.chinobot.aiuas.bot_collect.task.entity.vo.TaskInfoVo
     * @Author: shizt
     * @Date: 2020/1/13 17:55
     */
    TaskInfoVo getTaskById(@Param("uuid") String uuid);

    /**
     * 根据场景主键获取采查任务(领域场景主键名称)
     * @Param: [sceneUuid]
     * @Return: com.chinobot.aiuas.bot_collect.task.entity.vo.TaskInfoVo
     * @Author: shizt
     * @Date: 2020/1/13 18:10
     */
    TaskInfoVo getDomainSceneBySceneId(@Param("sceneUuid") String sceneUuid);

    /**
     * 根据策略id和对象id获取任务场景
     * @Author: shizt
     * @Date: 2020/2/24 14:44
     */
    List<TaskSceneDto> getTaskSceneByStrategyIdAndInfoId(@Param("strategyId")String strategyId, @Param("infoId")String infoId);
}
