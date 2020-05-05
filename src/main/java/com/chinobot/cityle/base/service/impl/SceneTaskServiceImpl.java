package com.chinobot.cityle.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.SceneTask;
import com.chinobot.cityle.base.entity.SceneTaskRole;
import com.chinobot.cityle.base.entity.vo.SceneTaskRoleVo;
import com.chinobot.cityle.base.mapper.SceneTaskMapper;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.cityle.base.service.ISceneTaskRoleService;
import com.chinobot.cityle.base.service.ISceneTaskService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 巡查内容 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-04-10
 */
@Service
public class SceneTaskServiceImpl extends BaseService<SceneTaskMapper, SceneTask> implements ISceneTaskService {

	@Autowired
	private SceneTaskMapper sceneTaskMapper;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private ISceneTaskRoleService sceneTaskRoleService;
	@Autowired
	private ISceneTaskService sceneTaskService;
	
	@Override
	public IPage<Map> getTaskPage(Page page, Map<String, String> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		
		return sceneTaskMapper.getTask(page, param);
	}

	@Override
	public Map getTaskById(String uuid) {
		Map<String, String> param = new HashMap();
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		param.put("uuid", uuid);
		
		List<Map> tasks = sceneTaskMapper.getTask(param);
		
		return (tasks.size() > 0)? tasks.get(0): null;
	}

	@Override
	public void saveOrUpdateSceneTaskRole(SceneTaskRoleVo sceneTaskRoleVo) {

		SceneTask sceneTask = sceneTaskRoleVo.getSceneTask();
		//是否存在场景
		boolean exists = true;
		if(CommonUtils.isObjEmpty(sceneTask.getUuid())) {
			exists = false;
		}
		//保存场景信息
		sceneTaskService.saveOrUpdate(sceneTask);
		
		List<SceneTaskRole> roles = sceneTaskRoleVo.getSceneTaskRole();
		if(CommonUtils.objNotEmpty(roles)) {
			if(!exists) {
				//新增场景任务角色
				for (SceneTaskRole sceneRole : roles) {
					sceneRole.setSceneTaskId(sceneTask.getUuid());
				}
			}else {
				//删除场景任务所有角色
				QueryWrapper<SceneTaskRole> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("scene_task_id", sceneTask.getUuid());
				sceneTaskRoleService.remove(queryWrapper);
			}
			
			for (SceneTaskRole sceneRole : roles) {
				sceneRole.setSceneTaskId(sceneTask.getUuid());
			}
			//保存场景任务角色
			sceneTaskRoleService.saveBatch(roles);
		}
	}
}
