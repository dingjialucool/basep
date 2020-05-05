package com.chinobot.plep.home.plan.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 
 * @ClassName: IMainService   
 * @Description: 主页面数据  服务类
 * @author: dingjialu  
 * @date:2019年7月9日 上午11:13:17
 */
public interface IMainService {

	/**
	 *    页面初始化数据 无人机与事件数据
	 * @param param
	 * @return
	 */
	Map getMainData(Page page,Map<String, Object> param);

}
