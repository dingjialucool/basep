package com.chinobot.plep.home.routedd.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.routedd.entity.DispatchDetail;
import com.chinobot.plep.home.routedd.mapper.DispatchDetailMapper;
import com.chinobot.plep.home.routedd.service.IDispatchDetailService;

/**
 * <p>
 * 调度明细 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-07-24
 */
@Service
public class DispatchDetailServiceImpl extends BaseService<DispatchDetailMapper, DispatchDetail> implements IDispatchDetailService {

	@Autowired
	private DispatchDetailMapper dispatchDetailMapper;
	
	@Override
	public Map getDispatchUav(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return dispatchDetailMapper.getDispatchUav(param);
	}

}
