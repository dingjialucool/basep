package com.chinobot.aiuas.bot_collect.task.service.impl;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.aiuas.bot_collect.task.entity.Domain;
import com.chinobot.aiuas.bot_collect.task.entity.Scene;
import com.chinobot.aiuas.bot_collect.task.entity.Task;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainListVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.aiuas.bot_collect.task.mapper.DomainMapper;
import com.chinobot.aiuas.bot_collect.task.mapper.SceneMapper;
import com.chinobot.aiuas.bot_collect.task.mapper.TaskMapper;
import com.chinobot.aiuas.bot_collect.task.service.IDomainService;
import com.chinobot.aiuas.bot_collect.task.service.ISceneService;
import com.chinobot.aiuas.bot_collect.task.service.ITaskService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.service.impl.BaseService;
import org.kie.api.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查领域 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Service
public class DomainServiceImpl extends BaseService<DomainMapper, Domain> implements IDomainService {

    @Autowired
    private DomainMapper domainMapper;
    @Autowired
    private SceneMapper sceneMapper;
    @Autowired
    private ISceneService iSceneService;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private IDomainService domainService;

    @Override
    public List<DomainListVo> getDomainList(Map<String,Object> param) {
        return domainMapper.getDomain(param);
    }

    @Override
    public Domain getDomainById(String uuid) {
        QueryWrapper<Domain> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("uuid", "d_name", "icon")
                .eq("uuid", uuid)
                .eq("is_deleted", GlobalConstant.IS_DELETED_NO);

        return getOne(queryWrapper);
    }

    @Override
    public Result editDomain(Domain domain) {
    	
    	String domainId = domain.getUuid();
        QueryWrapper<Domain> domainQueryWrapper = new QueryWrapper<>();
        domainQueryWrapper.eq("d_name", domain.getDName());
        domainQueryWrapper.eq("is_deleted", GlobalConstant.IS_DELETED_NO);
        Domain one = getOne(domainQueryWrapper);

        if(!CommonUtils.isObjEmpty(one)){
            if(CommonUtils.isObjEmpty(domain.getUuid()) ||
                    (!CommonUtils.isObjEmpty(domain.getUuid()) &&
                            !one.getUuid().equals(domain.getUuid()))){
                return ResultFactory.error(23000, "领域名称已存在！", null);
            }
        }
        saveOrUpdate(domain);

        //增加domainCode字段
        if(CommonUtils.isEmpty(domainId)) {
        	if(CommonUtils.isEmpty(domain.getParentUuid())) {
            	domain.setDomainCode(domain.getUuid());
            }else {
            	Domain dos = domainService.getById(domain.getParentUuid());
            	domain.setDomainCode(dos.getDomainCode() + "-" + domain.getUuid());
            }
        }
        
        domainService.updateById(domain);
        
        return ResultFactory.success(domain.getUuid());
    }

    @Override
    public Result delDomain(String uuid) {
        Domain domain = new Domain();
        domain.setUuid(uuid);
        domain.setIsDeleted(true);
        domainMapper.updateById(domain);
        // 删除场景
        delSceneByDomainId(uuid);
        // 删除子领域、场景
        delDomainByParentId(uuid);

        return ResultFactory.success();
    }

    @Override
    public List<DomainSceneVo> getDomainSceneList(Map<String, Object> param) {
        return domainMapper.getDomainSceneList(param);
    }

    /**
     * 根据父id删除领域
     * @Param: [parentUuid]
     * @Return: void
     * @Author: shizt
     * @Date: 2020/1/10 16:42
     */
    private void delDomainByParentId(String parentUuid){
        QueryWrapper<Domain> domainQueryWrapper = new QueryWrapper();
        domainQueryWrapper.eq("parent_uuid", parentUuid);
        List<Domain> list = list(domainQueryWrapper);

        for (Domain d:list) {
            Domain domain = new Domain();
            domain.setUuid(d.getUuid());
            domain.setIsDeleted(GlobalConstant.IS_DELETED_YES);

            // 删除领域
            updateById(domain);
            // 删除领域下的场景
            delSceneByDomainId(d.getUuid());
            // 删除子领域
            delDomainByParentId(d.getUuid());
        }
    }

    /**
     * 根据领域id删除场景、任务
     * @Param: [domainUuid]
     * @Return: void
     * @Author: shizt
     * @Date: 2020/1/10 16:42
     */
    private void delSceneByDomainId(String domainUuid){
        QueryWrapper<Scene> sceneQueryWrapper = new QueryWrapper<>();
        sceneQueryWrapper.eq("domain_uuid", domainUuid);
        List<Scene> scenes = iSceneService.list(sceneQueryWrapper);
        for (Scene s:scenes) {
            iSceneService.delScene(s.getUuid());
        }
    }
}
