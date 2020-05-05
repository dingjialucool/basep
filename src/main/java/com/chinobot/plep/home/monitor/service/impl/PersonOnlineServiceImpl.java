package com.chinobot.plep.home.monitor.service.impl;

import com.chinobot.plep.home.homepage.entity.vo.HomePageUavVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlinePersonDetailVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlinePersonVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlineUavDetailVo;
import com.chinobot.plep.home.monitor.entity.PersonOnline;
import com.chinobot.plep.home.monitor.mapper.PersonOnlineMapper;
import com.chinobot.plep.home.monitor.service.IPersonOnlineService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
 * @author djl
 * @since 2019-11-22
 */
@Service
public class PersonOnlineServiceImpl extends BaseService<PersonOnlineMapper, PersonOnline> implements IPersonOnlineService {

	@Autowired
	private PersonOnlineMapper personOnlineMapper;
	
	@Override
	public List<OnlinePersonVo> getPersonByDept(Map<String, Object> param) {
		
		return personOnlineMapper.getPersonByDept(param);
	}

	@Override
	public List<HomePageUavVo> getUavByDept(Map<String, Object> param) {
		return personOnlineMapper.getUavByDept(param);
	}

	@Override
	public OnlinePersonDetailVo getPersonDetail(String uuid) {
		List<OnlinePersonDetailVo> list = personOnlineMapper.getPersonDetail(uuid);
		if (list.size() > 0) {
			OnlinePersonDetailVo onlinePersonDetailVo = list.get(0);
			return onlinePersonDetailVo;
		}
		return null;
	}

	@Override
	public OnlineUavDetailVo getUavOnlineDetail(String uuid) {
		List<OnlineUavDetailVo> list = personOnlineMapper.getUavOnlineDetail(uuid);
		if (list.size() > 0) {
			OnlineUavDetailVo onlineUavDetailVo = list.get(0);
			return onlineUavDetailVo;
		}
		return null;
	}

}
