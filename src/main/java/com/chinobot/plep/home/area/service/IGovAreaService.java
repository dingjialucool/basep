package com.chinobot.plep.home.area.service;

import java.util.List;
import java.util.Map;

import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.area.entity.GovArea;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangw
 * @since 2019-09-10
 */
public interface IGovAreaService extends IBaseService<GovArea> {
	/**
	 * 保存行政区划
	 * @param govArea
	 */
	void saveGovArea(GovArea govArea);
	/**
	 * 根据父节点查询树节点
	 * @param param
	 * @return
	 */
	List<Map> getListByParent(Map<String, Object> param);
	
	void del(GovArea govArea);

}
