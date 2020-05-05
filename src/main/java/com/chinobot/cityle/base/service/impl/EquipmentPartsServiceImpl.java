package com.chinobot.cityle.base.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.cityle.base.entity.EquipmentParts;
import com.chinobot.cityle.base.mapper.EquipmentPartsMapper;
import com.chinobot.cityle.base.service.IEquipmentPartsService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 设备部件关联 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-04-04
 */
@Service
public class EquipmentPartsServiceImpl extends BaseService<EquipmentPartsMapper, EquipmentParts> implements IEquipmentPartsService {

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Result editEquipmentParts(List<EquipmentParts> equipmentParts) {
		String equipmentId = equipmentParts.get(0).getEquipmentId();
		QueryWrapper<EquipmentParts> queryWrapper = new QueryWrapper<EquipmentParts>();
		queryWrapper.eq("equipment_id", equipmentId);
		// 删除设备所有部件
		this.remove(queryWrapper);
		// 新增部件关联
		if(CommonUtils.objNotEmpty(equipmentParts.get(0).getPartsId())) {
			this.saveOrUpdateBatch(equipmentParts);
		}
		
		return ResultFactory.success();
	}

}
