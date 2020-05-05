package com.chinobot.cityle.base.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.SceneTaskTemplet;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 巡查内容模板 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-04-09
 */
public interface ISceneTaskTempletService extends IBaseService<SceneTaskTemplet> {

	/**
	 * 巡查内容模板 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	IPage<Map> getTaskTempletPage(Page page, Map<String, Object> param);
	
	/**
	 * 根据id获取巡查内容模板
	 * @param uuid
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	Map getTaskTempletById(String uuid);
	
	/**
	 * 根据id获取巡查内容模板
	 * @param uuid
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	List<Map> getTaskTempletByParam(Map<String, String> param);
}
