package com.chinobot.aiuas.bot_collect.task.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.Scene;
import com.chinobot.aiuas.bot_collect.task.entity.vo.SceneListVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查场景 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface SceneMapper extends IBaseMapper<Scene> {

    /**
     * 采查场景 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.task.entity.vo.SceneVo>
     * @Author: shizt
     * @Date: 2020/1/9 14:26
     */
    IPage<SceneListVo> getSceneList(Page page, @Param("p") Map<String,Object> param);
}
