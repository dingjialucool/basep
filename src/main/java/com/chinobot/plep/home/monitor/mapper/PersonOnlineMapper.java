package com.chinobot.plep.home.monitor.mapper;

import com.chinobot.plep.home.homepage.entity.vo.HomePageUavVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlinePersonDetailVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlinePersonVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlineUavDetailVo;
import com.chinobot.plep.home.monitor.entity.PersonOnline;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2019-11-22
 */
public interface PersonOnlineMapper extends IBaseMapper<PersonOnline> {
	
	/**
	 * 在线人员列表
	 * @return
	 */
	List<PersonOnline> getList();

	/**
	 * 部门人员
	 * @param param
	 * @return
	 */
	List<OnlinePersonVo> getPersonByDept(@Param("p") Map<String, Object> param);

	/**
	 * 部门下的在线无人机
	 * @param param
	 * @return
	 */
	List<HomePageUavVo> getUavByDept(@Param("p") Map<String, Object> param);

	/**
	 * 在线人员信息
	 * @param uuid
	 * @return
	 */
	List<OnlinePersonDetailVo> getPersonDetail(String uuid);

	/**
	 * 无人机在线信息
	 * @param uuid
	 * @return
	 */
	List<OnlineUavDetailVo> getUavOnlineDetail(String uuid);

}
