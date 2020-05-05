package com.chinobot.plep.home.route.service;

import com.chinobot.plep.home.homepage.entity.vo.HomePageAreaRendaSceneVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageEarlyWarningDeptMonthVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageTaskCountOfPointVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageUavVo;
import com.chinobot.plep.home.route.entity.CheckPoint;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 起飞点表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-07-16
 */
public interface ICheckPointService extends IBaseService<CheckPoint> {
	List<CheckPoint> getAllCheckPointBydept(Map<String, String> param);
	
	Long getCountOfPath(Map<String, String> param);
	
	Long getCountOfCheckPoint(Map<String, String> param);
	
	List<HomePageTaskCountOfPointVo> getCheckPointAndTaskCount(Map<String, Object> param);
	
	List<String> getRangePolygon(Map<String, Object> param);
	
	String getDeptPolygon(Map<String, Object> param);
	/**
	 * 获取区域扫描雷达图的MAX
	 * @param param
	 * @return
	 */
	Long getMaxCount(Map<String, Object> param);
	
	List<HomePageAreaRendaSceneVo> getPathCountByDeptAndScene(Map<String, Object> param);
	
	List<HomePageEarlyWarningDeptMonthVo> getHomePageEarlyWarningInfo(Map<String, Object> param);

	/**
	 *  获取所有航线数量
	 * @param param
	 * @return
	 */
	Long getCountOfLine(Map<String, Object> param);

	/**
	 *  获取航线所覆盖区域的边界
	 * @param param
	 * @return
	 */
	List<String> getRangeByLinePolygon(Map<String, Object> param);

	/**
	 * 航线总耗时
	 * @param param
	 * @return
	 */
	String getTotalOfFlyTime(Map<String, Object> param);

	/**
	 * 飞行次数
	 * @param param
	 * @return
	 */
	List<Map> getFlyNum(Map<String, Object> param);
	/**
	 * 首页无人机分页
	 * @param page
	 * @param deptId
	 * @return
	 */
	IPage<HomePageUavVo> getUavByDept(Page<HomePageUavVo> page, String uuid);
}
