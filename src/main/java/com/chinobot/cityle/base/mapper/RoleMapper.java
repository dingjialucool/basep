package com.chinobot.cityle.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.cityle.base.entity.Role;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-03-20
 */
public interface RoleMapper extends IBaseMapper<Role> {
	
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
	 * @return
	 * @author shizt  
	 * @date 2019年3月20日
	 * @company chinobot
	 */
	public List<Map> getRoleByPersonId(@Param("personId")String personId);
}
