package com.chinobot.plep.home.point.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.util.FastDFSClient;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.FileUtil;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.event.service.IEventMainService;
import com.chinobot.plep.home.point.entity.FixedPoint;
import com.chinobot.plep.home.point.entity.ScenePoint;
import com.chinobot.plep.home.point.entity.dto.FileBusDto;
import com.chinobot.plep.home.point.entity.dto.FixedPointDto;
import com.chinobot.plep.home.point.entity.vo.LnglatVo;
import com.chinobot.plep.home.point.entity.vo.VoPldr;
import com.chinobot.plep.home.point.entity.vo.VoPoint;
import com.chinobot.plep.home.point.entity.vo.VoRoute;

import com.chinobot.plep.home.point.mapper.FixedFlyPointMapper;
import com.chinobot.plep.home.point.mapper.FixedPointMapper;

import com.chinobot.plep.home.point.service.IFixedFlyPointService;

import com.chinobot.plep.home.point.service.IFixedPointService;
import com.chinobot.plep.home.point.service.IScenePointService;
import com.chinobot.plep.home.route.entity.Route;
import com.chinobot.plep.home.route.service.ICheckPointService;
import com.chinobot.plep.home.scene.service.ISceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 定点场景关系表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-08-22
 */
@Api(tags= {"定点接口"})
@RestController
@RequestMapping("/api/point/fixed-point")
public class FixedPointController extends BaseController {
	
	@Autowired
	private IFixedPointService fixedPointService;
	@Autowired
	private IScenePointService scenePointService;
	@Autowired
	private IEventMainService eventMainService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private ISceneService sceneService;
	@Autowired
	private ICheckPointService checkPointService;
	@Value("${config.zipPath}")
	private String zipTemp;
	@Autowired
	private FastDFSClient fastDFSClient;
	@Autowired
	private FileClient fileClient;

	@Autowired
	private FixedFlyPointMapper fixedFlyPointMapper;
	@Autowired
	private FixedPointMapper fixedPointMapper;

	@Autowired
	private IFixedFlyPointService fixedFlyPointService;

	
	@ApiOperation(value = "获取所有定点", notes = "参数对象param")
	@GetMapping("/getAllPoint")
//	@RequestMapping("/getAllPoint")
	public Result getAllPoint(@RequestParam Map<String, String> param) {
		if(StringUtils.isBlank(param.get("deptId"))) {
			param.put("deptId", ThreadLocalUtil.getResources().getDeptId());
		}
		return ResultFactory.success(fixedPointService.getAllPoint(param));
	}
	
	@ApiOperation(value = "获取航线规划的定点分页", notes = "参数deptId、nameLike")
	@GetMapping("/getPointPageForRoute")
	public Result getPointPageForRoute(Page page, @RequestParam Map<String, String> param) {
		if(StringUtils.isBlank(param.get("deptId"))) {
			param.put("deptId", ThreadLocalUtil.getResources().getDeptId());
		}
		return ResultFactory.success(fixedPointService.getAllPoint(page, param));
	}

	
	@ApiOperation(value = "获取定点分页列表", notes = "参数- 分页page，Map param")
	@GetMapping("/getPointPage")
	public Result getPointPage(Page page, @RequestParam Map<String, Object> param) {
		
			param.put("deptId",ThreadLocalUtil.getResources().getDept().getUuid());
		return ResultFactory.success(fixedPointService.getPointPage(page,  param));
	}
	
	
	@ApiOperation(value = "获取所有定点(没有分页)", notes = "参数- Map param")
	@GetMapping("/getAllPointNoPage")
//	@RequestMapping("/getAllPointNoPage")
	public Result getAllPointNoPage(@RequestParam Map<String, Object> param) {

			param.put("deptId",ThreadLocalUtil.getResources().getDept().getUuid());

		return ResultFactory.success(fixedPointService.getAllPointNoPage(param));
	}
	
	
	@ApiOperation(value = "查询单个定点信息", notes = "定点主键uuid")
	@GetMapping("")
//	@RequestMapping("")
	public Result point(String uuid) {
		Map map = new HashMap();
		if(CommonUtils.isNotEmpty(uuid)) {
			map.put("info",fixedPointService.getById(uuid));
			//已关联的场景
			QueryWrapper<ScenePoint> sceneRangeWrapper = new QueryWrapper<ScenePoint>();
			sceneRangeWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			sceneRangeWrapper.eq("point_id", uuid);
			map.put("scenes",scenePointService.list(sceneRangeWrapper));
		}
		Map param = new HashMap<>();
		param.put("busId", uuid);
		param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
		param.put("module", "point_album");
		map.put("files", fileBusService.getFileIdByBusId(param));
		//人员
		map.put("personList", eventMainService.getPersonList());
		//场景
		map.put("sceneList", sceneService.getAllScene());
		//航点集合
		QueryWrapper wrapper = CommonUtils.getEqQueryWrapper("data_status", "fixed_id", "1", uuid);
		wrapper.orderByAsc("sort");
		map.put("flyPointList", fixedFlyPointService.list(wrapper));
		return ResultFactory.success(map);
	}
	
	

	
	@ApiOperation(value = "查询单个定点信息和定点的图片信息", notes = "参数- 定点pointId")
	@GetMapping("/getPointImg")
	public Result getPointImg(@RequestParam(name = "pointId") String uuid) {
		Map map = new HashMap();
		if(CommonUtils.isNotEmpty(uuid)) {
			Map<String,Object> pointMap = new HashMap();
			pointMap.put("uuid", uuid);
			List<Map> list = fixedPointService.getPointInfo(pointMap);
			Map map2 = null;
			if(list.size()>0) {
				map2 = list.get(0);
			}
			Map p = new HashMap<>();
			p.put("busId", uuid);
			p.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
			p.put("module", "point_album");
			map2.put("files", fileBusService.getFileIdByBusId(p));
			
			map.put("info", map2);
			
			//查询每个航点的基准图
			List<Map> basicImgList = fixedPointMapper.getBasicImgList(uuid);
			map.put("basicImg", basicImgList);
		}
		return ResultFactory.success(map);
	}
	
	/**
	 *  获取某个定点的所有飞行图
	 * @param page
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "获取某个定点下所有航点的飞行图", notes = "参数- 分页page,Map param")
	@GetMapping("/getFlyImgList")
	public Result<Map> getFlyImgList(Page page, @RequestParam Map<String, Object> param) {
		
		return ResultFactory.success(fixedPointService.getFlyImgList(page,  param));
	}
	
	
	@ApiOperation(value = "获取某个定点的所有飞行图(不用分页)", notes = "参数- Map param")
	@GetMapping("/getFlyImgListNoPage")
//	@RequestMapping("/getFlyImgListNoPage")
	public Result getFlyImgListNoPage(@RequestParam Map<String, Object> param) {
		
		return ResultFactory.success(fixedPointService.getFlyImgListNoPage(param));
	}
	
	
	@ApiOperation(value = "获取飞行图，压缩上传到文件服务器", notes = "参数- Map param")
	@GetMapping("/getZip")
	public Result getZip(@RequestParam Map<String, Object> param) {
		
		List<Map> list = fixedPointService.getFlyImgToZip(param);
		String tmpPath = zipTemp + File.separator + CommonUtils.getUUID();
		FileUtil.createDir(tmpPath);	//创建临时目录
		List<File> zipFile = new ArrayList<File>();
		if(list.size() > 0) {
			for (Map map : list) {
				//下载飞行图
				String path = map.get("path").toString();
				File bFile = FileUtil.createFile(tmpPath + File.separator + CommonUtils.getUUID().substring(10) + ".jpg");
				fastDFSClient.downloadFile(path, bFile);
				zipFile.add(bFile);
			}
			File ysFile = new File(tmpPath + ".zip");
			UploadFile uf = null;
			try {
				//压缩上传到文件服务器
				FileUtil.toZip(zipFile, new FileOutputStream(ysFile));
				//上传到文件服务器
				uf = fileClient.uploadFile(ysFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return ResultFactory.success(uf);
		}
		
		return ResultFactory.success();
		
	}
	
	
	@ApiOperation(value = "保存定点", notes = "参数-定点等包装类 vo")
	@PostMapping("/edit")
//	@RequestMapping("/edit")
	public Result edit(@RequestBody VoPoint vo) {
		fixedPointService.edit(vo);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "删除定点", notes = "参数-定点sp")
	@PostMapping("/del")
//	@RequestMapping("/del")
	public Result del(@RequestBody FixedPoint sp) {
		fixedPointService.del(sp);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "保存路线", notes = "参数-航线包装类vo")
	@PostMapping("/saveRoute")
//	@RequestMapping("/saveRoute")
	public Result saveRoute(@RequestBody VoRoute vo) {
		
		return ResultFactory.success(fixedPointService.saveRoute(vo));
	}
	
	@ApiOperation(value = "删除路线", notes = "参数-航线route")
	@PostMapping("/delRoute")
//	@RequestMapping("/delRoute")
	public Result delRoute(@RequestBody Route route) {
		fixedPointService.delRoute(route);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "获取路线路径", notes = "参数-定点uuid，起飞点主键id")
	@GetMapping("/getRouteLine")
//	@RequestMapping("/getRouteLine")
	public Result getRouteLine(String uuid,String id) {
		HashMap map = new HashMap();
		map.put("line",fixedPointService.getRouteLine(uuid));
		map.put("point",checkPointService.getById(id));
		return ResultFactory.success(map);
	}
	
	@ApiOperation(value = "获取所有路线路径", notes = "参数对象param")
	@GetMapping("/getAllRouteLine")
//	@RequestMapping("/getAllRouteLine")
	public Result getAllRouteLine(@RequestParam Map<String, String> param) {
		if(!ThreadLocalUtil.isShenz()) {
			param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
		}else if(String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
			param.remove("deptId");
		}
		return ResultFactory.success(fixedPointService.getAllRouteLine(param));
	}
	
	@ApiOperation(value = "批量导入定点", notes = "参数-定点等包装类")
	@PostMapping("/doPldr")
//	@RequestMapping("/doPldr")
	public Result doPldr(@RequestBody VoPldr vo) throws Exception {
		return ResultFactory.success(fixedPointService.doPldr(vo));
	}
	
	@ApiOperation(value = "新增图片", notes = "参数-FileBusdto")
	@PostMapping("/addPic")
	public Result addPic(@RequestBody FileBusDto dto){
		
		return ResultFactory.success(fixedPointService.addPic(dto));
	}
	
	@ApiOperation(value = "删除图片", notes = "参数-FileBusdto")
	@GetMapping("/removePic")
	public Result removePic(@ApiParam(name = "uuid", value = "fileBus主键", required = true) @RequestParam(value = "uuid", required = true) String uuid){
		
		UpdateWrapper<FileBus> updateWrapper = new UpdateWrapper<FileBus>();
		updateWrapper.eq("uuid", uuid);
		updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		
		return ResultFactory.success(fileBusService.update(updateWrapper));
	}
	
	@ApiOperation(value = "将图片保存到定点文件夹下", notes = "参数-FileBusdto")
	@PostMapping("/saveToLabel")
	public Result saveToLabel(@RequestBody FileBusDto dto){
		fixedPointService.saveToLabel(dto);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "修改定点地址", notes = "参数-定点地址")
	@PostMapping("/editPointAddress")
	public Result editPointAddress(@RequestBody FixedPointDto dto){
		
		FixedPoint fixedPoint = new FixedPoint();
		BeanUtils.copyProperties(dto, fixedPoint);
		
		return ResultFactory.success(fixedPointService.updateById(fixedPoint));
	}
	@ApiOperation(value = "获取图片GPS", notes = "参数-图片文件id")
	@GetMapping("/getGpsByImgId")
	public Result<LnglatVo> getGpsByImgId(String id)throws Exception{
		return ResultFactory.success(fixedPointService.getGpsByImgId(id));
	}
}
