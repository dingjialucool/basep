package com.chinobot.plep.home.monitor.service.impl;

import com.chinobot.plep.home.monitor.entity.PersonOnline;
import com.chinobot.plep.home.monitor.entity.PersonTrajectory;
import com.chinobot.plep.home.monitor.mapper.PersonOnlineMapper;
import com.chinobot.plep.home.monitor.mapper.PersonTrajectoryMapper;
import com.chinobot.plep.home.monitor.service.IPersonTrajectoryService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-11-22
 */
@Service
public class PersonTrajectoryServiceImpl extends BaseService<PersonTrajectoryMapper, PersonTrajectory> implements IPersonTrajectoryService {

	@Autowired
	private PersonOnlineMapper personOnlineMapper;
	@Override
	public List<PersonOnline> getList() {
		return personOnlineMapper.getList();
	}

}
