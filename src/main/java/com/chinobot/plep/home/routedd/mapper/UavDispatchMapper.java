package com.chinobot.plep.home.routedd.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.routedd.entity.UavDispatch;

/**
 * <p>
 * 无人机调度表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-07-24
 */
public interface UavDispatchMapper extends IBaseMapper<UavDispatch> {
	/**
	 * 获取所有无人机
	 * @return
	 */
	List<Map> getAllUav(@Param("p") Map<String, String> param);
	/**
	 * 获取单个无人机的日期调度
	 * @param uuid
	 * @return
	 */
	List<Map> getUavDateState(String uuid);
	/**
	 * 获取禁飞区
	 * @param param
	 * @return
	 */
	List<Map> getNotFlyPoints(@Param("p") Map<String, Object> param);
	/**
	 * 获取 可飞行的起飞点
	 * @param param
	 * @return
	 */
	List<Map> getFlyPoints(@Param("p") Map<String, Object> param);
	/**
	 * 获取起飞点的路线
	 * @param uuid 起飞点 主键
	 * @return
	 */
	List<Map> getRoutesOfPoint(String uuid);
	/**
	 * 获取调度明细 
	 * @param dspId
	 * @return
	 */
	List<Map> getDispatchDetail(String dspId);
	
	/**
	 * 获取起飞点
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年7月31日
	 * @company chinobot
	 */
	List<Map> getPointByParam(@Param("p") Map<String, String> param);
	/**
	 * 获取历史调度分页
	 * @param param
	 * @return
	 */
	IPage<Map> getHistoryList(Page page, @Param("p") Map<String, Object> param);
	/**
	 * 获取收藏调度分页
	 * @param param
	 * @return
	 */
	IPage<Map> getCollectList(Page page, @Param("p") Map<String, Object> param);
	/**
	 * 获取起飞点的巡查范围
	 * @param uuid
	 * @return
	 */
	List<Map> getFlyAreaOfPoint(String uuid);
	/**
	 * 获取各日期状态
	 * @return
	 */
	List<Map> getDateState(@Param("p") Map<String, String> param);
	/**
	 * 
	 * @return
	 */
	String getTimeById(@Param("uuid") String uuid);

	/**
	 * 根据飞行员获取当日巡查无人机
	 * @Param:
	 * @Return: java.util.List<java.util.Map>
	 * @Author: shizt
	 * @Date: 2019/9/6 20:00
	 */
	List<Map> getUavByFlyPerson(@Param("pid")String pid);
	
	List<UavDispatch> getRouteListByDate(@Param("p") Map<String, Object> param); 

}
