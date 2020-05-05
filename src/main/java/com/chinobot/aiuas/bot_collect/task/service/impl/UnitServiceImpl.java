package com.chinobot.aiuas.bot_collect.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chinobot.aiuas.bot_collect.task.entity.Unit;
import com.chinobot.aiuas.bot_collect.task.entity.dto.UnitDto;
import com.chinobot.aiuas.bot_collect.task.mapper.UnitMapper;
import com.chinobot.aiuas.bot_collect.task.service.IUnitService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 防治救单位 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Service
public class UnitServiceImpl extends BaseService<UnitMapper, Unit> implements IUnitService {

    @Autowired
    private UnitMapper unitMapper;

    @Override
    public List<UnitDto> getUtilList(Map<String, Object> param) {
        return unitMapper.getUnitList(param);
    }

    @Override
    public Result delUnitByTaskUuid(String taskUuid) {
        Unit unit = new Unit();
        unit.setIsDeleted(GlobalConstant.IS_DELETED_YES);
        UpdateWrapper<Unit> unitWrapper = new UpdateWrapper<>();
        unitWrapper.eq("task_uuid", taskUuid);

        return ResultFactory.success(update(unit, unitWrapper));
    }
}
