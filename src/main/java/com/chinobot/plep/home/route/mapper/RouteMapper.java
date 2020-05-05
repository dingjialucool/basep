package com.chinobot.plep.home.route.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.route.entity.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 路线表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-07-16
 */
public interface RouteMapper extends IBaseMapper<Route> {
	
	IPage<Map> getIllegalBuilding(Page page, @Param("p") Map<String, String> param);
	
	List<Map> getIllegalBuilding(@Param("p") Map<String, String> param);
	
	List<Map> getRouteLine(@Param("uuid") String uuid);
	
	List<Map> getAllRouteLine(@Param("p") Map<String, String> param);
	
	/**
	 * 根据航线id获取航线节点
	 * @param routeId
	 * @return
	 * @author shizt  
	 * @date 2019年8月2日
	 * @company chinobot
	 */
	List<Map> getRoutePoint(String routeId);

	/**
	 * 获取航点列表
	 * @Param: [param]
	 * @Return: java.util.List<java.util.Map>
	 * @Author: shizt
	 * @Date: 2019/8/19 16:23
	 */
	List<Map> getRouteList(@Param("p") Map<String, String> param);
	
	/**
	 * 获取航线列表
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<Map> flyRouteList(Page page,@Param("p")Map<String, Object> param);

	/**
	 * 获取飞行任务列表
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<Map> flyTaskList(Page page,@Param("p")Map<String, Object> param);
	/**
	 * 获取追踪航线列表
	 * @param param
	 * @return
	 */
	List<Route> getRouteByDeptAndName(@Param("p")Map<String, Object> param);
}
