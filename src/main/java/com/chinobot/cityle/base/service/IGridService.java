package com.chinobot.cityle.base.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Grid;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.entity.vo.VoAddressBaseScenesDeptGrids;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 网格 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-03-26
 */
public interface IGridService extends IBaseService<Grid> {

	/**
	 * 网格/地址 列表
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月1日
	 * @company chinobot
	 */
	List<Map> getGridAddrList(Map<String, String> param);
	
	/**
	 * 网格/地址 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年3月18日
	 * @company chinobot
	 */
	IPage<VoAddressBase<Map>> getGridAddrPage(Page page, Map<String, String> param);
	
	
	/**
	 * 根据id获取网格/地址
	 * @param uuid
	 * @return
	 * @author shizt  
	 * @date 2019年3月26日
	 * @company chinobot
	 */
	VoAddressBase<Map> getGridAddrByid(String uuid);
	
	/**
	 * 网格 新增修改
	 * @param voAddressBaseScenesDeptGrids
	 * @return
	 * @author shizt  
	 * @date 2019年4月2日
	 * @company chinobot
	 */
	Result editGrid(VoAddressBaseScenesDeptGrids<Grid> voAddressBaseScenesDeptGrids);
	
	/**
	 * 删除 
	 * @param grid
	 * @return
	 * @author shizt  
	 * @date 2019年4月2日
	 * @company chinobot
	 */
	Result delGrid(Grid grid);

	/**
	 *  辅助分析数据 地图总览
	 * @param param
	 * @return
	 */
	List<LinkedHashMap<String, Object>> areaData(Map<String, String> param);
}
