package com.chinobot.aiuas.bot_collect.task.mapper;

import com.chinobot.aiuas.bot_collect.task.entity.Domain;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainListVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查领域 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface DomainMapper extends IBaseMapper<Domain> {

    /**
     * 采查领域列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.task.entity.vo.DomainListVo>
     * @Author: shizt
     * @Date: 2020/1/9 10:16
     */
    List<DomainListVo> getDomain(@Param("p") Map<String,Object> param);

    /**
     * 领域场景 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo>
     * @Author: shizt
     * @Date: 2020/1/10 17:59
     */
    List<DomainSceneVo> getDomainSceneList(@Param("p") Map<String,Object> param);

}
