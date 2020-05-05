package com.chinobot.plep.home.building.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.entity.vo.VoAddressBaseBuildingDept;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.building.entity.Building;
import com.chinobot.plep.home.building.entity.VoDeptAddressBase;

/**
 * <p>
 * 网格 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-03-26
 */
public interface IBuildingService extends IBaseService<Building> {


	List<Map> getBuildingAddrList(Map<String, String> param);
	
	/**
	 * 网格/地址 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年3月18日
	 * @company chinobot
	 */
	IPage<VoAddressBase<Map>> getBuildingAddrPage(Page page, Map<String, String> param);
	
	
	/**
	 * 根据id获取网格/地址
	 * @param uuid
	 * @return
	 * @author shizt  
	 * @date 2019年3月26日
	 * @company chinobot
	 */
	VoAddressBase<Map> getBuildingAddrById(String uuid);
	
	/**
	 * 网格 新增修改
	 * @param voAddressBaseScenesDeptGrids
	 * @return
	 * @author shizt  
	 * @date 2019年4月2日
	 * @company chinobot
	 */
	Result editGrid(VoAddressBaseBuildingDept<Building> voAddressBase);
	
	/**
	 * 删除 
	 * @param grid
	 * @return
	 * @author shizt  
	 * @date 2019年4月2日
	 * @company chinobot
	 */
	Result delGrid(Building building);

	/**
	 *  辅助分析数据 地图总览
	 * @param param
	 * @return
	 */
	List<LinkedHashMap<String, Object>> areaData(Map<String, String> param);

	IPage<VoAddressBase<Map>> getBuildingWeiAddrPage(Page page, Map<String, String> param);

	IPage<VoAddressBase<Map>> getBuildingYiAddrPage(Page page, Map<String, String> param);
	
	/**
	 * 查询中心点的范围	
	 * @param param	
	 * lon精度 		
	 * lat维度 	
	 * radius半径
	 * @return
	 */
	List<Map> getNearbyBuilding(Map<String, Object> param);

	List<Map> getBuildingAddrPolicy(Map<String, Object> param);

	/**
	 *  新增修改违章建筑
	 * @param voAddressBase
	 * @return
	 */
	Result addBuild(VoDeptAddressBase<Building> voAddressBase);

	/**
	 *  获取所有违章建筑
	 * @param page
	 * @param param
	 * @return
	 */
	Map getBuilds(Page page, Map<String, Object> param);
	
	/**
	 * 删除建筑
	 * @param building
	 * @return
	 */
	Result deleteBuid(Building building);
	
}
