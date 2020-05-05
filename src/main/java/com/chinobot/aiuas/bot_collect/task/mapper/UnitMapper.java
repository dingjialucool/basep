package com.chinobot.aiuas.bot_collect.task.mapper;

import com.chinobot.aiuas.bot_collect.task.entity.Unit;
import com.chinobot.aiuas.bot_collect.task.entity.dto.UnitDto;
import com.chinobot.aiuas.bot_collect.task.entity.vo.UnitVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 防治救单位 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface UnitMapper extends IBaseMapper<Unit> {

    /**
     * 防治救单位 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.task.entity.dto.UnitDto>
     * @Author: shizt
     * @Date: 2020/1/15 9:25
     */
    List<UnitDto> getUnitList(@Param("p") Map<String, Object> param);
}
