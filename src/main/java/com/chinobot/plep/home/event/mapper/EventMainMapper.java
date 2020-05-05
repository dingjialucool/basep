package com.chinobot.plep.home.event.mapper;

import com.chinobot.plep.home.event.entity.EventMain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 事件主表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-06-11
 */
public interface EventMainMapper extends IBaseMapper<EventMain> {

	/**
	 * 
	 * <p>获取事件列表</p>  
	 * @param page
	 * @param param
	 * @return  
	 * @author huangw
	 */
	IPage<Map> getEventList(Page page, @Param("param") Map<String, Object> param);
	
	/**
	 * 根据业务主键获取图片
	 * <p></p>  
	 * @param busId
	 * @return  
	 * @author huangw
	 */
	List<Map> getImgListByBusId(@Param("busId") String busId, @Param("module") String module);
	/**
	 * 根据业务主键获取视频
	 * <p></p>  
	 * @param busId
	 * @return  
	 * @author huangw
	 */
	List<Map> getVedioListByBusId(@Param("busId") String busId, @Param("module") String module);
	/**
	 * 根据事件id获取事件预警信息
	 * <p></p>  
	 * @param eventId
	 * @return  
	 * @author huangw
	 */
	Map getEventWarningInfoById(@Param("eventId") String eventId);
	/**
	 * 根据事件id获取巡查记录集合
	 * <p></p>  
	 * @param eventId
	 * @return  
	 * @author huangw
	 */
	List<Map> getPatrolListById(@Param("eventId") String eventId);
	/**
	 *根据事件id获取整改记录集合
	 * <p></p>  
	 * @param eventId
	 * @return  
	 * @author huangw
	 */
	List<Map> getReformListById(@Param("eventId") String eventId);
	
	List<Map> getLastEventReviewRecord(String eventId);
	/**
	 * 获取所有部门的位置信息
	 * @return
	 */
	List<Map> getAllDeptPosition();
	/**
	 * 修改后的事件分页
	 * @param param
	 * @return
	 */
	IPage<Map> getEventPage2(Page page, @Param("param") Map<String, Object> param);

	/**
	 * 查询首页 事件数据
	 * @param param
	 * @return
	 */
	IPage<Map> getAllEven(Page page,@Param("p")Map<String, Object> param);
}
