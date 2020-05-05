package com.chinobot.plep.home.scene.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.scene.entity.Scene;
import com.chinobot.plep.home.scene.mapper.SceneMapper;
import com.chinobot.plep.home.scene.service.ISceneService;

/**
 * <p>
 * 场景表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-08-22
 */
@Service("SceneServiceImpl")
public class SceneServiceImpl extends BaseService<SceneMapper, Scene> implements ISceneService {
	@Autowired
	private SceneMapper sceneMapper;
	@Override
	public List<Scene> getAllScene() {
		QueryWrapper<Scene> wrapper = new QueryWrapper<Scene>();
		wrapper.eq("data_status", "1");
		wrapper.isNull("parent_id");
		wrapper.orderByAsc("create_time");
		return this.list(wrapper);
	}

	@Override
	public List<Map> getAllSceneTree(Map<String, Object> param) {
		return sceneMapper.getAllSceneTree(param);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void del(Scene scene) {
		scene = this.getById(scene.getUuid());
		scene.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		this.saveOrUpdate(scene);//本身置为无效
		if(StringUtils.isBlank(scene.getParentId())) {//领域
			Scene entity = new Scene();
			entity.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			UpdateWrapper<Scene> wrapper = new UpdateWrapper<Scene>();
			wrapper.eq("parent_id", scene.getUuid());
			this.update(entity  , wrapper);//子节点置为无效
		}
	}

	@Override
	public Scene getSceneByDomainCodeAndSceneCode(String domainCode, String sceneCode) {
		return sceneMapper.getSceneByDomainCodeAndSceneCode(domainCode, sceneCode);
	}

}
