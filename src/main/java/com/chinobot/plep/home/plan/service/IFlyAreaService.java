package com.chinobot.plep.home.plan.service;

import com.chinobot.plep.home.plan.entity.FlyArea;
import com.chinobot.plep.home.plan.entity.vo.VoPathPlan;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangw
 * @since 2019-06-17
 */
public interface IFlyAreaService extends IBaseService<FlyArea> {
	/**
	 * 保存飞行路线及配置
	 * @param vo
	 */
	void saveConfigAndPath(VoPathPlan vo);
	/**
	 *获取飞行 路线及配置 
	 * @param areaId
	 * @return
	 */
	VoPathPlan getConfigAndPath(String areaId);
	
	/**
	 * 获取航线、输出参数、飞行参数
	 * @param param
	 * @return
	 */
	Map getPathConfig(Map<String, Object> param);
	
	/**
	 * 获取某个范围下的所有区域
	 * @param param
	 * @return
	 */
	IPage<Map> getFlyAreaPage(Page page,Map param);

}
