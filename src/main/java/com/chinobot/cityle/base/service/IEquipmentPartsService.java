package com.chinobot.cityle.base.service;

import java.util.List;

import com.chinobot.cityle.base.entity.EquipmentParts;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 设备部件关联 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-04-04
 */
public interface IEquipmentPartsService extends IBaseService<EquipmentParts> {

	/**
	 * 编辑设备部件关联
	 * @param equipmentParts
	 * @return
	 * @author shizt  
	 * @date 2019年4月4日
	 * @company chinobot
	 */
	Result editEquipmentParts(List<EquipmentParts> equipmentParts);
}
