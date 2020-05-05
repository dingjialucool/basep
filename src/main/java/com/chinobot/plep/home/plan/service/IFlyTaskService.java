package com.chinobot.plep.home.plan.service;

import com.chinobot.plep.home.plan.entity.FlyTask;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 飞行任务表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-06-19
 */
public interface IFlyTaskService extends IBaseService<FlyTask> {

	IPage<Map> getTaskPage(Page page, Map<String, String> param);
	
	Map getTaskById(String uuid);

	/**
	 *  获取当天的巡查任务总数
	 * @param param
	 * @return
	 */
	Map getTaskByTime(Map<String, Object> param);


	
	void doUpload(VoAddressBase<FlyTask> vo,String moduleName);
	
	IPage<Map> getUavList(Page page, Map<String, String> param);

}
