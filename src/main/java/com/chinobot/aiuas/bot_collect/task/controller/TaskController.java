package com.chinobot.aiuas.bot_collect.task.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.Domain;
import com.chinobot.aiuas.bot_collect.task.entity.Task;
import com.chinobot.aiuas.bot_collect.task.entity.vo.TaskInfoVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.TaskListVo;
import com.chinobot.aiuas.bot_collect.task.service.ITaskService;
import com.chinobot.cityle.base.entity.vo.PersonOrganizationVo;
import com.chinobot.cityle.base.service.IDeptService;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chinobot.framework.web.controller.BaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查任务 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Api(tags= {"采查任务接口"})
@RestController
@RequestMapping("/api/bot/collect/task/task")
public class TaskController extends BaseController {

    @Autowired
    private ITaskService iTaskService;
    @Autowired
    private IPersonService iPersonService;
    @Autowired
    private IDeptService iDeptService;

    /**
     * 采查任务 列表
     * @Param: [page, param]
     * @Return: com.chinobot.common.domain.Result<com.baomidou.mybatisplus.core.metadata.IPage<com.chinobot.aiuas.bot_collect.task.entity.vo.TaskListVo>>
     * @Author: shizt
     * @Date: 2020/1/13 10:01
     */
    @ApiOperation(value = "列表", notes = "")
    @GetMapping("/list")
    public Result<IPage<TaskListVo>> list(Page page ,@RequestParam Map<String, Object> param){
        if(CommonUtils.isObjEmpty(param.get("sceneUuid"))){
            return ResultFactory.error(1001, "sceneUuid is empty!", null);
        }
        return ResultFactory.success(iTaskService.getTaskList(page, param));
    }

    /**
     * 详细
     * @Param: [param]
     * @Return: com.chinobot.common.domain.Result<com.chinobot.aiuas.bot_collect.task.entity.vo.DomainListVo>
     * @Author: shizt
     * @Date: 2020/1/9 11:42
     */
    @ApiOperation(value = "详细", notes = "")
    @GetMapping("")
    public Result<TaskInfoVo> info(@RequestParam Map<String, Object> param){
        return ResultFactory.success(iTaskService.getTaskInfo(param));
    }

    /**
     * 编辑
     * @Param: [task]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/14 11:47
     */
    @ApiOperation(value = "编辑", notes = "")
    @PostMapping("/edit")
    public Result edit(@RequestBody TaskInfoVo taskInfoVo){
        return ResultFactory.success(iTaskService.editTask(taskInfoVo));
    }

    /**
     * 负责人列表
     * @Param: [organization]
     * @Return: com.chinobot.common.domain.Result<java.util.List<com.chinobot.cityle.base.entity.vo.PersonOrganizationVo>>
     * @Author: shizt
     * @Date: 2020/1/14 20:04
     */
    @ApiOperation(value = "负责人列表", notes = "")
    @GetMapping("/personList")
    public Result<List<PersonOrganizationVo>> getPersonList(String organization){
        return ResultFactory.success(iPersonService.getPersonByOrganization(organization));
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/4 15:14
     */
    @ApiOperation("同一机构和责任人类型下部门是否唯一")
    @GetMapping("/checkDeptUnique")
    public Result<List<String>> checkDeptUnique(String unitPersonType, String organization){
        return ResultFactory.success(iDeptService.checkDeptUnique(unitPersonType, organization));
    }

}
