package com.chinobot.cityle.base.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.SceneTask;
import com.chinobot.cityle.base.entity.vo.SceneTaskRoleVo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 巡查内容 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-04-10
 */
public interface ISceneTaskService extends IBaseService<SceneTask> {

	/**
	 * 巡查内容 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	IPage<Map> getTaskPage(Page page, Map<String, String> param);
	
	/**
	 * 巡查内容 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	Map getTaskById(String uuid);
	
	/**
	 *  场景角色新增修改
	 * @param sceneTaskRole
	 */
	void saveOrUpdateSceneTaskRole(SceneTaskRoleVo sceneTaskRoleVo);
}
