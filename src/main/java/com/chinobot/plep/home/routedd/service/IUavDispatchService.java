package com.chinobot.plep.home.routedd.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.routedd.entity.DispatchDetail;
import com.chinobot.plep.home.routedd.entity.UavDispatch;
import com.chinobot.plep.home.routedd.entity.vo.DispatchVo;

/**
 * <p>
 * 无人机调度表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-07-24
 */
public interface IUavDispatchService extends IBaseService<UavDispatch> {

	/**
	 * 获取所有无人机
	 * @return
	 * @throws Exception 
	 */
	List<Map> getAllUav(Map<String, String> param) throws Exception;
	/**
	 * 获取禁飞区
	 * @param param
	 * @return
	 */
	List<Map> getNotFlyPoints(Map<String, Object> param);
	/**
	 * 获取 可飞行的起飞点
	 * @param param
	 * @return
	 */
	List<Map> getFlyPoints(Map<String, Object> param);
	/**
	 * 获取某天数据
	 * @param param
	 * @return
	 */
	Map getOneDay(String uavId, String time);
	/**
	 * 添加一个巡查点
	 * @param vo
	 */
	void addPoint(DispatchVo vo);
	/**
	 * 删除一个巡查点
	 * @param dispatchDetail
	 */
	void delPoint(DispatchDetail dispatchDetail);
	/**
	 * 清空单台无人机单天的调度信息
	 * @param uavDispatch
	 */
	void cleanOneDay(UavDispatch uavDispatch);
	/**
	 * 收藏或取消收藏
	 * @param uuid 调度Id
	 */
	void doCollect(String uuid);
	/**
	 * 获取历史调度分页
	 * @param param
	 * @return
	 */
	IPage<Map> getHistoryList(Page page, Map<String, Object> param);
	/**
	 * 获取收藏调度分页
	 * @param param
	 * @return
	 */
	IPage<Map> getCollectList(Page page, Map<String, Object> param);
	/**
	 * 引用
	 * @param targetId
	 * @param sourceId
	 */
	void doQuote(String targetId, String sourceId);
	
	/**
	 * 获取起飞点
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年7月31日
	 * @company chinobot
	 */
	List<Map> getPointByParam(Map<String, String> param);
	
	/**
	 * 获取各日期状态和无人机、飞行员列表
	 * @return
	 * @throws Exception 
	 */
	Map init(Map<String, String> param) throws Exception;
	/**
	 * 获取调度路线明细
	 * @param uuid
	 * @return
	 */
	Map getRouteDetail(String uuid);

	/**
	 * 根据飞行员获取当日巡查无人机
	 * @Param:
	 * @Return: java.util.List<java.util.Map>
	 * @Author: shizt
	 * @Date: 2019/9/6 19:58
	 */
	List<Map> getUavByFlyPerson(String pid);
	
	List<UavDispatch> getRouteListByDate(Map<String, Object> param); 
}
