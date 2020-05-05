package com.chinobot.cityle.ddjk.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinobot.cityle.ddjk.entity.RobotGatherData;
import com.chinobot.cityle.ddjk.mapper.RobotGatherDataMapper;
import com.chinobot.cityle.ddjk.service.IRobotGatherDataService;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 设备采集场景数据 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-04-15
 */
@Service
public class RobotGatherDataServiceImpl extends BaseService<RobotGatherDataMapper, RobotGatherData> implements IRobotGatherDataService {

	@Autowired
	private RobotGatherDataMapper robotGatherDataMapper;

	@Override
	public List<Map> getWarningList(Map<String, Object> param) {
		
		return robotGatherDataMapper.getWarningList(param);
	}

	@Override
	public Map getThingOri() {
		return robotGatherDataMapper.getThingOri();
	}

	@Override
	public Map deptData() {
		return robotGatherDataMapper.deptData();
	}

	@Override
	public Map warnData() {
		return robotGatherDataMapper.warnData();
	}

	@Override
	public Map topData() {
		return robotGatherDataMapper.topData();
	}
}
