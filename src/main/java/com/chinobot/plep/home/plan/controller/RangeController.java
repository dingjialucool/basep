package com.chinobot.plep.home.plan.controller;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IDeptService;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.area.entity.GovArea;
import com.chinobot.plep.home.area.service.IGovAreaService;
import com.chinobot.plep.home.event.service.IEventMainService;
import com.chinobot.plep.home.plan.entity.Range;
import com.chinobot.plep.home.plan.entity.SceneRange;
import com.chinobot.plep.home.plan.entity.vo.VoAddressBaseRangeGrids;
import com.chinobot.plep.home.plan.entity.vo.VoAreaPathParm;
import com.chinobot.plep.home.plan.entity.vo.VoAreas;
import com.chinobot.plep.home.plan.entity.vo.VoPathPlan;
import com.chinobot.plep.home.plan.service.IRangeDeptService;
import com.chinobot.plep.home.plan.service.IRangeService;
import com.chinobot.plep.home.plan.service.ISceneRangeService;
import com.chinobot.plep.home.scene.service.ISceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 巡查范围表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-06-16
 */
@Api(tags= {"巡查范围接口"})
@RestController
@RequestMapping("/api/plan/range")
public class RangeController extends BaseController {
	@Autowired
	private IRangeService rangeService;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IEventMainService eventMainService;
	@Autowired
	private ISceneService sceneService;
	@Autowired
	private ISceneRangeService sceneRangeService;
	@Autowired
	private IGovAreaService govAreaService;
	
//	@RequestMapping("/getRangePage")
	@ApiOperation(value = "获取范围列表", notes = "参数对象param")
	@GetMapping("/getRangePage")
	public Result getRangePage(Page page, @RequestParam Map<String, Object> param) {
//		Person person = ThreadLocalUtil.getResources();
//		if(person != null) {
//			person = personService.getById(person.getUuid());
//		}
//		param.put("deptId",person.getDept().getUuid());
//		if(ThreadLocalUtil.isShenz() || ThreadLocalUtil.isSystemRole()) {
//			param.remove("deptId");//超级管理员查看所有
//		}
//		if(!ThreadLocalUtil.isShenz()) {
//			param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
//		}else if(String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
//			param.remove("deptId");
//		}
		if(param.get("deptId") !=null && StringUtils.isBlank(param.get("deptId").toString())) {
			param.put("deptId", ThreadLocalUtil.getResources().getDeptId());
		}
		
		return ResultFactory.success(rangeService.getRangeAddrPage(page, param));
	}
	
	@ApiOperation(value = "获取单个范围数据", notes = "参数-范围主键")
	@GetMapping("")
//	@RequestMapping("")
	public Result getRange(String uuid) {
		Map map = new HashMap();
		if(CommonUtils.isNotEmpty(uuid)) {
			map.put("info",rangeService.getRangeAddrById(uuid));
			//已关联的场景
			QueryWrapper<SceneRange> sceneRangeWrapper = new QueryWrapper<SceneRange>();
			sceneRangeWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			sceneRangeWrapper.eq("range_id",uuid);
			
			map.put("scenes", sceneRangeService.list(sceneRangeWrapper));
		}
		// 部门列表
		QueryWrapper<Dept> deptWrapper = new QueryWrapper<>();
		deptWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		map.put("deptList", deptService.list(deptWrapper));
		// 已关联网格的部门列表
//		Map paramDept = new HashMap<>();
//		paramDept.put("data_status", GlobalConstant.DATA_STATUS_VALID);
//		paramDept.put("range_id", uuid);
//		map.put("deptGridList", rangeDeptService.getRangeGrid(paramDept));
		Person person = ThreadLocalUtil.getResources();
		if(person != null) {
			person = personService.getById(person.getUuid());
			map.put("deptGridList", deptService.getById(person.getDeptId()));
		}
		
		Map param = new HashMap<>();
		param.put("busId", uuid);
		param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
		param.put("module", "range_album");
		map.put("files", fileBusService.getFileIdByBusId(param));
		//人员
		map.put("personList", eventMainService.getPersonList());
		//场景
		map.put("sceneList", sceneService.getAllScene());
		return ResultFactory.success(map);
	}
	
	@ApiOperation(value = "监控点批量导入-场景分派人", notes = "无参数")
	@PostMapping("/getScenesAndPersons")
//	@RequestMapping("/getScenesAndPersons")
	public Result getScenesAndPersons() {
		Map map = new HashMap();
		//人员
		map.put("personList", eventMainService.getPersonList());
		//场景
		map.put("sceneList", sceneService.getAllScene());
		return ResultFactory.success(map);
	}
	
	@ApiOperation(value = "获取单个范围 - N(暂未使用)", notes = "参数-范围id")
	@GetMapping("/getOnlyRange")
	public Result getOnlyRange(String uuid) {
		return ResultFactory.success(rangeService.getRangeAddrById(uuid));
	}
	
	@ApiOperation(value = "范围新增修改", notes = "参数-范围地址包装类vo")
	@PostMapping("/edit")
//	@RequestMapping("/edit")
	public Result edit(@RequestBody VoAddressBaseRangeGrids<Range> vo) throws Exception {
		
		return rangeService.editRange(vo);
	}
	
	@ApiOperation(value = "范围删除", notes = "参数-范围range")
	@PostMapping("/del")
//	@RequestMapping("/del")
	public Result del(@RequestBody Range range) {
		return rangeService.delRange(range);
	}
	
	@ApiOperation(value = "测试 -C(仅测试使用)", notes = "参数-无")
	@GetMapping("/test")
	public Result test() throws Exception {
		rangeService.test();
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "获取行政区划边界", notes = "参数为部门deptId,self")
	@GetMapping("/getAreaPolygon")
	public Result getAreaPolygon(String deptId, String self) throws IOException {
		//String[] paths = {"map"+File.separator+"深圳.txt","map"+File.separator+"宝安区.txt","map"+File.separator+"大鹏新区.txt","map"+File.separator+"福田区.txt","map"+File.separator+"光明区.txt","map"+File.separator+"龙岗区.txt","map"+File.separator+"龙华区.txt","map"+File.separator+"罗湖区.txt","map"+File.separator+"南山区.txt","map"+File.separator+"坪山区.txt","map"+File.separator+"盐田区.txt"};
		//List<Dept> deptList = deptService.list();
		List<String> paths = new ArrayList<String>();
		if("self".equals(self) || ThreadLocalUtil.getResources().getDeptId().equals(deptId)) {
			deptId = ThreadLocalUtil.getResources().getDeptId();
			//所有子节点
			QueryWrapper<Dept> deptWrapper = new QueryWrapper<Dept>();
			deptWrapper.eq("parent_id", deptId);
			deptWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			List<Dept> list = deptService.list(deptWrapper);
			for(Dept d : list) {
				GovArea govArea = govAreaService.getById(d.getAreaId());
				if(govArea != null) {
					paths.add(govArea.getTargetBoundary());
				}
				
			}
		}
		if(StringUtils.isNotBlank(deptId) && paths.size()==0) {
			Dept byId = deptService.getById(deptId);
			GovArea govArea = govAreaService.getById(byId.getAreaId());
			if(govArea != null) {
				paths.add(govArea.getTargetBoundary());
			}
		}
		
		List<List<List>> result = new ArrayList<List<List>>();
		for(String path : paths) {
//			ClassPathResource resource = new ClassPathResource(path);
//			InputStream inputStream = resource.getInputStream();
//			List<String> readLines = IOUtils.readLines(inputStream);
			if(path.length()>0) {
				//String fileString = readLines.get(0);
		        String[] polygonStringArray = path.split("#");
		        for(String polygonString : polygonStringArray) {
		        	List<List> polygon = new ArrayList<List>();
		        	String[] pointStringArray = polygonString.split(",");
		        	for(int i=0;i<pointStringArray.length;i=i+2) {
		        		List point = new ArrayList();
		        		point.add(Double.valueOf(pointStringArray[i]));
		        		point.add(Double.valueOf(pointStringArray[i+1]));
		        		polygon.add(point);
		        	}
		        	result.add(polygon);
		        }
			}
	        
		}
		
		return ResultFactory.success(result);
	}
	
	
	@ApiOperation(value = "保存区域，输出参数，区域路径", notes = "参数-区域，参数，路径包装类vo")
	@PostMapping("/area/save")
//	@RequestMapping("/area/save")
	public Result saveArea(@RequestBody VoAreas vo) {
		List<VoAreaPathParm> list = vo.getVo();
		boolean bo =false;
		for (VoAreaPathParm voAreaPathParm : list) {
			 bo = rangeService.saveArea(voAreaPathParm);
		}
		return ResultFactory.success(bo);
	}
	
	
	@ApiOperation(value = "保存相机参数，飞行参数", notes = "参数-相机参数，飞行参数包装类vo")
	@PostMapping("/saveConfig")
//	@RequestMapping("/saveConfig")
	public Result saveConfig(@RequestBody VoPathPlan vo) {
		
		return ResultFactory.success(rangeService.saveConfig(vo));
	}
	
	
	@ApiOperation(value = "航线重新规划，删除范围下所有区域、路线、参数等", notes = "参数-param")
	@GetMapping("/deleteAll")
//	@RequestMapping("/deleteAll")
	public Result deleteAll(@RequestParam Map<String,Object> param) {
		
		return ResultFactory.success(rangeService.deleteAll(param));
	}
}
