package com.chinobot.cityle.base.service;

import java.util.List;
import java.util.Map;

import com.chinobot.cityle.base.entity.Role;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-03-20
 */
public interface IRoleService extends IBaseService<Role> {
	
	/**
	 * 角色 列表
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年3月21日
	 * @company chinobot
	 */
	public List<Map> getRoleList(Map param);
	
	/**
	 * 根据 用户/人员id 获取角色列表
	 * @param personId
	 * @param type
	 * @return
	 * @author shizt  
	 * @date 2019年3月20日
	 * @company chinobot
	 */
	List<Map> getRoleByPersonId(String personId);
}
