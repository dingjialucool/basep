package com.chinobot.plep.home.scene.service;

import java.util.List;
import java.util.Map;

import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.scene.entity.Scene;

/**
 * <p>
 * 场景表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-08-22
 */
public interface ISceneService extends IBaseService<Scene> {

	//获取目前所有有效领域
	List<Scene> getAllScene();
	
	List<Map> getAllSceneTree(Map<String, Object> param);
	
	void del(Scene scene);
	
	/**
	 * 根据领域和场景的code获取场景
	 * @param domainCode
	 * @param sceneCode
	 * @return
	 */
	Scene getSceneByDomainCodeAndSceneCode(String domainCode, String sceneCode);
}
