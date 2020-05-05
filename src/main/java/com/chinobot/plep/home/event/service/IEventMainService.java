package com.chinobot.plep.home.event.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.event.entity.EventMain;

/**
 * <p>
 * 事件主表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-06-11
 */
public interface IEventMainService extends IBaseService<EventMain> {
	
	/**
	 *获取事件列表方法
	 * <p></p>  
	 * @param page
	 * @param param
	 * @return  
	 * @author huangw
	 */
	IPage<Map> getEventList(Page page, Map<String, Object> param);
	
	/**
	 * 获取事件详细信息
	 * <p></p>  
	 * @param eventId
	 * @return  
	 * @author huangw
	 */
	Map getEventDetail(String eventId);

	/**
	 * 事件处理
	 * @param eventId
	 * @return
	 */
	void handle(Map<String, Object> param);
	

	/**
	 * 获取人员列表
	 * @return
	 */
	List<Person> getPersonList();

	/**
	 * 新增事件
	 * @param eventId
	 * @return
	 */
	void add(Map<String, Object> param);
	
	/**
	 * 初始化参数
	 * @return
	 */
	Map addInit();
	
	/**
	 * 获取所有部门的位置信息
	 * @return
	 */
	List<Map> getAllDeptPosition();
	/**
	 *获取事件列表方法
	 * <p></p>  
	 * @param page
	 * @param param
	 * @return  
	 * @author huangw
	 */
	IPage<Map> getEventPage2(Page page, Map<String, Object> param);
	
	void warningFilter(String warningId) throws Exception;

	void warningFilterRemove(String warningId) throws Exception;
}
