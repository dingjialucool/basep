package com.chinobot.aiuas.bot_collect.task.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.Domain;
import com.chinobot.aiuas.bot_collect.task.entity.Scene;
import com.chinobot.aiuas.bot_collect.task.entity.vo.SceneListVo;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查场景 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface ISceneService extends IBaseService<Scene> {

    /**
     * 采查场景 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.task.entity.vo.SceneVo>
     * @Author: shizt
     * @Date: 2020/1/9 14:26
     */
    IPage<SceneListVo> getSceneList(Page page, Map<String, Object> param);

    /**
     * 根据主键获取采查场景
     * @Param: [uuid]
     * @Return: com.chinobot.aiuas.bot_collect.task.entity.Scene
     * @Author: shizt
     * @Date: 2020/1/10 10:59
     */
    Scene getSceneById(String uuid);

    /**
     * 采查场景 编辑
     * @Param: [scene]
     * @Return: com.chinobot.common.scene.Result
     * @Author: shizt
     * @Date: 2020/1/14 11:27
     */
    Result editScene(Scene scene);

    /**
     * 删除采查场景和相关采查任务
     * @Param: [uuid]
     * @Return: com.chinobot.common.scene.Result
     * @Author: shizt
     * @Date: 2020/1/10 16:11
     */
    Result delScene(String uuid);

}
