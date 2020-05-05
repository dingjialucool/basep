package com.chinobot.plep.home.monitor.service.impl;

import com.chinobot.plep.home.monitor.entity.UavFlight;
import com.chinobot.plep.home.monitor.entity.UavOnline;
import com.chinobot.plep.home.monitor.mapper.UavOnlineMapper;
import com.chinobot.plep.home.monitor.service.IUavOnlineService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.HttpUtils;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-07-26
 */
@Service
public class UavOnlineServiceImpl extends BaseService<UavOnlineMapper, UavOnline> implements IUavOnlineService {
	
	@Autowired
    private IUavOnlineService uavOnlineService;
	@Autowired
	private UavOnlineMapper uavOnlineMapper;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IRoleService roleService;
	
	@Override
	public void updateUav(UavFlight flight) {
		//先判断uav_online表中是否存在这个无人机
		QueryWrapper<UavOnline> queryWrapper = new QueryWrapper<UavOnline>();
		queryWrapper.eq("uav_code", flight.getUavCode());
		UavOnline uavOnline = uavOnlineService.getOne(queryWrapper);
		if(uavOnline == null) {
			UavOnline uavOnline2 = setUavOnline(flight,uavOnline);
			uavOnline2.setUavCode(flight.getUavCode());
			uavOnlineService.save(uavOnline2);
		}else {
			UpdateWrapper<UavOnline> updateWrapper = new UpdateWrapper<UavOnline>();
			updateWrapper.eq("uav_code",flight.getUavCode());
			UavOnline setUavOnline = setUavOnline(flight,uavOnline);
			uavOnlineService.update(setUavOnline, updateWrapper);
		}
	}
	
	private UavOnline setUavOnline(UavFlight flight,UavOnline uavOnline) {
		if(uavOnline == null) {
			uavOnline = new UavOnline();
		}
		
		uavOnline.setRunStatus(GlobalConstant.DATA_STATUS_VALID);
		uavOnline.setLongitude(flight.getLongitude());
		uavOnline.setLatitude(flight.getLatitude());
		uavOnline.setFlyingHeight(flight.getFlyingHeight());
		uavOnline.setFlightSpeed(flight.getFlightSpeed());
		uavOnline.setOperateBy(flight.getOperateBy());
		uavOnline.setOperateTime(flight.getOperateTime());
		uavOnline.setWorkId(flight.getWorkId());
		uavOnline.setWorkType(flight.getWorkType());
		uavOnline.setWorkStartTime(flight.getWorkStartTime());	
		return uavOnline;
	}

	@Override
	public List<Map> uavList(Page page,Map<String, Object> param) {
		Person person = ThreadLocalUtil.getResources();
		if(person != null) {
			person = personService.getById(person.getUuid());
		}
		param.put("deptId",person.getDeptId());
		List<Map> roles = roleService.getRoleByPersonId(person.getUuid());
		for(Map map : roles) {
			if(GlobalConstant.SUPER_ADMIN_ID.equals(map.get("roleId"))) {
				param.remove("dept_id");//超级管理员查看所有
			}
		}
		
		Map map = new HashMap();
		
		//如果是按照无人机名称查询的
		param.put("ename", param.get("name"));
		//获取所有的无人机
		IPage<Map> allUav = uavOnlineMapper.uavList(page,param);	
			
		return allUav.getRecords();
	}

	@Override
	public List<UavOnline> getUavOnlines() {
		
		return uavOnlineMapper.getUavOnlines();
	}
}
