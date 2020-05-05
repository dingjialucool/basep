package com.chinobot.plep.home.plan.service.impl;

import com.chinobot.plep.home.plan.entity.FlyPath;
import com.chinobot.plep.home.plan.entity.Range;
import com.chinobot.plep.home.plan.mapper.FlyPathMapper;
import com.chinobot.plep.home.plan.service.IFlyPathService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-06-18
 */
@Service
public class FlyPathServiceImpl extends BaseService<FlyPathMapper, FlyPath> implements IFlyPathService {
	
	@Autowired
	private FlyPathMapper flyPathMapper;
	
	@Override
	public Range getRangeByFlyPath(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return flyPathMapper.getRangeByFlyPath(param);
	}

}
