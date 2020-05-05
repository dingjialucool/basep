package com.chinobot.aiuas.bot_collect.task.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.aiuas.bot_collect.task.entity.Domain;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainListVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.aiuas.bot_collect.task.service.IDomainService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chinobot.framework.web.controller.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查领域 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Api(tags= {"采查领域接口"})
@RestController
@RequestMapping("/api/bot/collect/task/domain")
public class DomainController extends BaseController {

    @Autowired
    private IDomainService iDomainService;

    /**
     * 列表
     * @Param: [param]
     * @Return: com.chinobot.common.domain.Result<java.util.List<com.chinobot.aiuas.bot_collect.task.entity.vo.DomainListVo>>
     * @Author: shizt
     * @Date: 2020/1/9 10:18
     */
    @ApiOperation(value = "列表", notes = "")
    @GetMapping("/list")
    public Result<List<DomainListVo>> getList(@RequestParam Map<String,Object> param){
        return ResultFactory.success(iDomainService.getDomainList(param));
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
    public Result<Domain> info(String uuid){
        return ResultFactory.success(iDomainService.getDomainById(uuid));
    }

    /**
     * 编辑
     * @Param: [domain]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/9 10:19
     */
    @ApiOperation(value = "编辑", notes = "")
    @PostMapping("/edit")
    public Result edit(@RequestBody Domain domain){
        return iDomainService.editDomain(domain);
    }

    /**
     * 删除
     * @Param: [uuid]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/10 18:02
     */
    @ApiOperation(value = "删除")
    @PostMapping("/del")
    public Result del(@RequestParam String uuid){
        return ResultFactory.success(iDomainService.delDomain(uuid));
    }

    /**
     * 领域场景 列表
     * @Param: [param]
     * @Return: com.chinobot.common.domain.Result<com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo>
     * @Author: shizt
     * @Date: 2020/1/10 18:02
     */
    @ApiOperation(value = "领域场景列表", notes = "")
    @GetMapping("/domainSceneList")
    public Result<DomainSceneVo> getDomainSceneList(@RequestParam Map<String,Object> param){
        return ResultFactory.success(iDomainService.getDomainSceneList(param));
    }
}
