package com.chinobot.framework.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinobot.framework.web.entity.BaseMenu;
import com.chinobot.framework.web.mapper.BaseMenuMapper;
import com.chinobot.framework.web.service.IBaseMenuService;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author zhoull
 * @since 2019-05-31
 */
@Service
public class BaseMenuServiceImpl extends BaseService<BaseMenuMapper, BaseMenu> implements IBaseMenuService {

	@Autowired
	private BaseMenuMapper baseMenuMapper;
	
	@Override
	public List<BaseMenu> searchMenuByPersonId(String personId) {
		return baseMenuMapper.searchMenuByPersonId(personId);
	}

	@Override
	public List<BaseMenu> searchMenuByPersonIdAndSystem(String personId, String systemId) {
		return baseMenuMapper.searchMenuByPersonIdAndSystem(personId, systemId);
	}

}
