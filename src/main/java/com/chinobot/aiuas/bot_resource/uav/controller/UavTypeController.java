package com.chinobot.aiuas.bot_resource.uav.controller;


import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeInfoVo;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeListVo;
import com.chinobot.aiuas.bot_resource.uav.service.IUavTypeService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chinobot.framework.web.controller.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 机型 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
@RestController
@RequestMapping("/api/bot/resource/uav/uav-type")
public class UavTypeController extends BaseController {

    @Autowired
    private IUavTypeService iUavTypeService;

    /**
     * 机型 列表
     * @Author: shizt
     * @Date: 2020/1/15 17:54
     */
    @ApiOperation(value = "列表", notes = "")
    @GetMapping("/list")
    public Result<List<UavTypeListVo>> list(@RequestParam Map<String, Object> param){
        return ResultFactory.success(iUavTypeService.getUavTypeList(param));
    }

    /**
     * 机型 详细
     * @Author: shizt
     * @Date: 2020/1/15 17:54
     */
    @ApiOperation(value = "详细", notes = "")
    @GetMapping("/")
    public Result<UavTypeInfoVo> info(@RequestParam Map<String, Object> param){
        return ResultFactory.success(iUavTypeService.getUavTypeInfo(param));
    }

    /**
     * 机型 编辑
     * @Author: shizt
     * @Date: 2020/1/16 10:00
     */
    @ApiOperation(value = "编辑", notes = "")
    @PostMapping("/edit")
    public Result edit(@RequestBody UavTypeInfoVo uavTypeInfoVo){
        return ResultFactory.success(iUavTypeService.editUavType(uavTypeInfoVo));
    }

    /**
     * 机型 删除
     * @Author: shizt
     * @Date: 2020/1/16 10:00
     */
    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/del")
    public Result del(String uuid){
        iUavTypeService.delUavType(uuid);

        return ResultFactory.success();
    }
}
