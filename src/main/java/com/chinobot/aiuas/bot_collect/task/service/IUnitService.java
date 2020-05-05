package com.chinobot.aiuas.bot_collect.task.service;

import com.chinobot.aiuas.bot_collect.task.entity.Unit;
import com.chinobot.aiuas.bot_collect.task.entity.dto.UnitDto;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 防治救单位 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface IUnitService extends IBaseService<Unit> {
    /**
     * 防治救单位 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.task.entity.dto.UnitDto>
     * @Author: shizt
     * @Date: 2020/1/15 9:25
     */
    List<UnitDto> getUtilList(Map<String, Object> param);

    /**
     * 根据任务主键删除防治救单位
     * @Param: [taskUuid]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/15 15:47
     */
    Result delUnitByTaskUuid(String taskUuid);
}
