package com.chinobot.plep.home.routedd.service.impl;

import com.chinobot.plep.home.routedd.entity.DispatchDetailPath;
import com.chinobot.plep.home.routedd.mapper.DispatchDetailPathMapper;
import com.chinobot.plep.home.routedd.mapper.UavDispatchMapper;
import com.chinobot.plep.home.routedd.service.IDispatchDetailPathService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-08-05
 */
@Service
public class DispatchDetailPathServiceImpl extends BaseService<DispatchDetailPathMapper, DispatchDetailPath> implements IDispatchDetailPathService {

	@Autowired
	private DispatchDetailPathMapper dispatchDetailPathMapper;
	@Override
	public List<Map> getPathFlightTotal() {
		// TODO Auto-generated method stub
		return dispatchDetailPathMapper.getPathFlightTotal();
	}

}
