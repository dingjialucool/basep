package com.chinobot.cityle.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinobot.cityle.base.entity.Role;
import com.chinobot.cityle.base.mapper.RoleMapper;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-03-20
 */
@Service
public class RoleServiceImpl extends BaseService<RoleMapper, Role> implements IRoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List<Map> getRoleList(Map param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		
		return roleMapper.getRoleList(param);
	}
	
	@Override
	public List<Map> getRoleByPersonId(String personId) {
		
		return roleMapper.getRoleByPersonId(personId);
	}

}
