package com.chinobot.aiuas.bot_collect.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.constant.TaskConstant;
import com.chinobot.aiuas.bot_collect.task.entity.Domain;
import com.chinobot.aiuas.bot_collect.task.entity.Task;
import com.chinobot.aiuas.bot_collect.task.entity.Unit;
import com.chinobot.aiuas.bot_collect.task.entity.dto.UnitDto;
import com.chinobot.aiuas.bot_collect.task.entity.vo.TaskInfoVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.TaskListVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.UnitVo;
import com.chinobot.aiuas.bot_collect.task.mapper.TaskMapper;
import com.chinobot.aiuas.bot_collect.task.service.ITaskService;
import com.chinobot.aiuas.bot_collect.task.service.IUnitService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查任务 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Service
public class TaskServiceImpl extends BaseService<TaskMapper, Task> implements ITaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private IFileBusService iFileBusService;
    @Autowired
    private IUnitService iUnitService;
    /**
     * 文件模块名
     */
    final static String FILE_MODULE = "collect_task_album";

    @Override
    public IPage<TaskListVo> getTaskList(Page page, Map<String, Object> param) {
        return taskMapper.getTaskList(page, param);
    }

    @Override
    public TaskInfoVo getTaskInfo(Map<String, Object> param) {
        if (((String) param.get("type")).equals("add")) {
            return taskMapper.getDomainSceneBySceneId((String) param.get("sceneUuid"));
        } else {
            // 任务
            TaskInfoVo taskInfoVo = taskMapper.getTaskById((String) param.get("uuid"));
            // 单位
            Map unitParam = new HashMap();
            unitParam.put("taskUuid", taskInfoVo.getUuid());
            List<UnitDto> units = iUnitService.getUtilList(unitParam);

            UnitVo unitVo = new UnitVo();
            for (UnitDto u: units) {
                if(u.getUnitType().equals(TaskConstant.UNIT_TYPE_MANAGE)){
                    unitVo.setManageUuid(u.getUuid());
                    unitVo.setManageUnitType(u.getOrganization());
                    unitVo.setManagePersonType(u.getUnitPersonType());
                    unitVo.setManagePersonUuid(u.getUnitPersonUuid());
                    unitVo.setManagePersonName(u.getPname());
                }else if(u.getUnitType().equals(TaskConstant.UNIT_TYPE_AGAINST)){
                    unitVo.setAgainstUuid(u.getUuid());
                    unitVo.setAgainstUnitType(u.getOrganization());
                    unitVo.setAgainstPersonType(u.getUnitPersonType());
                    unitVo.setAgainstPersonUuid(u.getUnitPersonUuid());
                    unitVo.setAgainstPersonName(u.getPname());
                }else if(u.getUnitType().equals(TaskConstant.UNIT_TYPE_RELIEF)){
                    unitVo.setReliefUuid(u.getUuid());
                    unitVo.setReliefUnitType(u.getOrganization());
                    unitVo.setReliefPersonType(u.getUnitPersonType());
                    unitVo.setReliefPersonUuid(u.getUnitPersonUuid());
                    unitVo.setReliefPersonName(u.getPname());
                }
            }
            taskInfoVo.setUnitInfo(unitVo);
            // 相册
            Map fileParam = new HashMap<>();
            fileParam.put("busId", (String) param.get("uuid"));
            fileParam.put("module", FILE_MODULE);
            taskInfoVo.setFiles(iFileBusService.getFileBusList(fileParam));

            return taskInfoVo;
        }
    }

    @Override
    public Result editTask(TaskInfoVo taskInfoVo) {
        // 删除
        if (!CommonUtils.isObjEmpty(taskInfoVo.getIsDeleted()) &&
                taskInfoVo.getIsDeleted().equals(GlobalConstant.IS_DELETED_YES)) {
            delTask(taskInfoVo.getUuid());
            return ResultFactory.success(taskInfoVo.getUuid());
        }

        // 保存任务
        Task task = new Task();
        task.setUuid(taskInfoVo.getUuid())
                .setSceneUuid(taskInfoVo.getSceneUuid())
                .setTName(taskInfoVo.getTName())
                .setResultName(taskInfoVo.getResultName())
                .setResultType(taskInfoVo.getResultType())
                .setDangerGrade(taskInfoVo.getDangerGrade())
                .setAlgorithmUuid(taskInfoVo.getAlgorithmUuid())
                .setCollectResult(taskInfoVo.getCollectResult());
        saveOrUpdate(task);

        // 保存单位
        UnitVo unitInfo = taskInfoVo.getUnitInfo();
        if (!CommonUtils.isObjEmpty(unitInfo)) {
            Unit manageUnit = new Unit();
            manageUnit.setTaskUuid(task.getUuid())
                    .setUuid(unitInfo.getManageUuid())
                    .setUnitPersonType(unitInfo.getManagePersonType())
                    .setUnitPersonUuid(unitInfo.getManagePersonUuid())
                    .setOrganization(unitInfo.getManageUnitType())
                    .setUnitType(TaskConstant.UNIT_TYPE_MANAGE);
            iUnitService.saveOrUpdate(manageUnit);

            Unit againstUnit = new Unit();
            againstUnit.setTaskUuid(task.getUuid())
                    .setUuid(unitInfo.getAgainstUuid())
                    .setUnitPersonType(unitInfo.getAgainstPersonType())
                    .setUnitPersonUuid(unitInfo.getAgainstPersonUuid())
                    .setOrganization(unitInfo.getAgainstUnitType())
                    .setUnitType(TaskConstant.UNIT_TYPE_AGAINST);
            iUnitService.saveOrUpdate(againstUnit);

            Unit reliefUnit = new Unit();
            reliefUnit.setTaskUuid(task.getUuid())
                    .setUuid(unitInfo.getReliefUuid())
                    .setUnitPersonType(unitInfo.getReliefPersonType())
                    .setUnitPersonUuid(unitInfo.getReliefPersonUuid())
                    .setOrganization(unitInfo.getReliefUnitType())
                    .setUnitType(TaskConstant.UNIT_TYPE_RELIEF);
            iUnitService.saveOrUpdate(reliefUnit);
        }

        // 保存文件关联
        iFileBusService.saveFileBusList(taskInfoVo.getFiles(), task.getUuid(), "collect_task_album");

        return ResultFactory.success(task.getUuid());
    }

    @Override
    public Result delTaskBySceneUuid(String sceneUuid) {
        QueryWrapper<Task> taskQueryWrapper = new QueryWrapper();
        taskQueryWrapper.eq("scene_uuid", sceneUuid)
                        .eq("is_deleted", GlobalConstant.IS_DELETED_NO);
        List<Task> tasks = list(taskQueryWrapper);
        for (Task t: tasks) {
            // 删除任务
            Task task = new Task();
            task.setUuid(t.getUuid())
                .setIsDeleted(GlobalConstant.IS_DELETED_YES);
            updateById(task);
            // 删除防治救单位
            iUnitService.delUnitByTaskUuid(t.getUuid());
            // 删除相册
            iFileBusService.delFileBus(t.getUuid(), FILE_MODULE);
        }
        return ResultFactory.success();
    }

    /**
     * 刪除采查任务
     * @Param: [uuid]
     * @Return: void
     * @Author: shizt
     * @Date: 2020/1/14 18:36
     */
    private void delTask(String uuid) {
        // 删除任务
        Task task = new Task();
        task.setUuid(uuid)
            .setIsDeleted(GlobalConstant.IS_DELETED_YES);
        updateById(task);
        // 删除防治救单位
        iUnitService.delUnitByTaskUuid(uuid);
        // 删除相册
        iFileBusService.delFileBus(uuid, FILE_MODULE);
    }

}
