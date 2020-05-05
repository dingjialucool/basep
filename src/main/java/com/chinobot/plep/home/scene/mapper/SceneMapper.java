package com.chinobot.plep.home.scene.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.scene.entity.Scene;

/**
 * <p>
 * 场景表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-08-22
 */
@Repository("oldSceneMapper")
public interface SceneMapper extends IBaseMapper<Scene> {

	List<Map> getAllSceneTree(@Param("p") Map<String, Object> param);

	Scene getSceneByDomainCodeAndSceneCode(@Param("domainCode")String domainCode, @Param("sceneCode")String sceneCode);
}
