package com.chinobot.plep.home.route.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.route.entity.Route;
import com.chinobot.plep.home.route.entity.vo.VoPointRelation;
import com.chinobot.plep.home.route.entity.vo.VoRoute;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 路线表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-07-16
 */
public interface IRouteService extends IBaseService<Route> {

	IPage<Map> getIllegalBuilding(Page page, Map<String, String> param);
	
	List<Map> getIllegalBuilding(Map<String, String> param);
	
	String save(VoRoute vo);
	
	List<Map> getRouteLine(String uuid);

	void delRoute(Route route);
	
	List<Map> getAllRouteLine(Map<String, String> param);
	
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
	 * 新增起飞点    关联起飞点
	 * @param vo
	 * @return
	 */
	boolean addPointAndRelations(VoPointRelation vo);

	/**
	 * 删除起飞点 和 将 范围内 的 关联点置为空
	 * @param rangId
	 * @param pointId
	 * @return
	 */
	boolean deletePoint(String rangId, String pointId);

	/**
	 * 获取航点列表
	 * @Param: [param]
	 * @Return: java.util.List<java.util.Map>
	 * @Author: shizt
	 * @Date: 2019/8/19 16:23
	 */
	List<Map> getRouteList(Map<String, String> param);
	
	/**
	 * 获取航线列表
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<Map> flyRouteList(Page page, Map<String, Object> param);

	/**
	 * 获取飞行任务列表
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<Map> flyTaskList(Page page, Map<String, Object> param);

	/**
	 * 删除飞行任务
	 * @param param
	 * @return
	 */
	boolean delFlyTask(Map<String, Object> param);
	
	List<Route> getRouteByDeptAndName(Map<String, Object> param);
}
