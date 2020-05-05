package com.chinobot.cityle.ddjk.service;

import java.util.List;
import java.util.Map;

import com.chinobot.cityle.ddjk.entity.RobotGatherData;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 设备采集场景数据 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-04-15
 */
public interface IRobotGatherDataService extends IBaseService<RobotGatherData> {
	/**
	 * 预警 列表
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月16日
	 * @company chinobot
	 */
	List<Map> getWarningList(Map<String, Object> param);

	/**
	 * 首页事件来源数据
	 * @return
	 */
	Map getThingOri();
	
	/**
	 *  首页执法队伍数据
	 * @return
	 */
	Map deptData();

	/**
	 * 首页预警分析数据
	 * @return
	 */
	Map warnData();

	/**
	 *  首页顶部数据
	 * @param param
	 * @return
	 */
	Map topData();
}
