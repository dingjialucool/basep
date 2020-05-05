package com.chinobot.plep.home.monitor.service;

import com.chinobot.plep.home.homepage.entity.vo.HomePageUavVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlinePersonDetailVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlinePersonVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlineUavDetailVo;
import com.chinobot.plep.home.monitor.entity.PersonOnline;

import java.util.List;
import java.util.Map;

import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author djl
 * @since 2019-11-22
 */
public interface IPersonOnlineService extends IBaseService<PersonOnline> {
	
	List<OnlinePersonVo> getPersonByDept(Map<String, Object> param);

	List<HomePageUavVo> getUavByDept(Map<String, Object> param);

	/**
	 * 人员在线信息
	 * @param uuid
	 * @return
	 */
	OnlinePersonDetailVo getPersonDetail(String uuid);

	/**
	 * 无人机在线信息
	 * @param uuid
	 * @return
	 */
	OnlineUavDetailVo getUavOnlineDetail(String uuid);

}
