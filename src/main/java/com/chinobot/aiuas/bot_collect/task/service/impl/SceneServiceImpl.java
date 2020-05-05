package com.chinobot.aiuas.bot_collect.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.Domain;
import com.chinobot.aiuas.bot_collect.task.entity.Scene;
import com.chinobot.aiuas.bot_collect.task.entity.Task;
import com.chinobot.aiuas.bot_collect.task.entity.vo.SceneListVo;
import com.chinobot.aiuas.bot_collect.task.mapper.SceneMapper;
import com.chinobot.aiuas.bot_collect.task.mapper.TaskMapper;
import com.chinobot.aiuas.bot_collect.task.service.ISceneService;
import com.chinobot.aiuas.bot_collect.task.service.ITaskService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查场景 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Service
public class SceneServiceImpl extends BaseService<SceneMapper, Scene> implements ISceneService {

    @Autowired
    private SceneMapper sceneMapper;
    @Autowired
    private ITaskService iTaskService;

    @Override
    public IPage<SceneListVo> getSceneList(Page page, Map<String, Object> param) {
        return sceneMapper.getSceneList(page, param);
    }

    @Override
    public Scene getSceneById(String uuid) {
        QueryWrapper<Scene> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("uuid", "domain_uuid", "s_name")
                .eq("uuid", uuid)
                .eq("is_deleted", GlobalConstant.IS_DELETED_NO);

        return getOne(queryWrapper);
    }

    @Override
    public Result editScene(Scene scene) {
        QueryWrapper<Scene> sceneQueryWrapper = new QueryWrapper<>();
        sceneQueryWrapper.eq("s_name", scene.getSName());
        sceneQueryWrapper.eq("is_deleted", GlobalConstant.IS_DELETED_NO);
        Scene one = getOne(sceneQueryWrapper);

        if(!CommonUtils.isObjEmpty(one)){
            if(CommonUtils.isObjEmpty(scene.getUuid()) ||
                    (!CommonUtils.isObjEmpty(scene.getUuid()) &&
                            !one.getUuid().equals(scene.getUuid()))){
                return ResultFactory.error(23000, "场景名称已存在！", null);
            }
        }
        saveOrUpdate(scene);

        return ResultFactory.success(scene.getUuid());
    }

    @Override
    public Result delScene(String uuid) {
        // 删除场景
        Scene scene = new Scene();
        scene.setUuid(uuid);
        scene.setIsDeleted(GlobalConstant.IS_DELETED_YES);
        sceneMapper.updateById(scene);
        // 删除任务
        iTaskService.delTaskBySceneUuid(uuid);

        return ResultFactory.success();
    }

}
