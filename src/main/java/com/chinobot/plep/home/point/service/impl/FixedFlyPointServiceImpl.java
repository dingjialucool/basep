package com.chinobot.plep.home.point.service.impl;

import com.chinobot.plep.home.point.entity.FixedFlyPoint;
import com.chinobot.plep.home.point.mapper.FixedFlyPointMapper;
import com.chinobot.plep.home.point.service.IFixedFlyPointService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 航点表 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-11-14
 */
@Service
public class FixedFlyPointServiceImpl extends BaseService<FixedFlyPointMapper, FixedFlyPoint> implements IFixedFlyPointService {

	@Autowired
	private FixedFlyPointMapper fixedFlyPointMapper;
	
	@Override
	public List<FixedFlyPoint> listFlyPointsByTaskId(String uuid) {
		return fixedFlyPointMapper.listFlyPointsByTaskId(uuid);
	}

}
