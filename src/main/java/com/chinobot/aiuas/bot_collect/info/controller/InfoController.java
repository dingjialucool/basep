package com.chinobot.aiuas.bot_collect.info.controller;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.dmg.pmml.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chinobot.aiuas.bot_collect.info.entity.dto.CollectObjectAndGeoDTO;
import com.chinobot.aiuas.bot_collect.info.entity.dto.CollectObjectDTO;
import com.chinobot.aiuas.bot_collect.info.entity.vo.CollectPolygon;
import com.chinobot.aiuas.bot_collect.info.entity.vo.FlightTaskListVo;
import com.chinobot.aiuas.bot_collect.info.entity.vo.GovAreaVo;
import com.chinobot.aiuas.bot_collect.info.service.IFlightTaskService;
import com.chinobot.aiuas.bot_collect.info.service.IInfoService;
import com.chinobot.aiuas.bot_collect.info.util.Tree;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 采查对象 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Api(tags="采查对象接口")
@RestController
@RequestMapping("/api/info/info")
public class InfoController extends BaseController {

	@Autowired
	private IInfoService infoService;
	@Autowired
	private IFlightTaskService iFlightTaskService;
	
	@ApiOperation(value = "树形采查对象", notes = "树形采查对象")
	@GetMapping("/getTreeObject")
	public Result<Tree<CollectObjectDTO>> getTreeObject() {
		
		return ResultFactory.success(infoService.getTreeObject());
	}
	
	@ApiOperation(value = "新增采查对象", notes = "新增采查对象")
	@PostMapping("/addCollectObject")
	public Result addCollectObject(@RequestBody CollectObjectAndGeoDTO dto) {
		
		infoService.addCollectObject(dto);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "回显采查对象", notes = "回显采查对象")
	@GetMapping("/getCollectObject")
	public Result<CollectObjectAndGeoDTO> getCollectObject(@ApiParam(name = "infoId", value = "采查对象主键", required = true) @RequestParam(value = "infoId", required = true) String infoId) {
		
		return ResultFactory.success(infoService.getCollectObject(infoId));
	}
	
	@ApiOperation(value = "删除采查对象", notes = "删除采查对象")
	@GetMapping("/removeCollectObject")
	public Result removeCollectObject(@ApiParam(name = "infoId", value = "采查对象主键", required = true) @RequestParam(value = "infoId", required = true) String infoId) {
		
		//判断是否可以删除（与策略木有关联或不在策略有效期内）
		infoService.getCollect(infoId);
		infoService.removeCollectObject(infoId);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "复选框选中的采查对象", notes = "复选框选中的采查对象")
	@PostMapping("/checkedCollectObject")
	public Result<List<CollectPolygon>> checkedCollectObject(@RequestBody List<String> idList) {
		
		List<CollectPolygon> checkedCollectObject = new ArrayList<CollectPolygon>();
		for (String id : idList) {
			checkedCollectObject.addAll(infoService.checkedCollectObject(id)) ;
		}
		
		return ResultFactory.success(checkedCollectObject);
	}
	
	@ApiOperation(value = "kml文件上传", notes = "参数- 文件对象file")
	@PostMapping("/kmlUpload")
	public Result kmlUpload(@RequestParam("file") MultipartFile file,
			@ApiParam(name = "parentUuid", value = "采查对象parentUuid", required = true) @RequestParam(value = "parentUuid", required = true) String parentUuid) throws IOException {
		
		List<CollectObjectAndGeoDTO> kmlUpload = infoService.kmlUpload(file);
		if (kmlUpload.size() == 0) {
			return ResultFactory.fail(null);
		}
		for (CollectObjectAndGeoDTO dto : kmlUpload) {
			dto.getCoAddress().setParentUuid(parentUuid);
			infoService.addCollectObject(dto);
		}
		List<Map> list = new ArrayList<>();
		Map map =new HashMap();
		map.put("uuid", 1);
		list.add(map);
		return ResultFactory.success (list);
	}
	
	@ApiOperation(value = "导出kml文件", notes = "参数- 采查对象主键")
	@GetMapping("/kmlUpload")
	public String exportKml(HttpServletResponse resp,@ApiParam(name = "id", value = "采查对象id", required = true) @RequestParam(value = "id", required = true) String id) {
		
		return infoService.exportKml(id,resp);
	}
	
	@ApiOperation(value = "获取行政区划code", notes = "参数- 采查对象主键")
	@GetMapping("/getCode")
	public Result<List<GovAreaVo>> getCode() {
		
		return ResultFactory.success(infoService.getCode());
	}
	
	@ApiOperation(value = "对象搜索", notes = "对象搜索")
	@GetMapping("/searchCollect")
	public Result searchCollect(@ApiParam(name = "infoName", value = "采查对象名称", required = false) @RequestParam(value = "infoName", required = false) String infoName) {
		
		return ResultFactory.success(infoService.searchCollect(infoName));
	}
	
	@ApiOperation(value = "获取标签", notes = "获取标签")
	@GetMapping("/getTags")
	public Result getTags() {
		
		return ResultFactory.success(infoService.getTags());
	}

	/**
	 * @Author: shizt
	 * @Date: 2020/2/24 17:25
	 */
	@ApiOperation("新增航班采查任务关联")
	@PostMapping("/addFlightTask")
	public Result addFlightTask(@RequestBody FlightTaskListVo flightTaskListVo){
		iFlightTaskService.addFlightTaskList(flightTaskListVo);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "根据采查对象uuid查询采查对象名称，地址以及图片和视频", notes = "对象数据")
	@GetMapping("/searchInfoCheckData")
	public Result searchInfoCheckData(@ApiParam(name = "infoId", value = "采查对象Id", required = true) @RequestParam(value = "infoId", required = true) String infoId) {
		//根据对象id，查询对象采查图片以及采查视频
		return ResultFactory.success(infoService.searchInfoCheckData(infoId));
	}
}
