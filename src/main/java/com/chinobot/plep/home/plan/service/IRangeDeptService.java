package com.chinobot.plep.home.plan.service;

import com.chinobot.plep.home.plan.entity.RangeDept;

import java.util.List;
import java.util.Map;

import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 范围部门关系表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-06-16
 */
public interface IRangeDeptService extends IBaseService<RangeDept> {
	
	/**
	    *    获取范围已关联的部门
	 * <p></p>  
	 * @param paramDept
	 * @return  
	 * @author huangw
	 */
	List<Map> getRangeGrid(Map paramDept);

}
