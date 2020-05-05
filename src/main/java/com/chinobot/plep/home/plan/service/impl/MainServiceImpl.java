package com.chinobot.plep.home.plan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.Role;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.cityle.base.service.IUavService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.plep.home.event.mapper.EventMainMapper;
import com.chinobot.plep.home.plan.service.IMainService;

/**
 * 
 * @ClassName: MainServiceImpl   
 * @Description: 主页面数据 服务实现类 
 * @author: dingjialu  
 * @date:2019年7月9日 上午11:15:00
 */
@Service
public class MainServiceImpl implements IMainService{

	@Autowired
	private IUavService uavService;
	@Autowired
	private EventMainMapper eventMainMapper;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IRoleService roleService;
	@Override
	public Map getMainData(Page page,Map<String, Object> param) {
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
		IPage<Map> allUav = uavService.getAllUav(page,param);	
		map.put("allUav", allUav);
			
		return map;
	}
	


}
