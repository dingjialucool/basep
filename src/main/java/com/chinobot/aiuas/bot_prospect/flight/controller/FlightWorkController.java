package com.chinobot.aiuas.bot_prospect.flight.controller;


import java.util.List;
import java.util.Map;

import com.chinobot.aiuas.bot_prospect.flight.entity.vo.WorkFilesVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_prospect.flight.entity.FlightWork;
import com.chinobot.aiuas.bot_prospect.flight.entity.dto.UavFlightWorkParamDto;
import com.chinobot.aiuas.bot_prospect.flight.entity.dto.WorkStateDto;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.FlightWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavFlightWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightWorkService;
import com.chinobot.aiuas.bot_prospect.flight.service.TestWarningService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 航班作业 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-02-24
 */
@Api(tags="航班作业接口")
@RestController
@RequestMapping("/api/bot/prospect/flight-work")
public class FlightWorkController extends BaseController {

    @Autowired
    private IFlightWorkService iFlightWorkService;
    @Autowired
    private TestWarningService testService;
//    /**
//     * @Author: shizt
//     * @Date: 2020/2/25 10:48
//     */
//    @ApiOperation("航班作业 分页列表")
//    @GetMapping("/getPage")
//    public Result<IPage<FlightWorkPageVo>> getPage(Page page, @RequestParam Map<String, Object> param){
//        return ResultFactory.success(iFlightWorkService.getPage(page, param));
//    }

    @ApiOperation("航班作业数据 策略列表/无人机人员状态/统计/作业分页")
    @GetMapping("/getFlightWorkData")
    public Result<Map<String, Object>> getFlightWorkData(Page page, @RequestParam Map<String, Object> param, String... refreshModule){
        return ResultFactory.success(iFlightWorkService.getFlightWorkData(page, param, refreshModule));
    }
    
    @ApiImplicitParams({
    	@ApiImplicitParam(name="workId",value="作业主键",required=true,paramType="query"),
        @ApiImplicitParam(name="imgsPath",value="预警图片文件夹",required=true,paramType="query"),
    })
    @ApiOperation("模拟推送预警")
    @GetMapping("/testWarningPost")
    public Result<String> testWarningPost(String workId, String imgsPath){
    	return ResultFactory.success(testService.postWarnings(workId, imgsPath));
    }
    
    @ApiImplicitParams({
    	@ApiImplicitParam(name="workId",value="作业主键",required=true,paramType="query"),
        @ApiImplicitParam(name="imgsPath",value="预警图片文件夹",required=true,paramType="query"),
    })
    @ApiOperation("模拟推送预警Pro")
    @GetMapping("/testWarningPostPro")
    public Result<String> testWarningPostPro(String workId, String imgsPath){
    	return ResultFactory.success(testService.postWarningsPro(workId, imgsPath));
    }
    
    @ApiImplicitParams({
    	@ApiImplicitParam(name="workId",value="作业主键",required=true,paramType="query"),
        @ApiImplicitParam(name="imgsPath",value="预警图片文件夹",required=true,paramType="query"),
    })
    @ApiOperation("模拟推送基准图预警Pro")
    @GetMapping("/testWarningPostProBase")
    public Result<String> testWarningPostProBase(String workId, String imgsPath){
    	return ResultFactory.success(testService.postWarningsProPro(workId, imgsPath));
    }
    
    @ApiImplicitParams({
    	@ApiImplicitParam(name="workIds",value="作业主键,多个逗号分隔",required=true,paramType="query"),
        @ApiImplicitParam(name="type",value="类型：1-车辆数  2-工程进度、人数",required=true,paramType="query"),
        @ApiImplicitParam(name="stage",value="阶段，类型填2时必填，码值参考bot_project_stage",required=false,paramType="query"),
    })
    @ApiOperation("模拟推送采查结果")
    @GetMapping("/postCollectData")
    public Result<String> postCollectData(String workIds, String type, String stage){
    	return ResultFactory.success(testService.postCollectData(workIds, type, stage));
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/6 11:44
     */
    @ApiOperation("航班调度数据")
    @GetMapping("/getFlightTaskData")
    public Result<Map<String, Object>> getFlightTaskData(@RequestParam Map<String, Object> param, String... refreshModule){
        return ResultFactory.success(iFlightWorkService.getFlightTaskData(param, refreshModule));
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/6 16:33
     */
    @ApiOperation("编辑航班作业")
    @PostMapping("/edit")
    public Result edit(@RequestBody FlightWorkVo flightWorkVo){
        iFlightWorkService.edit(flightWorkVo);
        return ResultFactory.success();
    }


    /**
     * @Author: shizt
     * @Date: 2020/3/6 16:33
     */
    @ApiOperation("更改航班日期")
    @PostMapping("/updateWorkDate")
    public Result updateWorkDate(@RequestBody FlightWork flightWork){
        return ResultFactory.success(iFlightWorkService.updateById(flightWork));
    }
    
    @ApiOperation("无人机获取任务")
    @GetMapping("/getUavWorks")
    public Result<List<UavFlightWorkVo>> getUavWorks(UavFlightWorkParamDto dto){
    	if(StringUtils.isBlank(dto.getWorkStatus())) {
    		dto.setWorkStatus("2");
    	}
    	return ResultFactory.success(iFlightWorkService.getUavWorks(dto));
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/9 15:35
     */
    @ApiOperation("航班作业详情")
    @GetMapping("/getWorkInfo")
    public Result<Map<String, Object>> getWorkInfo(@RequestParam Map<String, Object> param, String... refreshModule){
        return ResultFactory.success(iFlightWorkService.getWorkInfo(param, refreshModule));
    }

    /**
     * @throws Exception 
     * @Author: shizt
     * @Date: 2020/3/10 9:37
     */
    @ApiOperation("办理")
    @PostMapping("/flightWorkHandle")
    public Result flightWorkHandle(@RequestBody WorkFilesVo workFilesVo) throws Exception{
        iFlightWorkService.doneWork(workFilesVo);
        return ResultFactory.success();
    }
    
    @ApiOperation("改变作业状态, code = 200成功，code = 233 提示错误消息msg")
    @PostMapping("/changeWorkState")
    public Result changeWorkState(@RequestBody WorkStateDto dto) {
    	String rs = iFlightWorkService.changeWorkState(dto);
    	if(!"true".equals(rs)) {
    		return ResultFactory.error( 233, rs, null);
    	}
    	return ResultFactory.success();
    }

    @ApiOperation("每日作业数量")
    @GetMapping("/getWorkCount")
    public Result getWorkCount(String pageType, String month){
        return ResultFactory.success(iFlightWorkService.getWorkCountByMonth(pageType, month));
    }
}
