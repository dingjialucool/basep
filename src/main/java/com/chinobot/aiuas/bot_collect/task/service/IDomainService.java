package com.chinobot.aiuas.bot_collect.task.service;

import com.chinobot.aiuas.bot_collect.task.entity.Domain;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainListVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查领域 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface IDomainService extends IBaseService<Domain> {

    /**
     * 采查领域 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.task.entity.vo.DomainListVo>
     * @Author: shizt
     * @Date: 2020/1/9 10:15
     */
    List<DomainListVo> getDomainList(Map<String,Object> param);

    /**
     * 根据主键获取采查领域
     * @Param: [uuid]
     * @Return: com.chinobot.aiuas.bot_collect.task.entity.Domain
     * @Author: shizt
     * @Date: 2020/1/10 11:09
     */
    Domain getDomainById(String uuid);

    /**
     * 采查领域 编辑
     * @Param: [domain]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/14 11:27
     */
    Result editDomain(Domain domain);

    /**
     * 删除采查领域和相关采查场景
     * @Param: [uuid]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/10 16:11
     */
    Result delDomain(String uuid);

    /**
     * 领域场景 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo>
     * @Author: shizt
     * @Date: 2020/1/10 17:58
     */
    List<DomainSceneVo> getDomainSceneList(Map<String, Object> param);
}
