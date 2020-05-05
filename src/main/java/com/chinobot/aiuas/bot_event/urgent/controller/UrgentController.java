package com.chinobot.aiuas.bot_event.urgent.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_event.urgent.entity.Urgent;
import com.chinobot.aiuas.bot_event.urgent.entity.dto.HistoryUrgentDTO;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.HistoryUrgentVo;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.PersonListVo;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.UrgentWorkStatusVo;
import com.chinobot.aiuas.bot_event.urgent.service.IUrgentService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chinobot.framework.web.controller.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 紧急调度表 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2020-03-19
 */
@Api(tags = "紧急调度接口")
@RestController
@RequestMapping("/api/bot/event/urgent")
public class UrgentController extends BaseController {
    @Autowired
    private IUrgentService iUrgentService;

    /**
     * @Author: shizt
     * @Date: 2020/3/19 14:31
     */
    @ApiOperation("新增紧急调度")
    @PostMapping("/add")
    public Result add(@RequestBody Urgent urgent) throws Exception {
        return ResultFactory.success(iUrgentService.add(urgent));
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/20 11:04
     */
    @ApiOperation("紧急调度分页")
    @GetMapping("/getPage")
    public Result<IPage> getPage(Page page, @RequestParam Map<String, String> params) {
        return ResultFactory.success(iUrgentService.getPage(page));
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/20 14:34
     */
    @ApiOperation("紧急调度详细")
    @GetMapping("/getInfo")
    public Result<Map> getInfo(String urgentId){
        return ResultFactory.success(iUrgentService.getInfo(urgentId));
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/20 16:53
     */
    @ApiOperation("紧急调度修改状态")
    @PostMapping("/editWorkStatus")
    public Result editWorkStatus(@RequestBody UrgentWorkStatusVo urgentWorkStatusVo) throws Exception {
        iUrgentService.editWorkStatus(urgentWorkStatusVo);
        return ResultFactory.success();
    }
    
    @ApiOperation(value = "历史调度列表查询", notes = "历史调度列表查询")
	@GetMapping("/getHistoryUrgentList")
	public Result<IPage<HistoryUrgentVo>> getHistoryUrgentList(HistoryUrgentDTO dto) {
		
		return ResultFactory.success(iUrgentService.getHistoryUrgentList(dto));
	}
    
    @ApiOperation(value = "获取紧急调度列表执行人与发起人", notes = "历史调度列表查询")
	@GetMapping("/getPersons")
	public Result<List<PersonListVo>> getPersons(@ApiParam(name = "type", value = "1 获取执行人列表 2 获取发起人列表", required = true) @RequestParam(value = "type", required = true) String type) {
		
		return ResultFactory.success(iUrgentService.getPersons(type));
	}
}
