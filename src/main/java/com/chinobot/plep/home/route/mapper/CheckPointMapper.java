package com.chinobot.plep.home.route.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.homepage.entity.vo.HomePageAreaRendaSceneVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageEarlyWarningDeptMonthVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageTaskCountOfPointVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageUavVo;
import com.chinobot.plep.home.route.entity.CheckPoint;

/**
 * <p>
 * 起飞点表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-07-16
 */
public interface CheckPointMapper extends IBaseMapper<CheckPoint> {

	List<CheckPoint> getAllCheckPointBydept(@Param("p") Map<String, String> param);
	
	
	Long getCountOfPath(@Param("p") Map<String, String> param);
	
	Long getCountOfCheckPoint(@Param("p") Map<String, String> param);
	
	List<HomePageTaskCountOfPointVo> getCheckPointAndTaskCount(@Param("p") Map<String, Object> param);
	
	List<String> getRangePolygon(@Param("p") Map<String, Object> param);
	
	String getDeptPolygon(@Param("p") Map<String, Object> param);
	
	Long getMaxCount(@Param("p") Map<String, Object> param);
	
	List<HomePageAreaRendaSceneVo> getPathCountByDeptAndScene(@Param("p") Map<String, Object> param);
	
	List<HomePageEarlyWarningDeptMonthVo> getHomePageEarlyWarningInfo(@Param("p") Map<String, Object> param);


	Long getCountOfLine(@Param("p") Map<String, Object> param);


	List<String> getRangeByLinePolygon(@Param("p") Map<String, Object> param);


	String getTotalOfFlyTime(@Param("p") Map<String, Object> param);


	List<Map> getFlyNum(@Param("p") Map<String, Object> param);
	
	IPage<HomePageUavVo> getUavByDept(Page page, @Param("deptId") String deptId, @Param("uuid") String uuid);
}
