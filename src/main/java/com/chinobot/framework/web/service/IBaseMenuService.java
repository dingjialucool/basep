package com.chinobot.framework.web.service;

import java.util.List;


import com.chinobot.framework.web.entity.BaseMenu;

/**
 * <p>
 *菜单 服务类
 * </p>
 *
 * @author zhoull
 * @since 2019-05-31
 */
public interface IBaseMenuService extends IBaseService<BaseMenu> {

	List<BaseMenu> searchMenuByPersonId(String uuid);
	
	List<BaseMenu> searchMenuByPersonIdAndSystem(String personId, String systemId);

}
