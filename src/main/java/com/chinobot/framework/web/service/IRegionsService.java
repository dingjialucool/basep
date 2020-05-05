package com.chinobot.framework.web.service;

import com.chinobot.framework.web.entity.Regions;

/**
 * <p>
 * 行政区划表（2018） 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-05-16
 */
public interface IRegionsService extends IBaseService<Regions> {

	/**
	 * 根据行政区划名称获取代码
	 * @param name
	 * @return
	 * @author shizt  
	 * @date 2019年5月16日
	 * @company chinobot
	 */
	String getRegionsCode(String name);
}
