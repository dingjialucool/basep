package com.chinobot.cityle.base.service;

import java.util.List;
import java.util.Map;

import com.chinobot.cityle.base.entity.DeptGrid;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 部门网格关联 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-04-02
 */
public interface IDeptGridService extends IBaseService<DeptGrid> {

	/**
	 *  获取已关联网格部门
	 * @param paramDept
	 * @return
	 */
	List<Map> getDeptGrid(Map paramDept);

}
